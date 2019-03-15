package de.vanappsteer.windowalarmconfig.models;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.vanappsteer.windowalarmconfig.util.BleConfigurationProfile;

public class SensorConfigModel extends ConfigModel {

    private String mSensorPollInterval;

    public SensorConfigModel(String sensorPollInterval) {
        mSensorPollInterval = sensorPollInterval;
    }

    @Override
    public Map<UUID, String> getDataMap() {

        Map<UUID, String> map = new HashMap<>();
        map.put(BleConfigurationProfile.CHARACTERISTIC_SENSOR_POLL_INTERVAL_MS_UUID, mSensorPollInterval);

        return map;
    }

    @Override
    public boolean isInErrorState() {

        boolean hasEmptyFields = false;
        hasEmptyFields |= "".equals(getSensorPollInterval());

        boolean inErrorState = super.isInErrorState();

        return inErrorState || hasEmptyFields;
    }


    /* BEGIN GETTER */
    public String getSensorPollInterval() {
        return mSensorPollInterval;
    }
    /* END GETTER */


    /* BEGIN SETTER */
    public void setSensorPollInterval(String pollInterval) {
        mSensorPollInterval = pollInterval;
    }
    /* END SETTER */
}
