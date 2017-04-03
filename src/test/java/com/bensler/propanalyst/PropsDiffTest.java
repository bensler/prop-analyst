package com.bensler.propanalyst;

import static java.util.Arrays.asList;

import java.io.IOException;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

public class PropsDiffTest {

    public PropsDiffTest() { }

    private LoadedProps loadProperties(final String name) throws IOException {
        final Properties properties = new Properties();
        final String filename = name + ".properties";

        properties.load(getClass().getResourceAsStream(filename));
        return new LoadedProps(filename, properties);
    }

    @Test
    public void testDiff() throws IOException {
        final LoadedProps p1 = loadProperties("01");
        final LoadedProps p2 = loadProperties("02");
        final PropsDiff diff = new PropsDiff(p1, p2);

        Assert.assertEquals(asList("a.b.c"), diff.getMissingKeysInSlave());
        Assert.assertEquals(asList("k.l.m"), diff.getAdditionalKeysInSlave());
    }

}
