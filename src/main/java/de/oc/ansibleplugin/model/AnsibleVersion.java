package de.oc.ansibleplugin.model;

/**
 * Describes an Ansible API Version.
 *
 * Created by Weingärtner on 21.03.2015.
 */
public class AnsibleVersion implements Comparable<AnsibleVersion> {

    private final int major;
    private final int minor;

    public static final AnsibleVersion DEFAULT_VERSION = new AnsibleVersion(0, 0);

    private AnsibleVersion(int major, int minor) {
        this.major = major;
        this.minor = minor;
    }

    public static AnsibleVersion valueOf(String value) {
        String parts[] = value.split("\\.");
        return new AnsibleVersion(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    @Override
    public int compareTo(AnsibleVersion o) {
        if (major != o.major) {
            return major - o.major;
        }
        if (minor != o.minor) {
            return minor - o.minor;
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnsibleVersion that = (AnsibleVersion) o;

        if (major != that.major) return false;
        if (minor != that.minor) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = major;
        result = 31 * result + minor;
        return result;
    }

    @Override
    public String toString() {
        return String.format("%s.%s", major, minor);
    }
}