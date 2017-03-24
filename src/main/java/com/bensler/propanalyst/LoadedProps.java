package com.bensler.propanalyst;

import static java.util.Collections.unmodifiableList;

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
        final List<String> tmpKeys = new ArrayList<String>(keySet.size());

        srcFilename = aSrcFilename;
        for (Object key : keySet) {
            tmpKeys.add(key.toString());
        }

        Collections.sort(tmpKeys);
        keys = unmodifiableList(tmpKeys);
    }

    String getSrcFilename() {
        return srcFilename;
    }

    List<String> getAdditionalKeysIn(final LoadedProps slave) {
        final List<String> slaveKeys = new ArrayList<String>(slave.keys);

        slaveKeys.removeAll(keys);
        return unmodifiableList(slaveKeys);
    }

    List<String> getMissingKeysIn(final LoadedProps slave) {
        final List<String> thisKeys = new ArrayList<String>(keys);

        thisKeys.removeAll(slave.keys);
        return unmodifiableList(thisKeys);
    }

}
