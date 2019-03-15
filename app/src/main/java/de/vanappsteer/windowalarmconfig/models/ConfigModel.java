package de.vanappsteer.windowalarmconfig.models;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public abstract class ConfigModel {

    private Set<Integer> mErrorStateSet = new HashSet<>();

    public abstract Map<UUID, String> getDataMap();

    public void addErrorState(int errorStateId) {
        mErrorStateSet.add(errorStateId);
    }

    public void removeErrorState(int errorStateId) {
        mErrorStateSet.remove(errorStateId);
    }

    public boolean isInErrorState() {
        return mErrorStateSet.size() > 0;
    }
}
