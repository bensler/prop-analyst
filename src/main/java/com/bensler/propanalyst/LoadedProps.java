package com.bensler.propanalyst;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class LoadedProps {

    private final String srcFilename;
    private final List<String> keys;

    public LoadedProps(final String aSrcFilename, final Properties props) {
        final Set<Object> keySet = props.keySet();

        srcFilename = aSrcFilename;
        keys = new ArrayList<String>(keySet.size()); // TODO immutable
        for (Object key : keySet) {
            keys.add(key.toString());
        }

        Collections.sort(keys);
    }

    String getSrcFilename() {
        return srcFilename;
    }

    List<String> getAdditionalKeysIn(final LoadedProps slave) {
        final List<String> slaveKeys = new ArrayList<String>(slave.keys);

        slaveKeys.removeAll(keys);
        return slaveKeys;
    }

    List<String> getMissingKeysIn(final LoadedProps slave) {
        final List<String> thisKeys = new ArrayList<String>(keys);

        thisKeys.removeAll(slave.keys);
        return thisKeys;
    }

}
