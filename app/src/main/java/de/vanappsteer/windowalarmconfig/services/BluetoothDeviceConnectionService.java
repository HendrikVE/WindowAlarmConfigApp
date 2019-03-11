package de.vanappsteer.windowalarmconfig.services;

import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;

import de.vanappsteer.windowalarmconfig.util.LoggingUtil;

import static de.vanappsteer.windowalarmconfig.services.BluetoothDeviceConnectionService.DeviceConnectionListener.DEVICE_CONNECTION_ERROR_GENERIC;
import static de.vanappsteer.windowalarmconfig.services.BluetoothDeviceConnectionService.DeviceConnectionListener.DEVICE_CONNECTION_ERROR_READ;
import static de.vanappsteer.windowalarmconfig.services.BluetoothDeviceConnectionService.DeviceConnectionListener.DEVICE_CONNECTION_ERROR_UNSUPPORTED;
import static de.vanappsteer.windowalarmconfig.services.BluetoothDeviceConnectionService.DeviceConnectionListener.DEVICE_CONNECTION_ERROR_WRITE;
import static de.vanappsteer.windowalarmconfig.services.BluetoothDeviceConnectionService.DeviceConnectionListener.DEVICE_DISCONNECTED;

public class BluetoothDeviceConnectionService extends Service {

    private final UUID BLE_SERVICE_UUID = UUID.fromString("2fa1dab8-3eef-40fc-8540-7fc496a10d75");

    private final IBinder mBinder = new LocalBinder();

    private BluetoothGatt mBluetoothGatt = null;
    private BluetoothGattService mBluetoothGattService = null;
    private boolean mDisconnectPending = false;

    private HashMap<UUID, String> mCharacteristicHashMap;
    private final Queue<BluetoothGattCharacteristic> mReadCharacteristicsOperationsQueue = new LinkedList<>();
    private final Queue<BluetoothGattCharacteristic> mWriteCharacteristicsOperationsQueue = new LinkedList<>();

    private Set<DeviceConnectionListener> mDeviceConnectionListenerSet = new HashSet<>();

    public BluetoothDeviceConnectionService() {

    }

    public class LocalBinder extends Binder {

        public BluetoothDeviceConnectionService getService() {
            return BluetoothDeviceConnectionService.this;
        }
    }

    public static class DeviceConnectionListener {

        protected static final int DEVICE_DISCONNECTED = 0;
        protected static final int DEVICE_CONNECTION_ERROR_GENERIC = 1;
        protected static final int DEVICE_CONNECTION_ERROR_UNSUPPORTED = 2;
        protected static final int DEVICE_CONNECTION_ERROR_READ = 3;
        protected static final int DEVICE_CONNECTION_ERROR_WRITE = 4;

        public void onDeviceConnected() {}
        public void onDeviceDisconnected() {}
        public void onAllCharacteristicsRead(Map<UUID, String> characteristicMap) {}
        public void onAllCharacteristicsWrote() {}
        public void onDeviceConnectionError(int errorCode) {}
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        disconnectDevice();
    }

    public void connectDevice(BluetoothDevice device) {

        LoggingUtil.debug("connectDevice()");

        mBluetoothGatt = device.connectGatt(BluetoothDeviceConnectionService.this, false, mGattCharacteristicCallback);
    }

    public void disconnectDevice() {

        if (mBluetoothGatt != null) {

            synchronized (mReadCharacteristicsOperationsQueue) {
                synchronized (mWriteCharacteristicsOperationsQueue) {
                    int operationsPending = mReadCharacteristicsOperationsQueue.size() + mWriteCharacteristicsOperationsQueue.size();

                    if (operationsPending == 0) {
                        mBluetoothGatt.disconnect();
                        mDisconnectPending = false;
                    }
                    else {
                        mDisconnectPending = true;
                    }
                }
            }
        }
    }

    private boolean isDisconnected() {
        return mBluetoothGatt == null || mBluetoothGattService == null;
    }

    public void readCharacteristics() {

        if (isDisconnected()) {
            mDeviceConnectionListenerSet.forEach(l -> l.onDeviceConnectionError(DEVICE_DISCONNECTED));

            return;
        }

        List<BluetoothGattCharacteristic> characteristicList = mBluetoothGattService.getCharacteristics();
        mCharacteristicHashMap = new HashMap<>();

        mReadCharacteristicsOperationsQueue.addAll(characteristicList);

        // initial call of readCharacteristic, further calls are done within onCharacteristicRead afterwards
        boolean success = mBluetoothGatt.readCharacteristic(mReadCharacteristicsOperationsQueue.poll());
        if (! success) {
            mDeviceConnectionListenerSet.forEach(l -> l.onDeviceConnectionError(DEVICE_CONNECTION_ERROR_READ));
        }
    }

    public void writeCharacteristics(Map<UUID, String> characteristicMap) {

        if (isDisconnected()) {
            mDeviceConnectionListenerSet.forEach(l -> l.onDeviceConnectionError(DEVICE_DISCONNECTED));

            return;
        }

        synchronized (mWriteCharacteristicsOperationsQueue) {

            boolean needInitialCall = mWriteCharacteristicsOperationsQueue.size() == 0;

            BluetoothGattService gattService = mBluetoothGatt.getService(BLE_SERVICE_UUID);

            for (Map.Entry<UUID, String> entry : characteristicMap.entrySet()) {

                BluetoothGattCharacteristic characteristic = gattService.getCharacteristic(entry.getKey());
                characteristic.setValue(entry.getValue());

                LoggingUtil.debug("entry.getValue() = " + entry.getValue());
                LoggingUtil.debug("characteristic.getStringValue(0) = " +  characteristic.getStringValue(0));

                mWriteCharacteristicsOperationsQueue.add(characteristic);
            }

            if (needInitialCall) {
                // initial call of writeCharacteristic, further calls are done within onCharacteristicWrite afterwards
                boolean success = mBluetoothGatt.writeCharacteristic(mWriteCharacteristicsOperationsQueue.poll());
                if (! success) {
                    mDeviceConnectionListenerSet.forEach(l -> l.onDeviceConnectionError(DEVICE_CONNECTION_ERROR_WRITE));
                }
            }
        }
    }

    public void addDeviceConnectionListener(DeviceConnectionListener listener) {
        mDeviceConnectionListenerSet.add(listener);
    }

    public void removeDeviceConnectionListener(DeviceConnectionListener listener) {
        mDeviceConnectionListenerSet.remove(listener);
    }

    private BluetoothGattCallback mGattCharacteristicCallback = new BluetoothGattCallback() {

        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {

            LoggingUtil.debug("onConnectionStateChange");
            LoggingUtil.debug("status: " + status);
            LoggingUtil.debug("newState: " + newState);

            if (status == BluetoothGatt.GATT_SUCCESS) {

                switch (newState) {

                    case BluetoothProfile.STATE_CONNECTED:
                        LoggingUtil.debug("discoverServices");
                        gatt.discoverServices();
                        break;

                    case BluetoothProfile.STATE_DISCONNECTED:
                        gatt.close();
                        mBluetoothGatt = null;
                        mBluetoothGattService = null;
                        mDeviceConnectionListenerSet.forEach(l -> l.onDeviceDisconnected());
                        break;

                    default:
                        LoggingUtil.debug("unhandled state: " + newState);
                }
            }
            else {
                mDeviceConnectionListenerSet.forEach(l -> l.onDeviceConnectionError(DEVICE_CONNECTION_ERROR_GENERIC));
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {

            LoggingUtil.debug("onServicesDiscovered");

            if (status != BluetoothGatt.GATT_SUCCESS) {
                LoggingUtil.error("status != BluetoothGatt.GATT_SUCCESS");

                mDeviceConnectionListenerSet.forEach(l -> l.onDeviceConnectionError(DEVICE_CONNECTION_ERROR_GENERIC));

                return;
            }

            mBluetoothGattService = gatt.getService(BLE_SERVICE_UUID);
            if (mBluetoothGattService == null) {

                gatt.disconnect();

                mDeviceConnectionListenerSet.forEach(l -> l.onDeviceConnectionError(DEVICE_CONNECTION_ERROR_UNSUPPORTED));

                return;
            }

            mDeviceConnectionListenerSet.forEach(l -> l.onDeviceConnected());
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {

            LoggingUtil.debug("onCharacteristicRead");
            LoggingUtil.debug("uuid: " + characteristic.getUuid());
            LoggingUtil.debug("value: " + characteristic.getStringValue(0));

            mCharacteristicHashMap.put(characteristic.getUuid(), characteristic.getStringValue(0));

            synchronized (mReadCharacteristicsOperationsQueue) {

                if (mReadCharacteristicsOperationsQueue.size() > 0) {
                    boolean success = gatt.readCharacteristic(mReadCharacteristicsOperationsQueue.poll());
                    if (! success) {
                        mDeviceConnectionListenerSet.forEach(l -> l.onDeviceConnectionError(DEVICE_CONNECTION_ERROR_READ));
                    }
                }
                else {
                    mDeviceConnectionListenerSet.forEach(l -> l.onAllCharacteristicsRead(mCharacteristicHashMap));

                    if (mDisconnectPending) {
                        disconnectDevice();
                    }
                }
            }
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {

            LoggingUtil.debug("onCharacteristicWrite");
            LoggingUtil.debug("uuid: " + characteristic.getUuid());
            LoggingUtil.debug("value: " + characteristic.getStringValue(0));

            LoggingUtil.debug("pending queue size: " + mWriteCharacteristicsOperationsQueue.size());

            synchronized (mWriteCharacteristicsOperationsQueue) {

                if (mWriteCharacteristicsOperationsQueue.size() > 0) {
                    boolean success = gatt.writeCharacteristic(mWriteCharacteristicsOperationsQueue.poll());
                    if (! success) {
                        mDeviceConnectionListenerSet.forEach(l -> l.onDeviceConnectionError(DEVICE_CONNECTION_ERROR_WRITE));
                    }
                }
                else {
                    mDeviceConnectionListenerSet.forEach(l -> l.onAllCharacteristicsWrote());

                    if (mDisconnectPending) {
                        disconnectDevice();
                    }
                }
            }
        }
    };
}
