package de.vanappsteer.windowalarmconfig.activities;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.vanappsteer.windowalarmconfig.R;
import de.vanappsteer.windowalarmconfig.adapter.PagerAdapter;
import de.vanappsteer.windowalarmconfig.interfaces.ConfigView;
import de.vanappsteer.windowalarmconfig.models.ConfigModel;
import de.vanappsteer.windowalarmconfig.services.BluetoothDeviceConnectionService;
import de.vanappsteer.windowalarmconfig.services.BluetoothDeviceConnectionService.DeviceConnectionListener;
import de.vanappsteer.windowalarmconfig.util.BleConfigurationProfile;
import de.vanappsteer.windowalarmconfig.util.LoggingUtil;

public class DeviceConfigActivity extends AppCompatActivity {

    public enum Result {
        CANCELLED,
        FAILED,
        SUCCESS
    }

    private boolean mRestartCommandSent = false;

    public static final String ACTIVITY_RESULT_KEY_RESULT = "ACTIVITY_RESULT_KEY_RESULT";

    public static final String KEY_CHARACTERISTIC_HASH_MAP = "KEY_CHARACTERISTIC_HASH_MAP";

    private BluetoothDeviceConnectionService mDeviceService;
    private boolean mDeviceServiceBound = false;

    private AlertDialog mDialogWriteToDevice;

    private HashMap<UUID, String> mConfigDescriptionHashMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_config);

        setResult(RESULT_CANCELED);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        @SuppressWarnings("unchecked")
        HashMap<UUID, String> characteristicHashMap = (HashMap<UUID, String>) intent.getSerializableExtra(KEY_CHARACTERISTIC_HASH_MAP);
        if (characteristicHashMap == null) {
            characteristicHashMap = new HashMap<>();
        }

        for (Map.Entry<UUID, String> entry : characteristicHashMap.entrySet()) {
            mConfigDescriptionHashMap.put(entry.getKey(), entry.getValue());
        }

        initViews();
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = new Intent(this, BluetoothDeviceConnectionService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbindService(mConnection);
        mDeviceServiceBound = false;
    }

    private void initViews() {
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), mConfigDescriptionHashMap);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                hideKeyboard(DeviceConfigActivity.this);
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // not implemented
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // not implemented
            }
        });

        Button buttonSave = findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(view -> {

            InputMethodManager imm = (InputMethodManager) DeviceConfigActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

            if (mDeviceServiceBound) {
                Map<UUID, String> map = new HashMap<>();

                for (int i = 0; i < adapter.getCount(); i++) {
                    ConfigView configView = (ConfigView) adapter.getItem(i);
                    ConfigModel configModel = configView.getModel();
                    map.putAll(configModel.getDataMap());

                    for (Map.Entry<UUID, String> entry : configModel.getDataMap().entrySet()) {
                        LoggingUtil.debug(entry.getKey().toString());
                        LoggingUtil.debug(entry.getValue());
                    }
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(DeviceConfigActivity.this);
                builder.setTitle(R.string.dialog_bluetooth_device_writing_title);
                builder.setView(R.layout.progress_infinite);
                builder.setCancelable(false);

                mDialogWriteToDevice = builder.create();
                mDialogWriteToDevice.show();

                mDeviceService.writeCharacteristics(map);
            }
            else {
                // TODO: keep config activity instead and retry?
                finishWithIntent(Result.FAILED);
            }
        });
        Button buttonCancel = findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(view -> finishWithIntent(Result.CANCELLED));
    }

    private void finishWithIntent(Result result) {

        Intent resultIntent = new Intent();
        resultIntent.putExtra(ACTIVITY_RESULT_KEY_RESULT, result);

        if (result == Result.SUCCESS) {
            setResult(RESULT_OK, resultIntent);
        }
        else {
            setResult(RESULT_CANCELED, resultIntent);
        }

        DeviceConfigActivity.this.finish();
    }

    public static void hideKeyboard(Activity activity) {

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();

        if (view == null) {
            view = new View(activity);
        }

        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            BluetoothDeviceConnectionService.LocalBinder binder = (BluetoothDeviceConnectionService.LocalBinder) service;
            mDeviceService = binder.getService();
            mDeviceService.addDeviceConnectionListener(mDeviceConnectionListener);
            mDeviceServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mDeviceServiceBound = false;
        }
    };

    private DeviceConnectionListener mDeviceConnectionListener = new DeviceConnectionListener() {

        @Override
        public void onAllCharacteristicsWrote() {

            if (! mRestartCommandSent) {

                mRestartCommandSent = true;

                // send restart command
                Map<UUID, String> map = new HashMap<>();
                map.put(BleConfigurationProfile.CHARACTERISTIC_DEVICE_RESTART_UUID, "empty value");
                mDeviceService.writeCharacteristics(map);
            }
            else {
                finishWithIntent(Result.SUCCESS);
            }
        }

        @Override
        public void onDeviceConnectionError(int errorCode) {

            if (! mRestartCommandSent) {
                finishWithIntent(Result.FAILED);
            }
        }
    };
}
