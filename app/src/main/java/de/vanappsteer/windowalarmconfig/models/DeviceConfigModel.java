package de.vanappsteer.windowalarmconfig.models;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.vanappsteer.windowalarmconfig.util.BleConfigurationProfile;

public class DeviceConfigModel extends ConfigModel {

    private String mDeviceRoom;
    private String mDeviceID;

    public DeviceConfigModel(String room, String id) {
        mDeviceRoom = room;
        mDeviceID = id;
    }

    @Override
    public Map<UUID, String> getDataMap() {

        Map<UUID, String> map = new HashMap<>();
        map.put(BleConfigurationProfile.CHARACTERISTIC_DEVICE_ROOM_UUID, mDeviceRoom);
        map.put(BleConfigurationProfile.CHARACTERISTIC_DEVICE_ID_UUID, mDeviceID);

        return map;
    }


    /* BEGIN GETTER */
    public String getDeviceRoom() {
        return mDeviceRoom;
    }

    public String getDeviceId() {
        return mDeviceID;
    }
    /* END GETTER */


    /* BEGIN SETTER */
    public void setDeviceRoom(String room) {
        mDeviceRoom = room;
    }

    public void setDeviceId(String id) {
        mDeviceID = id;
    }
    /* END SETTER */
}
