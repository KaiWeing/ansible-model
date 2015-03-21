package de.oc.ansibleplugin.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Weingärtner on 21.03.2015.
 */
public class AnsibleModuleOption {
    private String name;
    private boolean required;
    private String defaultValue;
    private List<String> choices = new ArrayList<>();
    private String comments;
    private AnsibleVersion sinceVersion = AnsibleVersion.DEFAULT_VERSION;

    public AnsibleVersion getSinceVersion() {
        return sinceVersion;
    }

    public void setSinceVersion(AnsibleVersion sinceVersion) {
        this.sinceVersion = sinceVersion;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }

    @Override
    public String toString() {
        return "AnsibleModuleOption{" +
                "name='" + name + '\'' +
                ", required=" + required +
                ", defaultValue='" + defaultValue + '\'' +
                ", choices=" + choices +
                ", comments='" + comments + '\'' +
                '}';
    }
}
