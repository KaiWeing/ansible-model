package de.oc.ansibleplugin.json;

import de.oc.ansibleplugin.AnsibleModule;
import de.oc.ansibleplugin.reader.ModuleReader;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.matchers.JUnitMatchers.containsString;


/**
 * Created by Weingärtner on 21.03.2015.
 */
public class JsonConverterTest {

    private final AnsibleModule testModule;

    public JsonConverterTest() {
        this.testModule = new ModuleReader().readModule("redis_module.html");
    }

    @Test
    public void testMarshalling() throws IOException {
        JsonConverter jsonConverter = new JsonConverter();
        StringWriter writer = new StringWriter();
        jsonConverter.write(testModule, writer);

        assertThat(writer.toString(), containsString("redis"));

        AnsibleModule moduleRead = jsonConverter.read(new StringReader(writer.toString()), AnsibleModule.class);

        assertThat(moduleRead.getName(), is("redis"));
    }
}
