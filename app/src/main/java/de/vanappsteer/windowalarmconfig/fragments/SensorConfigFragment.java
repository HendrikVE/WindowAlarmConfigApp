package de.vanappsteer.windowalarmconfig.fragments;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.vanappsteer.windowalarmconfig.R;
import de.vanappsteer.windowalarmconfig.presenter.SensorConfigPresenter;
import de.vanappsteer.windowalarmconfig.interfaces.SensorConfigView;
import de.vanappsteer.windowalarmconfig.models.SensorConfigModel;
import de.vanappsteer.windowalarmconfig.util.TextChangeWatcher;

public class SensorConfigFragment extends Fragment implements SensorConfigView {

    private TextInputEditText mEditTextSensorPollInterval;

    private SensorConfigPresenter mPresenter;

    public SensorConfigFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_sensor_config, container, false);
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        initViews();
    }

    @Override
    public void onDestroyView() {

        mEditTextSensorPollInterval = null;

        super.onDestroyView();
    }

    private void initViews() {
        mEditTextSensorPollInterval = getView().findViewById(R.id.editTextSensorPollInterval);
        updateSensorPollInterval(mPresenter.getSensorPollInterval());
        mEditTextSensorPollInterval.addTextChangedListener(new TextChangeWatcher() {

            @Override
            public void afterTextChanged(Editable editable) {
                mPresenter.setSensorPollInterval(editable.toString());
            }
        });
    }

    @Override
    public void updateSensorPollInterval(String pollInterval) {
        mEditTextSensorPollInterval.setText(pollInterval);
    }

    @Override
    public void setModel(SensorConfigModel model) {
        mPresenter = new SensorConfigPresenter(model, this);
    }

    @Override
    public SensorConfigModel getModel() {
        return mPresenter.getModel();
    }
}
