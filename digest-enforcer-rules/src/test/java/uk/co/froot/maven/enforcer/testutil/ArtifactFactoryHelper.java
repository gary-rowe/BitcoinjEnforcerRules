package uk.co.froot.maven.enforcer.testutil;

import java.io.File;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.factory.DefaultArtifactFactory;
import org.apache.maven.artifact.versioning.VersionRange;

public class ArtifactFactoryHelper extends DefaultArtifactFactory {

    private final File file;

    public ArtifactFactoryHelper(final File file) {
        this.file = file;
    }

    @Override
    public Artifact createDependencyArtifact(final String groupId, final String artifactId, final VersionRange versionRange,
            final String type, final String classifier, final String scope) {
        final Artifact artifact = new ArtifactHelper(artifactId, versionRange.toString(), scope);
        artifact.setFile(this.file);
        return artifact;
    }

}
