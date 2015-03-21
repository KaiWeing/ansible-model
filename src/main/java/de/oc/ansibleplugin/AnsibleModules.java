package de.oc.ansibleplugin;

import de.oc.ansibleplugin.reader.ModuleReader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Weingärtner on 21.03.2015.
 */
public class AnsibleModules {
    private HashMap<String, AnsibleModule> modules = new HashMap<>();

    public AnsibleModules(Collection<AnsibleModule> values) {
        addModules(values);
    }

    public void addModule(AnsibleModule module) {
        modules.put(module.getName(), module);
    }

    public void addModules(Collection<AnsibleModule> modules) {
        modules.stream()
                .forEach(this::addModule);
    }

    public List<AnsibleModule> toList() {
        return new ArrayList<>(modules.values());
    }

    public AnsibleModules findModules(String name) {
        List<AnsibleModule> values = toList().stream()
                .filter(m -> m.getName().startsWith(name))
                .collect(Collectors.toList());
        return new AnsibleModules(values);
    }

    public AnsibleVersion getApiVersion() {
        return modules.values().stream()
                .map(AnsibleModule::getSinceVersion)
                .max(AnsibleVersion::compareTo)
                .orElse(AnsibleVersion.DEFAULT_VERSION);
    }

    @Override
    public String toString() {
        return modules.values().toString();
    }

    public static void main(String[] args) {
        ModuleReader reader = new ModuleReader();
        AnsibleModules ansibleModules = reader.readModules("list_of_all_modules.html");
        AnsibleModules a10modules = ansibleModules.findModules("a10");

        System.out.println(a10modules);
    }

}
