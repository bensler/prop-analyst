package com.bensler.propanalyst;

import java.util.List;

public class PropsDiff {

    private final LoadedProps master;
    private final LoadedProps slave;
    private final List<String> additionalKeysInSlave;
    private final List<String> missingKeysInSlave;

    public PropsDiff(final LoadedProps aMaster, final LoadedProps aSlave) {
        master = aMaster;
        slave = aSlave;
        additionalKeysInSlave = master.getAdditionalKeysIn(slave);
        missingKeysInSlave = master.getMissingKeysIn(slave);
    }

    public LoadedProps getMaster() {
        return master;
    }

    public LoadedProps getSlave() {
        return slave;
    }

    public List<String> getAdditionalKeysInSlave() {
        return additionalKeysInSlave;
    }

    public List<String> getMissingKeysInSlave() {
        return missingKeysInSlave;
    }

    public boolean isEmpty() {
        return (additionalKeysInSlave.isEmpty() && missingKeysInSlave.isEmpty());
    }

}
