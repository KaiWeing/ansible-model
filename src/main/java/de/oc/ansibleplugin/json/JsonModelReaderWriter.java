package de.oc.ansibleplugin.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import de.oc.ansibleplugin.model.AnsibleModule;
import de.oc.ansibleplugin.model.AnsibleModules;
import de.oc.ansibleplugin.model.AnsibleVersion;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Weingärtner on 21.03.2015.
 */
public class JsonModelReaderWriter {

    private final Gson gson;

    private final File jsonModelFile;

    public JsonModelReaderWriter(File jsonModelFile) {
        this.jsonModelFile = jsonModelFile;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(AnsibleVersion.class, new AnsibleVersionTypeAdapter());
        gsonBuilder.registerTypeAdapter(List.class, new OmitEmptyCollectionTypeAdapter());
        this.gson = gsonBuilder.create();
    }

    public void write(AnsibleModules model) throws IOException {
        FileWriter fileWriter = new FileWriter(jsonModelFile);
        gson.toJson(model.toList(), fileWriter);
        fileWriter.flush();
    }

    @SuppressWarnings("unchecked")
    public AnsibleModules read() throws FileNotFoundException {
        Type listType = new TypeToken<ArrayList<AnsibleModule>>() {
        }.getType();
        List<AnsibleModule> modules = gson.fromJson(new FileReader(jsonModelFile), listType);
        return new AnsibleModules(modules);
    }

    public AnsibleModules readFromClasspath() {
        Type listType = new TypeToken<ArrayList<AnsibleModule>>() {
        }.getType();
        InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream("/ansible_model.json"));
        List<AnsibleModule> modules = gson.fromJson(reader, listType);
        return new AnsibleModules(modules);
    }

    public static void main(String[] args) {
        JsonModelReaderWriter jsonModelReaderWriter = new JsonModelReaderWriter(new File("/"));
        AnsibleModules ansibleModules = jsonModelReaderWriter.readFromClasspath();
        System.out.println(ansibleModules.getApiVersion());
    }

}
