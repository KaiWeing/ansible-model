package de.oc.ansibleplugin.model;

import de.oc.ansibleplugin.json.JsonModelReaderWriter;
import de.oc.ansibleplugin.reader.ModelParser;

import java.io.File;
import java.io.IOException;
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

    public AnsibleModules() {
    }

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

    public AnsibleModule getModule(String name) {
        return modules.get(name);
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

    public static void main(String[] args) throws IOException {
        File ansibleModel = File.createTempFile("ansible_model", ".json");
        ModelParser reader = new ModelParser();
        AnsibleModules ansibleModules = reader.readModules("list_of_all_modules.html");
        JsonModelReaderWriter jsonModelReaderWriter = new JsonModelReaderWriter(ansibleModel);
        jsonModelReaderWriter.write(ansibleModules);

        System.out.println("written to " + ansibleModel.getAbsolutePath());
    }

}
