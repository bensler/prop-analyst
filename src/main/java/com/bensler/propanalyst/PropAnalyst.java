package com.bensler.propanalyst;

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
        master = load(masterPropertyFileName);

        slaves = new ArrayList<LoadedProps>(otherPropertyFilenames.size()); // TODO Immutable
        for (String propFilename : otherPropertyFilenames) {
            slaves.add(load(propFilename));
        }
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
        final List<String> additionalKeysIn = master.getAdditionalKeysIn(slave);
        final List<String> missingKeys = master.getMissingKeysIn(slave);

        System.out.println("slave:  " + slave.getSrcFilename());
        for (String key : additionalKeysIn) {
            System.out.println("+ " + key);
        }

        for (String key : missingKeys) {
            System.out.println("- " + key);
        }

        if (additionalKeysIn.isEmpty() && missingKeys.isEmpty()) {
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
