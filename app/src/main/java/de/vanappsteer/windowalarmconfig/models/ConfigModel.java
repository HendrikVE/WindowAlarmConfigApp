package de.vanappsteer.windowalarmconfig.models;

import java.util.Map;
import java.util.UUID;

public abstract class ConfigModel {

    public abstract Map<UUID, String> getDataMap();
}
