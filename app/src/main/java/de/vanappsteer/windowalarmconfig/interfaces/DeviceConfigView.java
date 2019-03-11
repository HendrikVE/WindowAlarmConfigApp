package de.vanappsteer.windowalarmconfig.interfaces;

import de.vanappsteer.windowalarmconfig.models.DeviceConfigModel;

public interface DeviceConfigView extends ConfigView<DeviceConfigModel> {

    void updateDeviceRoom(String room);
    void updateDeviceId(String id);
}
