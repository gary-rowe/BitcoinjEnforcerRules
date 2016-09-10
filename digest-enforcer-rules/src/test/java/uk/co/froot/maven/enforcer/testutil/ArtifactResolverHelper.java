package uk.co.froot.maven.enforcer.testutil;

import org.apache.maven.artifact.resolver.ArtifactResolutionRequest;
import org.apache.maven.artifact.resolver.ArtifactResolutionResult;
import org.apache.maven.artifact.resolver.DefaultArtifactResolver;

public class ArtifactResolverHelper extends DefaultArtifactResolver {

    @Override
    public ArtifactResolutionResult resolve(final ArtifactResolutionRequest request) {
        return null;
    }

}
