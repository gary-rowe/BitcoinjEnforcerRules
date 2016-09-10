package uk.co.froot.maven.enforcer.testutil;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.handler.ArtifactHandler;
import org.apache.maven.artifact.metadata.ArtifactMetadata;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.filter.ArtifactFilter;
import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.apache.maven.artifact.versioning.OverConstrainedVersionException;
import org.apache.maven.artifact.versioning.VersionRange;

@SuppressWarnings("deprecation")
public class ArtifactHelper implements Artifact {

    private final String name;
    private final String version;
    private final String scope;
    private File file;

    public ArtifactHelper(final String name, final String version, final String scope) {
        this.name = name;
        this.version = version;
        this.scope = scope;
    }

    @Override
    public int compareTo(final Artifact o) {
        return getArtifactId().compareTo(o.getArtifactId());
    }

    @Override
    public String getGroupId() {
        return "test";
    }

    @Override
    public String getArtifactId() {
        return this.name;
    }

    @Override
    public String getVersion() {
        return this.version;
    }

    @Override
    public void setVersion(final String version) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getScope() {
        return this.scope;
    }

    @Override
    public String getType() {
        return "jar";
    }

    @Override
    public String getClassifier() {
        return null;
    }

    @Override
    public boolean hasClassifier() {
        return false;
    }

    @Override
    public File getFile() {
        return this.file;
    }

    @Override
    public void setFile(final File destination) {
        this.file = destination;
    }

    @Override
    public String getBaseVersion() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setBaseVersion(final String baseVersion) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getId() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getDependencyConflictId() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addMetadata(final ArtifactMetadata metadata) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<ArtifactMetadata> getMetadataList() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setRepository(final ArtifactRepository remoteRepository) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ArtifactRepository getRepository() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateVersion(final String version, final ArtifactRepository localRepository) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getDownloadUrl() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setDownloadUrl(final String downloadUrl) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ArtifactFilter getDependencyFilter() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setDependencyFilter(final ArtifactFilter artifactFilter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ArtifactHandler getArtifactHandler() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> getDependencyTrail() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setDependencyTrail(final List<String> dependencyTrail) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setScope(final String scope) {
        throw new UnsupportedOperationException();
    }

    @Override
    public VersionRange getVersionRange() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setVersionRange(final VersionRange newRange) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void selectVersion(final String version) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setGroupId(final String groupId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setArtifactId(final String artifactId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isSnapshot() {
        return false;
    }

    @Override
    public void setResolved(final boolean resolved) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isResolved() {
        return true;
    }

    @Override
    public void setResolvedVersion(final String version) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setArtifactHandler(final ArtifactHandler handler) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isRelease() {
        return true;
    }

    @Override
    public void setRelease(final boolean release) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ArtifactVersion> getAvailableVersions() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setAvailableVersions(final List<ArtifactVersion> versions) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isOptional() {
        return false;
    }

    @Override
    public void setOptional(final boolean optional) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ArtifactVersion getSelectedVersion() throws OverConstrainedVersionException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isSelectedVersionKnown() throws OverConstrainedVersionException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        // groupId:artifactId:version:type:classifier:scope
        return getGroupId() + ":" + getArtifactId() + ":" + getVersion() + ":" + getType() + ":" + getClassifier() + ":" + getScope();
    }

}
