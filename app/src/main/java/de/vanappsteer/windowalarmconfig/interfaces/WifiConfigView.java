package de.vanappsteer.windowalarmconfig.interfaces;

import de.vanappsteer.windowalarmconfig.models.WifiConfigModel;

public interface WifiConfigView extends ConfigView<WifiConfigModel> {

    void updateWifiSsid(String ssid);
    void updateWifiPassword(String password);
}
