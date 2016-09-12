package uk.co.froot.maven.enforcer.testutil;

import java.util.List;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.metadata.ArtifactMetadata;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.repository.ArtifactRepositoryPolicy;
import org.apache.maven.artifact.repository.Authentication;
import org.apache.maven.artifact.repository.layout.ArtifactRepositoryLayout;
import org.apache.maven.repository.Proxy;

//TODO: Replace with proper mock objects
@SuppressWarnings("deprecation")
public class ArtifactRepositoryHelper implements ArtifactRepository {

    @Override
    public String pathOf(Artifact artifact) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String pathOfRemoteRepositoryMetadata(ArtifactMetadata artifactMetadata) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String pathOfLocalRepositoryMetadata(ArtifactMetadata metadata, ArtifactRepository repository) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getUrl() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setUrl(String url) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getBasedir() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getProtocol() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getId() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setId(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ArtifactRepositoryPolicy getSnapshots() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setSnapshotUpdatePolicy(ArtifactRepositoryPolicy policy) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ArtifactRepositoryPolicy getReleases() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setReleaseUpdatePolicy(ArtifactRepositoryPolicy policy) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ArtifactRepositoryLayout getLayout() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setLayout(ArtifactRepositoryLayout layout) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getKey() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isUniqueVersion() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isBlacklisted() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setBlacklisted(boolean blackListed) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Artifact find(Artifact artifact) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> findVersions(Artifact artifact) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isProjectAware() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setAuthentication(Authentication authentication) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Authentication getAuthentication() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setProxy(Proxy proxy) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Proxy getProxy() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ArtifactRepository> getMirroredRepositories() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setMirroredRepositories(List<ArtifactRepository> mirroredRepositories) {
        throw new UnsupportedOperationException();
    }

}
