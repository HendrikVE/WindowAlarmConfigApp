package de.vanappsteer.windowalarmconfig.util;

import java.util.UUID;

public class BleConfigurationProfile {

    public static final UUID CHARACTERISTIC_DEVICE_ROOM_UUID = UUID.fromString("d3491796-683b-4b9c-aafb-f51a35459d43");
    public static final UUID CHARACTERISTIC_DEVICE_ID_UUID = UUID.fromString("4745e11f-b403-4cfb-83bb-710d46897875");

    public static final UUID CHARACTERISTIC_MQTT_USER_UUID = UUID.fromString("69150609-18f8-4523-a41f-6d9a01d2e08d");
    public static final UUID CHARACTERISTIC_MQTT_PASSWORD_UUID = UUID.fromString("8bebec77-ea21-4c14-9d64-dbec1fd5267c");
    public static final UUID CHARACTERISTIC_MQTT_SERVER_IP_UUID = UUID.fromString("e3b150fb-90a2-4cd3-80c5-b1189e110ef1");
    public static final UUID CHARACTERISTIC_MQTT_SERVER_PORT_UUID = UUID.fromString("4eeff953-0f5e-43ee-b1be-1783a8190b0d");

    public static final UUID CHARACTERISTIC_OTA_HOST_UUID = UUID.fromString("2f44b103-444c-48f5-bf60-91b81dfa0a25");
    public static final UUID CHARACTERISTIC_OTA_FILENAME_UUID = UUID.fromString("4b95d245-db08-4c56-98f9-738faa8cfbb6");
    public static final UUID CHARACTERISTIC_OTA_SERVER_USERNAME_UUID = UUID.fromString("1c93dce2-3796-4027-9f55-6d251c41dd34");
    public static final UUID CHARACTERISTIC_OTA_SERVER_PASSWORD_UUID = UUID.fromString("0e837309-5336-45a3-9b69-d0f7134f30ff");

    public static final UUID CHARACTERISTIC_WIFI_SSID_UUID = UUID.fromString("8ca0bf1d-bb5d-4a66-9191-341fd805e288");
    public static final UUID CHARACTERISTIC_WIFI_PASSWORD_UUID = UUID.fromString("fa41c195-ae99-422e-8f1f-0730702b3fc5");

    public static final UUID CHARACTERISTIC_SENSOR_POLL_INTERVAL_MS_UUID = UUID.fromString("68011c92-854a-4f2c-a94c-5ee37dc607c3");

    public static final UUID CHARACTERISTIC_DEVICE_RESTART_UUID = UUID.fromString("890f7b6f-cecc-4e3e-ade2-5f2907867f4b");

}
