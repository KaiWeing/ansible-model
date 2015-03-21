package de.oc.ansibleplugin;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by Weingärtner on 21.03.2015.
 */
public class AnsibleVersionTest {


    @Test
    public void testVersionParsing() {
        AnsibleVersion version = AnsibleVersion.valueOf("1.2");

        assertThat(version.getMajor(), is(1));
        assertThat(version.getMinor(), is(2));
    }

    @Test
    public void testVersionComparison() {
        assertTrue("1.1 > 1.0", AnsibleVersion.valueOf("1.1").compareTo(AnsibleVersion.valueOf("1.0")) > 0);
        assertTrue("1.0 < 1.1", AnsibleVersion.valueOf("1.0").compareTo(AnsibleVersion.valueOf("1.1")) < 0);
        assertTrue("1.0 = 1.0", AnsibleVersion.valueOf("1.0").compareTo(AnsibleVersion.valueOf("1.0")) == 0);
    }
}
