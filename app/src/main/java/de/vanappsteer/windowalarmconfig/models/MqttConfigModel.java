package de.vanappsteer.windowalarmconfig.models;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.vanappsteer.windowalarmconfig.util.BleConfigurationProfile;

public class MqttConfigModel extends ConfigModel {

    private String mMqttUsername;
    private String mMqttPassword;
    private String mMqttBrokerAddress;
    private String mMqttBrokerPort;

    public MqttConfigModel(String username, String password, String brokerAddress, String brokerPort) {
        mMqttUsername = username;
        mMqttPassword = password;
        mMqttBrokerAddress = brokerAddress;
        mMqttBrokerPort = brokerPort;
    }

    @Override
    public Map<UUID, String> getDataMap() {

        Map<java.util.UUID, java.lang.String> map = new HashMap<>();
        map.put(BleConfigurationProfile.CHARACTERISTIC_MQTT_USER_UUID, mMqttUsername);
        map.put(BleConfigurationProfile.CHARACTERISTIC_MQTT_PASSWORD_UUID, mMqttPassword);
        map.put(BleConfigurationProfile.CHARACTERISTIC_MQTT_SERVER_IP_UUID, mMqttBrokerAddress);
        map.put(BleConfigurationProfile.CHARACTERISTIC_MQTT_SERVER_PORT_UUID, mMqttBrokerPort);

        return map;
    }

    @Override
    public boolean isInErrorState() {

        boolean hasEmptyFields = false;
        hasEmptyFields |= "".equals(getMqttUsername());
        hasEmptyFields |= "".equals(getMqttPassword());
        hasEmptyFields |= "".equals(getMqttBrokerAddress());
        hasEmptyFields |= "".equals(getMqttBrokerPort());

        boolean inErrorState = super.isInErrorState();

        return inErrorState || hasEmptyFields;
    }


    /* BEGIN GETTER */
    public String getMqttUsername() {
        return mMqttUsername;
    }

    public String getMqttPassword() {
        return mMqttPassword;
    }

    public String getMqttBrokerAddress() {
        return mMqttBrokerAddress;
    }

    public String getMqttBrokerPort() {
        return mMqttBrokerPort;
    }
    /* END GETTER */


    /* BEGIN SETTER */
    public void setMqttUsername(String username) {
        mMqttUsername = username;
    }

    public void setMqttPassword(String password) {
        mMqttPassword = password;
    }

    public void setMqttBrokerAddress(String address) {
        mMqttBrokerAddress = address;
    }

    public void setMqttBrokerPort(String port) {
        mMqttBrokerPort = port;
    }
    /* END SETTER */
}
