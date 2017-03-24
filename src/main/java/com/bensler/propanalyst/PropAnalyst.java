package com.bensler.propanalyst;

import static java.util.Collections.unmodifiableList;

import java.io.FileInputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class PropAnalyst {

    public static void main(final String[] args) throws IOException {
        final List<String> argsList = Arrays.asList(args);

        new PropAnalyst(args[0], argsList.subList(1, argsList.size())).analyze();
    }

    private final LoadedProps master;
    private List<LoadedProps> slaves;

    public PropAnalyst(final String masterPropertyFileName, final List<String> otherPropertyFilenames)
        throws IOException {
        final List<LoadedProps> tmpSlaves = new ArrayList<LoadedProps>(otherPropertyFilenames.size());

        for (String propFilename : otherPropertyFilenames) {
            tmpSlaves.add(load(propFilename));
        }

        master = load(masterPropertyFileName);
        slaves = unmodifiableList(tmpSlaves);
    }

    public void analyze() {
        System.out.println("master: " + master.getSrcFilename());
        System.out.println();
        for (LoadedProps slave : slaves) {
            analyze(slave);
            System.out.println();
        }
    }

    private void analyze(final LoadedProps slave) {
        final PropsDiff diff = new PropsDiff(master, slave);

        System.out.println("slave:  " + slave.getSrcFilename());
        for (String key : diff.getAdditionalKeysInSlave()) {
            System.out.println("+ " + key);
        }

        for (String key : diff.getMissingKeysInSlave()) {
            System.out.println("- " + key);
        }

        if (diff.isEmpty()) {
            System.out.println("o equal");
        }
    }

    private LoadedProps load(final String filename) throws IOException {
        try(final FileInputStream fIn = new FileInputStream(filename)) {
            final Properties properties = new Properties();

            properties.load(fIn);
            return new LoadedProps(filename, properties);
        }
    }

}
