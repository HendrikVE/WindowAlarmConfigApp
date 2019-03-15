package de.vanappsteer.windowalarmconfig.fragments;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.vanappsteer.windowalarmconfig.R;
import de.vanappsteer.windowalarmconfig.inputvalidator.PortInputValidator;
import de.vanappsteer.windowalarmconfig.presenter.MqttConfigPresenter;
import de.vanappsteer.windowalarmconfig.interfaces.MqttConfigView;
import de.vanappsteer.windowalarmconfig.models.MqttConfigModel;
import de.vanappsteer.windowalarmconfig.util.LoggingUtil;
import de.vanappsteer.windowalarmconfig.util.TextChangeWatcher;

public class MqttConfigFragment extends Fragment implements MqttConfigView {

    private TextInputEditText mEditTextMqttUsername;
    private TextInputEditText mEditTextMqttPassword;
    private TextInputEditText mEditTextMqttBrokerAddress;
    private TextInputEditText mEditTextMqttBrokerPort;

    private PortInputValidator mPortInputValidator = new PortInputValidator();

    private MqttConfigPresenter mPresenter;

    public MqttConfigFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_mqtt_config, container, false);
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        initViews();
    }

    @Override
    public void onDestroyView() {

        mEditTextMqttUsername = null;
        mEditTextMqttPassword = null;
        mEditTextMqttBrokerAddress = null;
        mEditTextMqttBrokerPort = null;

        super.onDestroyView();
    }

    private void initViews() {

        mEditTextMqttUsername = getView().findViewById(R.id.editTextMqttUsername);
        updateMqttUsername(mPresenter.getMqttUsername());
        mEditTextMqttUsername.addTextChangedListener(new TextChangeWatcher() {

            @Override
            public void afterTextChanged(Editable editable) {
                mPresenter.setMqttUsername(editable.toString());
            }
        });

        mEditTextMqttPassword = getView().findViewById(R.id.editTextMqttPassword);
        updateMqttPassword(mPresenter.getMqttPassword());
        mEditTextMqttPassword.addTextChangedListener(new TextChangeWatcher() {

            @Override
            public void afterTextChanged(Editable editable) {
                mPresenter.setMqttPassword(editable.toString());
            }
        });

        mEditTextMqttBrokerAddress = getView().findViewById(R.id.editTextMqttBrokerAddress);
        updateMqttBrokerAddress(mPresenter.getMqttBrokerAddress());
        mEditTextMqttBrokerAddress.addTextChangedListener(new TextChangeWatcher() {

            @Override
            public void afterTextChanged(Editable editable) {
                mPresenter.setMqttBrokerAddress(editable.toString());
            }
        });

        mEditTextMqttBrokerPort = getView().findViewById(R.id.editTextMqttBrokerPort);
        updateMqttBrokerPort(mPresenter.getMqttBrokerPort());
        mEditTextMqttBrokerPort.addTextChangedListener(new TextChangeWatcher() {

            @Override
            public void afterTextChanged(Editable editable) {

                String inputPort = editable.toString();

                mPresenter.setMqttBrokerPort(inputPort);

                boolean valid;
                try {
                    Integer port = Integer.parseInt(inputPort);
                    valid = mPortInputValidator.validate(port);
                }
                catch(NumberFormatException e) {
                    LoggingUtil.error(e.getMessage());
                    valid = false;
                }

                if (! valid) {
                    mEditTextMqttBrokerPort.setError(mPortInputValidator.getValidRangeString());
                }
                else {
                    mEditTextMqttBrokerPort.setError(null);
                }
            }
        });
    }

    @Override
    public void updateMqttUsername(String username) {
        mEditTextMqttUsername.setText(username);
    }

    @Override
    public void updateMqttPassword(String password) {
        mEditTextMqttPassword.setText(password);
    }

    @Override
    public void updateMqttBrokerAddress(String address) {
        mEditTextMqttBrokerAddress.setText(address);
    }

    @Override
    public void updateMqttBrokerPort(String port) {
        mEditTextMqttBrokerPort.setText(port);
    }

    @Override
    public void setModel(MqttConfigModel model) {
        mPresenter = new MqttConfigPresenter(model, this);
    }

    @Override
    public MqttConfigModel getModel() {
        return mPresenter.getModel();
    }
}
