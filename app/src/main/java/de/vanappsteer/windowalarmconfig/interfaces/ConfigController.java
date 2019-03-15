package de.vanappsteer.windowalarmconfig.interfaces;

import de.vanappsteer.windowalarmconfig.models.ConfigModel;

public abstract class ConfigController<T extends ConfigModel> {

    public abstract void updateView();
    public abstract T getModel();

    public void addErrorState(int errorStateId) {
        getModel().addErrorState(errorStateId);
    }

    public void removeErrorState(int errorStateId) {
        getModel().removeErrorState(errorStateId);
    }

    public boolean isInErrorState() {
        return getModel().isInErrorState();
    }
}
