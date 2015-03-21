package de.oc.ansibleplugin.reader;

import de.oc.ansibleplugin.model.AnsibleModule;
import de.oc.ansibleplugin.model.AnsibleModuleOption;
import de.oc.ansibleplugin.model.AnsibleVersion;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;
import static org.junit.matchers.JUnitMatchers.hasItem;

/**
 * Created by Weingärtner on 21.03.2015.
 */
public class ModelParserTest {

    private ModelParser sut = new ModelParser();

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

    @Test
    public void testDefaultValueNullWhenEmpty() {
        AnsibleModule redisModule = sut.readModule("redis_module.html");

        assertThat(redisModule.getOption("db").getDefaultValue(), is(nullValue()));
    }
}
