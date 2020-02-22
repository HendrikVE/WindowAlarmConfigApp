package de.vanappsteer.windowalarmconfig.services;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.polidea.rxandroidble2.RxBleDeviceServices;

import java.util.UUID;

import de.vanappsteer.genericbleprotocolservice.GenericBleProtocolService;

public class DeviceConfigProtocolService extends GenericBleProtocolService {

    public static final UUID BLE_SERVICE_UUID = UUID.fromString("2fa1dab8-3eef-40fc-8540-7fc496a10d75");

    private final IBinder mBinder = new LocalBinder();

    public DeviceConfigProtocolService() { }

    @Override
    public void onCreate() {
        super.onCreate();

        addDeviceConnectionListener(mDeviceConnectionListener);
    }

    @Override
    public UUID getServiceUuid() {
        return BLE_SERVICE_UUID;
    }

    @SuppressLint("CheckResult")
    @Override
    protected boolean checkSupportedService(RxBleDeviceServices deviceServices) {

        try {
            deviceServices.getService(getServiceUuid()).blockingGet();
            return true;
        }
        catch (Throwable t) {
            return false;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class LocalBinder extends Binder {

        public DeviceConfigProtocolService getService() {
            return DeviceConfigProtocolService.this;
        }
    }

    private DeviceConnectionListener mDeviceConnectionListener = new DeviceConnectionListener() {

        @Override
        public void onCharacteristicWrote(UUID uuid, String value) {


        }
    };
}
