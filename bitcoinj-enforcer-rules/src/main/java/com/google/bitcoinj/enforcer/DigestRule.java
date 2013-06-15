package com.google.bitcoinj.enforcer;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.ArtifactNotFoundException;
import org.apache.maven.artifact.resolver.ArtifactResolutionException;
import org.apache.maven.artifact.resolver.ArtifactResolver;
import org.apache.maven.artifact.versioning.VersionRange;
import org.apache.maven.enforcer.rule.api.EnforcerRule;
import org.apache.maven.enforcer.rule.api.EnforcerRuleException;
import org.apache.maven.enforcer.rule.api.EnforcerRuleHelper;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.component.configurator.expression.ExpressionEvaluationException;
import org.codehaus.plexus.component.repository.exception.ComponentLookupException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

/**
 * <p>Enforcer rule to provide the following to build process:</p>
 * <ul>
 * <li>Checking of hashes against an expected list</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class DigestRule implements EnforcerRule {

  private String[] urns = null;


  public String getCacheId() {
    return "id"; // This is not cacheable
  }

  public boolean isCacheable() {
    return false;
  }

  public boolean isResultValid(EnforcerRule arg0) {
    return false;
  }

  @SuppressWarnings("deprecation")
  public void execute(EnforcerRuleHelper helper) throws EnforcerRuleException {

    Log log = helper.getLog();

    boolean failed=false;

    try {
      // get the various expressions out of the helper.
      MavenProject project = (MavenProject) helper.evaluate("${project}");

      ArtifactRepository localRepository;

      // Due to backwards compatibility issues the deprecated interface must be used here
      ArtifactFactory artifactFactory;
      ArtifactResolver resolver;
      try {
        localRepository = (ArtifactRepository) helper.evaluate("${localRepository}");

        artifactFactory = (ArtifactFactory) helper.getComponent(ArtifactFactory.class);

        resolver = (ArtifactResolver) helper.getComponent(ArtifactResolver.class);

      } catch (ComponentLookupException e) {
        throw new EnforcerRuleException("Failing because a component lookup failed", e);
      }

      if (urns == null) {
        throw new EnforcerRuleException("Failing because there are no URNs in the <configuration> section. See the README for help.");
      }

      for (String urn : urns) {
        log.info("Verifying URN: " + urn);
        // Decode it into artifact co-ordinates
        String[] coordinates = urn.split(":");
        if (coordinates.length != 8) {
          throw new EnforcerRuleException("Failing because URH '" + urn + "' is not in format 'groupId:artifactId:version:type:classifier:scope:algorithm:hash'");
        }

        String groupId = coordinates[0];
        String artifactId = coordinates[1];
        String version = coordinates[2];
        String type = coordinates[3];
        String classifier = "null".equalsIgnoreCase(coordinates[4]) ? null : coordinates[4];
        String scope = coordinates[5];
        String algorithm = coordinates[6];
        String hash = coordinates[7];

        VersionRange versionRange = VersionRange.createFromVersion(version);
        Artifact artifact = artifactFactory.createDependencyArtifact(groupId, artifactId, versionRange, type, classifier, scope);
        try {
          resolver.resolve(artifact, project.getRemoteArtifactRepositories(), localRepository);
        } catch (ArtifactResolutionException e) {
          throw new EnforcerRuleException("Failing due to artifact resolution ", e);
        } catch (ArtifactNotFoundException e) {
          throw new EnforcerRuleException("Failing due to artifact not found", e);
        }

        String actual = digest(artifact.getFile(), algorithm);
        if (!actual.equals(hash)) {
          log.error("*** CRITICAL FAILURE *** Artifact does not match. Possible side-chain attack. Expected='" + hash + "' Actual='" + actual + "'");
          failed = true;
        }
      }

      if (failed) {
        throw new EnforcerRuleException("At least one artifact has not met expectations.");
      }

    } catch (ExpressionEvaluationException e) {
      throw new EnforcerRuleException("Unable to lookup an expression " + e.getLocalizedMessage(), e);
    }
  }

  /**
   * @param file The file to digest
   * @param algo The algorithm to use (e.g. "sha1", "md5")
   *
   * @return The hex version of the digest
   *
   * @throws EnforcerRuleException If something goes wrong
   */
  private String digest(File file, String algo) throws EnforcerRuleException {
    try {
      // Create a fresh digest every time
      MessageDigest md = MessageDigest.getInstance(algo);

      // Wrap a digest around the file input stream
      FileInputStream fis = new FileInputStream(file);
      DigestInputStream dis = new DigestInputStream(fis, md);

      // Read fully to get digest
      while (dis.read() != -1) {
      }

      // Clean up resources
      dis.close();
      fis.close();
      byte[] digest = md.digest();

      return byteToHex(digest);

    } catch (NoSuchAlgorithmException e) {
      throw new EnforcerRuleException("Unable to digest " + e.getLocalizedMessage(), e);
    } catch (FileNotFoundException e) {
      throw new EnforcerRuleException("Unable to digest " + e.getLocalizedMessage(), e);
    } catch (IOException e) {
      throw new EnforcerRuleException("Unable to digest " + e.getLocalizedMessage(), e);
    }
  }

  /**
   * @param digest The digest of the message
   *
   * @return The hex version
   */
  private static String byteToHex(final byte[] digest) {
    Formatter formatter = new Formatter();
    for (byte b : digest) {
      formatter.format("%02x", b);
    }
    String result = formatter.toString();
    formatter.close();
    return result;
  }

}
