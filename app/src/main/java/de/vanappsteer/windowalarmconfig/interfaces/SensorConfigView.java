package de.vanappsteer.windowalarmconfig.interfaces;

import de.vanappsteer.windowalarmconfig.models.SensorConfigModel;

public interface SensorConfigView extends ConfigView<SensorConfigModel> {

    void updateSensorPollInterval(String pollInterval);
}
