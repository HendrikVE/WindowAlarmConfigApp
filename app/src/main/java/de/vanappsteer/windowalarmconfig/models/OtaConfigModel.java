package de.vanappsteer.windowalarmconfig.models;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.vanappsteer.windowalarmconfig.util.BleConfigurationProfile;

public class OtaConfigModel extends ConfigModel {

    private String mOtaServerAddress;
    private String mOtaFilename;
    private String mOtaUsername;
    private String mOtaPassword;

    public OtaConfigModel(String serverAddress, String filename, String username, String password) {
        mOtaServerAddress = serverAddress;
        mOtaFilename = filename;
        mOtaUsername = username;
        mOtaPassword = password;
    }

    @Override
    public Map<UUID, String> getDataMap() {

        Map<UUID, String> map = new HashMap<>();
        map.put(BleConfigurationProfile.CHARACTERISTIC_OTA_HOST_UUID, mOtaServerAddress);
        map.put(BleConfigurationProfile.CHARACTERISTIC_OTA_FILENAME_UUID, mOtaFilename);
        map.put(BleConfigurationProfile.CHARACTERISTIC_OTA_SERVER_USERNAME_UUID, mOtaUsername);
        map.put(BleConfigurationProfile.CHARACTERISTIC_OTA_SERVER_PASSWORD_UUID, mOtaPassword);

        return map;
    }


    /* BEGIN GETTER */
    public String getOtaServerAddress() {
        return mOtaServerAddress;
    }

    public String getOtaFilename() {
        return mOtaFilename;
    }

    public String getOtaUsername() {
        return mOtaUsername;
    }

    public String getOtaPassword() {
        return mOtaPassword;
    }
    /* END GETTER */


    /* BEGIN SETTER */
    public void setOtaServerAddress(String address) {
        mOtaServerAddress = address;
    }

    public void setOtaFilename(String filename) {
        mOtaFilename = filename;
    }

    public void setOtaUsername(String username) {
        mOtaUsername = username;
    }

    public void setOtaPassword(String password) {
        mOtaPassword = password;
    }
    /* END SETTER */
}
