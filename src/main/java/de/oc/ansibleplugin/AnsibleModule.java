package de.oc.ansibleplugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Contains all describing metadata about an Ansible module.
 *
 * Created by Weingärtner on 21.03.2015.
 */
public class AnsibleModule {
    private String name;
    private String info;
    private String synopsis;
    private AnsibleVersion sinceVersion = AnsibleVersion.DEFAULT_VERSION;
    private HashMap<String, AnsibleModuleOption> options = new HashMap<>();

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public AnsibleVersion getSinceVersion() {
        return sinceVersion;
    }

    public void setSinceVersion(AnsibleVersion sinceVersion) {
        this.sinceVersion = sinceVersion;
    }

    public void addOption(AnsibleModuleOption option) {
        options.put(option.getName(), option);
    }

    public AnsibleModuleOption getOption(String name) {
        return options.get(name);
    }

    public List<AnsibleModuleOption> getOptions() {
        return new ArrayList(options.values());
    }

    public List<AnsibleModuleOption> getRequiredOptions() {
        return getOptions().stream()
                .filter(AnsibleModuleOption::isRequired)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "AnsibleModule{" +
                "name='" + name + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", sinceVersion=" + sinceVersion +
                ", options=" + options +
                '}';
    }

    public void addOptions(Collection<AnsibleModuleOption> ansibleModuleOptions) {
        ansibleModuleOptions.forEach(this::addOption);
    }
}
