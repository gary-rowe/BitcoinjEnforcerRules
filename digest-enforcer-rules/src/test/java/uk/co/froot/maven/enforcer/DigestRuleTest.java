package uk.co.froot.maven.enforcer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.resolver.ArtifactResolver;
import org.apache.maven.enforcer.rule.api.EnforcerRuleException;
import org.apache.maven.project.MavenProject;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.co.froot.maven.enforcer.testutil.ArtifactFactoryHelper;
import uk.co.froot.maven.enforcer.testutil.ArtifactHelper;
import uk.co.froot.maven.enforcer.testutil.ArtifactRepositoryHelper;
import uk.co.froot.maven.enforcer.testutil.ArtifactResolverHelper;
import uk.co.froot.maven.enforcer.testutil.ProjectHelper;

public class DigestRuleTest {

    private static final Object KEY_PROJECT = "${project}";
    private static final Object KEY_LOCAL_REPOSITORY = "${localRepository}";
    @SuppressWarnings("deprecation")
    private static final Object KEY_ArtifactFactory = org.apache.maven.artifact.factory.ArtifactFactory.class;
    private static final Object KEY_ArtifactResolver = ArtifactResolver.class;
    private static final File dummyArtifactFile = new File("target/test/Dep.jar");
    private static final String fileContent = "This is a dummy file";
    private static final String fileContentSHA = "e2648e9f0e220678a5ca1d71be6c42b7296ee329";

    @BeforeClass
    public static void init() {
        final File folder = dummyArtifactFile.getParentFile();
        System.out.println(folder.getAbsolutePath());
        folder.mkdirs();
        final File dummyArtifactShaFile = new File(folder, dummyArtifactFile.getName() + ".sha1");
        writeFile(dummyArtifactFile, fileContent.getBytes());
        writeFile(dummyArtifactShaFile, fileContentSHA.getBytes());
    }

    private static void writeFile(final File file, final byte[] bytes) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(bytes);
        } catch (final IOException e) {
        	e.printStackTrace();
            if (fos != null) {
                try {
                    fos.close();
                } catch (final IOException e2) {
                	e2.printStackTrace();
                }
            }
        }
    }

    private static Artifact dummyArtifact(final String name, final String version, final String scope) {
        final Artifact artifact = new ArtifactHelper(name, version, scope);
        artifact.setFile(dummyArtifactFile);
        return artifact;
    }

    private static String urn(final Artifact artifact) {
        return artifact.toString() + ":" + fileContentSHA;
    }

    private static Artifact DUMMY_ARTIFACT = dummyArtifact("test1", "1.2.0", "compile");
    private static String DUMMY_URN = urn(DUMMY_ARTIFACT);
    private static String DUMMY_URN_HASH = DUMMY_URN.substring(0, DUMMY_URN.length() - 1) + 'a';
    private static String DUMMY_URN_SCOPE = DUMMY_URN.replace("compile", "runtime");

    private static ProjectHelper dummyHelper() {
        final ProjectHelper helper = new ProjectHelper();
        helper.set(KEY_LOCAL_REPOSITORY, new ArtifactRepositoryHelper());
        helper.set(KEY_ArtifactFactory, new ArtifactFactoryHelper(dummyArtifactFile));
        helper.set(KEY_ArtifactResolver, new ArtifactResolverHelper());
        return helper;
    }

    private static MavenProject dummyProject(final ProjectHelper helper) {
        final MavenProject project = new MavenProject();
        helper.set(KEY_PROJECT, project);
        project.setPluginArtifacts(new HashSet<Artifact>());
        project.getArtifacts();
        return project;
    }

    @Test
    public void testWithDependencyURN() throws EnforcerRuleException {
        final DigestRule rule = new DigestRule();
        final ProjectHelper helper = dummyHelper();
        final MavenProject project = dummyProject(helper);

        project.getArtifacts().add(DUMMY_ARTIFACT);
        rule.setUrns(DUMMY_URN);

        rule.execute(helper);
    }

    @Test(expected = EnforcerRuleException.class)
    public void testWithoutDependencyURN() throws EnforcerRuleException {
        final DigestRule rule = new DigestRule();
        final ProjectHelper helper = dummyHelper();
        final MavenProject project = dummyProject(helper);

        project.getArtifacts().add(DUMMY_ARTIFACT);
        rule.setUrns();

        rule.execute(helper);
    }

    @Test(expected = EnforcerRuleException.class)
    public void testWithDependencyURNHash() throws EnforcerRuleException {
        final DigestRule rule = new DigestRule();
        final ProjectHelper helper = dummyHelper();
        final MavenProject project = dummyProject(helper);

        project.getArtifacts().add(DUMMY_ARTIFACT);
        rule.setUrns(DUMMY_URN_HASH);

        rule.execute(helper);
    }

    @Test(expected = EnforcerRuleException.class)
    public void testWithDependencyURNScope() throws EnforcerRuleException {
        final DigestRule rule = new DigestRule();
        final ProjectHelper helper = dummyHelper();
        final MavenProject project = dummyProject(helper);

        project.getArtifacts().add(DUMMY_ARTIFACT);
        rule.setUrns(DUMMY_URN_SCOPE);

        rule.execute(helper);
    }

    @Test
    public void testWithPluginURN() throws EnforcerRuleException {
        final DigestRule rule = new DigestRule();
        final ProjectHelper helper = dummyHelper();
        final MavenProject project = dummyProject(helper);

        project.getPluginArtifacts().add(DUMMY_ARTIFACT);
        rule.setUrns(DUMMY_URN);

        rule.execute(helper);
    }

    @Test(expected = EnforcerRuleException.class)
    public void testWithoutPluginURN() throws EnforcerRuleException {
        final DigestRule rule = new DigestRule();
        final ProjectHelper helper = dummyHelper();
        final MavenProject project = dummyProject(helper);

        project.getPluginArtifacts().add(DUMMY_ARTIFACT);
        rule.setUrns();

        rule.execute(helper);
    }

    @Test(expected = EnforcerRuleException.class)
    public void testWithPluginURNHash() throws EnforcerRuleException {
        final DigestRule rule = new DigestRule();
        final ProjectHelper helper = dummyHelper();
        final MavenProject project = dummyProject(helper);

        project.getPluginArtifacts().add(DUMMY_ARTIFACT);
        rule.setUrns(DUMMY_URN_HASH);

        rule.execute(helper);
    }

    @Test(expected = EnforcerRuleException.class)
    public void testWithPluginURNScope() throws EnforcerRuleException {
        final DigestRule rule = new DigestRule();
        final ProjectHelper helper = dummyHelper();
        final MavenProject project = dummyProject(helper);

        project.getPluginArtifacts().add(DUMMY_ARTIFACT);
        rule.setUrns(DUMMY_URN_SCOPE);

        rule.execute(helper);
    }

}
