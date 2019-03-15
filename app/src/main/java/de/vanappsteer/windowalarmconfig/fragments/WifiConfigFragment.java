package de.vanappsteer.windowalarmconfig.fragments;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.vanappsteer.windowalarmconfig.R;
import de.vanappsteer.windowalarmconfig.presenter.WifiConfigPresenter;
import de.vanappsteer.windowalarmconfig.interfaces.WifiConfigView;
import de.vanappsteer.windowalarmconfig.models.WifiConfigModel;
import de.vanappsteer.windowalarmconfig.util.TextChangeWatcher;

public class WifiConfigFragment extends Fragment implements WifiConfigView {

    private TextInputEditText mEditTextWifiSsid;
    private TextInputEditText mEditTextWifiPassword;

    private WifiConfigPresenter mPresenter;

    public WifiConfigFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_wifi_config, container, false);
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        initViews();
    }

    @Override
    public void onDestroyView() {

        mEditTextWifiSsid = null;
        mEditTextWifiPassword = null;

        super.onDestroyView();
    }

    private void initViews() {
        mEditTextWifiSsid = getView().findViewById(R.id.editTextWifiSsid);
        updateWifiSsid(mPresenter.getSsid());
        mEditTextWifiSsid.addTextChangedListener(new TextChangeWatcher() {

            @Override
            public void afterTextChanged(Editable editable) {
                mPresenter.setWifiSsid(editable.toString());
            }
        });

        mEditTextWifiPassword = getView().findViewById(R.id.editTextWifiPassword);
        updateWifiPassword(mPresenter.getPassword());
        mEditTextWifiPassword.addTextChangedListener(new TextChangeWatcher() {

            @Override
            public void afterTextChanged(Editable editable) {
                mPresenter.setWifiPassword(editable.toString());
            }
        });
    }

    @Override
    public void updateWifiSsid(String ssid) {
        mEditTextWifiSsid.setText(ssid);
    }

    @Override
    public void updateWifiPassword(String password) {
        mEditTextWifiPassword.setText(password);
    }

    @Override
    public void setModel(WifiConfigModel model) {
        mPresenter = new WifiConfigPresenter(model, this);
    }

    @Override
    public WifiConfigModel getModel() {
        return mPresenter.getModel();
    }

    @Override
    public void updateDisplayedErrors() {
        if ("".equals(mPresenter.getSsid())) {
            mEditTextWifiSsid.setError(getString(R.string.text_cant_be_empty));
        }
        else {
            mEditTextWifiSsid.setError(null);
        }

        if ("".equals(mPresenter.getPassword())) {
            mEditTextWifiPassword.setError(getString(R.string.text_cant_be_empty));
        }
        else {
            mEditTextWifiPassword.setError(null);
        }
    }
}
