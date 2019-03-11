package de.vanappsteer.windowalarmconfig.interfaces;

import de.vanappsteer.windowalarmconfig.models.OtaConfigModel;

public interface OtaConfigView extends ConfigView<OtaConfigModel> {

    void updateOtaServerAddress(String address);
    void updateOtaFilename(String filename);
    void updateOtaUsername(String username);
    void updateOtaPassword(String password);
}
