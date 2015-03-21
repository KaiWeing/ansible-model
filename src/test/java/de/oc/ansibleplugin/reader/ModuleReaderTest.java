package de.oc.ansibleplugin.reader;

import de.oc.ansibleplugin.AnsibleModule;
import de.oc.ansibleplugin.AnsibleModuleOption;
import de.oc.ansibleplugin.AnsibleVersion;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;
import static org.junit.matchers.JUnitMatchers.hasItem;

/**
 * Created by Weingärtner on 21.03.2015.
 */
public class ModuleReaderTest {

    private ModuleReader sut = new ModuleReader();

    @Test
    public void testReadModule() {
        AnsibleModule redisModule = sut.readModule("redis_module.html");

        assertThat(redisModule.getName(), is("redis"));
        assertThat(redisModule.getInfo(), containsString("Various redis commands"));
        assertThat(redisModule.getSynopsis(), containsString("Unified utility to interact with redis instances"));
        assertThat(redisModule.getSinceVersion(), is(AnsibleVersion.valueOf("1.3")));
    }

    @Test
    public void testReadModuleOptions() {
        AnsibleModule redisModule = sut.readModule("redis_module.html");

        assertThat(redisModule.getOptions(), is(not(Collections.<AnsibleModuleOption>emptyList())));
        assertThat(redisModule.getOption("flush_mode"), is(not(nullValue())));
        assertThat(redisModule.getOption("flush_mode").getName(), is("flush_mode"));
        assertThat(redisModule.getOption("flush_mode").isRequired(), is(false));
        assertThat(redisModule.getOption("flush_mode").getDefaultValue(), is("all"));
        assertThat(redisModule.getOption("flush_mode").getChoices(), allOf(hasItem("all"), hasItem("db")));
        assertThat(redisModule.getOption("flush_mode").getComments(), containsString("Type of flush"));
        assertThat(redisModule.getOption("name").getSinceVersion(), is(AnsibleVersion.valueOf("1.6")));
        assertThat(redisModule.getRequiredOptions().size(), is(1));
    }
}
