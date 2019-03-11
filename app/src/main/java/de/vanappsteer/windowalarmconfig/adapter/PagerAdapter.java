package de.vanappsteer.windowalarmconfig.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.HashMap;
import java.util.UUID;

import de.vanappsteer.windowalarmconfig.fragments.DeviceConfigFragment;
import de.vanappsteer.windowalarmconfig.fragments.MqttConfigFragment;
import de.vanappsteer.windowalarmconfig.fragments.OtaConfigFragment;
import de.vanappsteer.windowalarmconfig.fragments.SensorConfigFragment;
import de.vanappsteer.windowalarmconfig.fragments.WifiConfigFragment;
import de.vanappsteer.windowalarmconfig.models.DeviceConfigModel;
import de.vanappsteer.windowalarmconfig.models.MqttConfigModel;
import de.vanappsteer.windowalarmconfig.models.OtaConfigModel;
import de.vanappsteer.windowalarmconfig.models.SensorConfigModel;
import de.vanappsteer.windowalarmconfig.models.WifiConfigModel;
import de.vanappsteer.windowalarmconfig.util.BleConfigurationProfile;
import de.vanappsteer.windowalarmconfig.util.LoggingUtil;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private int mNumerOfTabs;

    private DeviceConfigFragment mDeviceConfigFragment;
    private OtaConfigFragment mOtaConfigFragment;
    private WifiConfigFragment mWifiConfigFragment;
    private MqttConfigFragment mMqttConfigFragment;
    private SensorConfigFragment mSensorConfigFragment;

    public PagerAdapter(FragmentManager fm, int numberOfTabs, HashMap<UUID, String> characteristicHashMap) {

        super(fm);
        this.mNumerOfTabs = numberOfTabs;

        DeviceConfigModel deviceConfigModel = new DeviceConfigModel(
                characteristicHashMap.get(BleConfigurationProfile.CHARACTERISTIC_DEVICE_ROOM_UUID),
                characteristicHashMap.get(BleConfigurationProfile.CHARACTERISTIC_DEVICE_ID_UUID)
        );
        mDeviceConfigFragment = new DeviceConfigFragment();
        mDeviceConfigFragment.setModel(deviceConfigModel);

        OtaConfigModel otaConfigModel = new OtaConfigModel(
                characteristicHashMap.get(BleConfigurationProfile.CHARACTERISTIC_OTA_HOST_UUID),
                characteristicHashMap.get(BleConfigurationProfile.CHARACTERISTIC_OTA_FILENAME_UUID),
                characteristicHashMap.get(BleConfigurationProfile.CHARACTERISTIC_OTA_SERVER_USERNAME_UUID),
                characteristicHashMap.get(BleConfigurationProfile.CHARACTERISTIC_OTA_SERVER_PASSWORD_UUID)
        );
        mOtaConfigFragment = new OtaConfigFragment();
        mOtaConfigFragment.setModel(otaConfigModel);


        WifiConfigModel wifiConfigModel = new WifiConfigModel(
                characteristicHashMap.get(BleConfigurationProfile.CHARACTERISTIC_WIFI_SSID_UUID),
                characteristicHashMap.get(BleConfigurationProfile.CHARACTERISTIC_WIFI_PASSWORD_UUID)
        );
        mWifiConfigFragment = new WifiConfigFragment();
        mWifiConfigFragment.setModel(wifiConfigModel);

        MqttConfigModel mqttConfigModel = new MqttConfigModel(
                characteristicHashMap.get(BleConfigurationProfile.CHARACTERISTIC_MQTT_USER_UUID),
                characteristicHashMap.get(BleConfigurationProfile.CHARACTERISTIC_MQTT_PASSWORD_UUID),
                characteristicHashMap.get(BleConfigurationProfile.CHARACTERISTIC_MQTT_SERVER_IP_UUID),
                characteristicHashMap.get(BleConfigurationProfile.CHARACTERISTIC_MQTT_SERVER_PORT_UUID)
        );
        mMqttConfigFragment = new MqttConfigFragment();
        mMqttConfigFragment.setModel(mqttConfigModel);

        SensorConfigModel sensorConfigModel = new SensorConfigModel(
                characteristicHashMap.get(BleConfigurationProfile.CHARACTERISTIC_SENSOR_POLL_INTERVAL_MS_UUID)
        );
        mSensorConfigFragment = new SensorConfigFragment();
        mSensorConfigFragment.setModel(sensorConfigModel);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment;

        switch (position) {

            case 0:
                fragment = mDeviceConfigFragment;
                break;

            case 1:
                fragment = mOtaConfigFragment;
                break;

            case 2:
                fragment = mWifiConfigFragment;
                break;

            case 3:
                fragment = mMqttConfigFragment;
                break;

            case 4:
                fragment = mSensorConfigFragment;
                break;

            default:
                LoggingUtil.error("no fragment defined for position: " + position);
                return null;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return mNumerOfTabs;
    }
}
