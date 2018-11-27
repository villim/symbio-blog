package com.symbio.blog.domain;

public class Version {

    String version;

    String revision;

    public Version(String version, String revision) {
        this.version = version;
        this.revision = revision;
    }

    public String getVersion() {
        return version;
    }

    public String getRevision() {
        return revision;
    }

    @Override
    public String toString() {
        return "Version [version=" + version + ", revision=" + revision + "]";
    }
}