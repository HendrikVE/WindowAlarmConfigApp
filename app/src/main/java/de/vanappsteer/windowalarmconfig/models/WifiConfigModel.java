package de.vanappsteer.windowalarmconfig.models;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.vanappsteer.windowalarmconfig.util.BleConfigurationProfile;

public class WifiConfigModel extends ConfigModel {

    private String mWifiSsid;
    private String mWifiPassword;

    public WifiConfigModel(String ssid, String password) {
        mWifiSsid = ssid;
        mWifiPassword = password;
    }

    @Override
    public Map<UUID, String> getDataMap() {

        Map<UUID, String> map = new HashMap<>();
        map.put(BleConfigurationProfile.CHARACTERISTIC_WIFI_SSID_UUID, mWifiSsid);
        map.put(BleConfigurationProfile.CHARACTERISTIC_WIFI_PASSWORD_UUID, mWifiPassword);

        return map;
    }

    @Override
    public boolean isInErrorState() {

        boolean hasEmptyFields = false;
        hasEmptyFields |= "".equals(getWifiSsid());
        hasEmptyFields |= "".equals(getWifiPassword());

        boolean inErrorState = super.isInErrorState();

        return inErrorState || hasEmptyFields;
    }


    /* BEGIN GETTER */
    public String getWifiSsid() {
        return mWifiSsid;
    }

    public String getWifiPassword() {
        return mWifiPassword;
    }
    /* END GETTER */


    /* BEGIN SETTER */
    public void setWifiSsid(String ssid) {
        mWifiSsid = ssid;
    }

    public void setWifiPassword(String password) {
        mWifiPassword = password;
    }
    /* END SETTER */
}
