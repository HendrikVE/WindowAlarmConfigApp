package de.vanappsteer.windowalarmconfig.interfaces;

import de.vanappsteer.windowalarmconfig.models.ConfigModel;

public interface ConfigView<T extends ConfigModel> {

    void setModel(T model);
    T getModel();

    void updateDisplayedErrors();
}
