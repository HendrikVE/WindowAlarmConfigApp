package de.vanappsteer.windowalarmconfig.interfaces;

import de.vanappsteer.windowalarmconfig.models.ConfigModel;

public interface ConfigController<T extends ConfigModel> {

    void updateView();
    T getModel();
}
