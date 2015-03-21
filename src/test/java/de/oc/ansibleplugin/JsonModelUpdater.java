package de.oc.ansibleplugin;

import de.oc.ansibleplugin.json.JsonModelReaderWriter;
import de.oc.ansibleplugin.model.AnsibleModules;
import de.oc.ansibleplugin.reader.ModelParser;

import java.io.File;
import java.io.IOException;

/**
 * Updates the Model data in src/main/resources.
 *
 * Created by Weingärtner on 21.03.2015.
 */
public class JsonModelUpdater {

    public static void main(String[] args) throws IOException {
        File modelFile = new File("src/main/resources/ansible_model.json");
        ModelParser modelParser = new ModelParser();
        AnsibleModules ansibleModules = modelParser.readModules("list_of_all_modules.html");
        JsonModelReaderWriter jsonModelReaderWriter = new JsonModelReaderWriter(modelFile);
        jsonModelReaderWriter.write(ansibleModules);

        System.out.println("Wrote " + modelFile.getAbsolutePath());
    }
}
