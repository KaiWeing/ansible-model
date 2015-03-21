package de.oc.ansibleplugin.json;

import de.oc.ansibleplugin.model.AnsibleModule;
import de.oc.ansibleplugin.model.AnsibleModules;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


/**
 * Created by Weingärtner on 21.03.2015.
 */
public class JsonModelReaderWriterTest {


    public JsonModelReaderWriterTest() {

    }

    @Test
    public void testMarshalling() throws IOException {
        File testFile = File.createTempFile("test", ".json");
        testFile.deleteOnExit();

        JsonModelReaderWriter jsonModelReaderWriter = new JsonModelReaderWriter(testFile);
        StringWriter writer = new StringWriter();
        AnsibleModule ansibleModule = new AnsibleModule();
        ansibleModule.setName("redis");
        AnsibleModules ansibleModules = new AnsibleModules();
        ansibleModules.addModule(ansibleModule);
        jsonModelReaderWriter.write(ansibleModules);

        AnsibleModules modulesRead = jsonModelReaderWriter.read();

        assertThat(modulesRead.getModule("redis").getName(), is("redis"));
    }
}
