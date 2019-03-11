package de.vanappsteer.windowalarmconfig.activities;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Intent intent = new Intent(StartActivity.this, DeviceScanActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

}
