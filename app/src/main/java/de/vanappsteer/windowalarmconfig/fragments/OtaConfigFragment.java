package de.vanappsteer.windowalarmconfig.fragments;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.vanappsteer.windowalarmconfig.R;
import de.vanappsteer.windowalarmconfig.presenter.OtaConfigPresenter;
import de.vanappsteer.windowalarmconfig.interfaces.OtaConfigView;
import de.vanappsteer.windowalarmconfig.models.OtaConfigModel;
import de.vanappsteer.windowalarmconfig.util.TextChangeWatcher;

public class OtaConfigFragment extends Fragment implements OtaConfigView {

    private TextInputEditText mEditTextOtaServerAddress;
    private TextInputEditText mEditTextOtaFilename;
    private TextInputEditText mEditTextOtaUsername;
    private TextInputEditText mEditTextOtaPassword;

    private OtaConfigPresenter mPresenter;

    public OtaConfigFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_ota_config, container, false);
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        initViews();
    }

    @Override
    public void onDestroyView() {

        mEditTextOtaServerAddress = null;
        mEditTextOtaFilename = null;
        mEditTextOtaUsername = null;
        mEditTextOtaPassword = null;

        super.onDestroyView();
    }

    private void initViews() {
        mEditTextOtaServerAddress = getView().findViewById(R.id.editTextOtaServerAddress);
        updateOtaServerAddress(mPresenter.getOtaServerAddress());
        mEditTextOtaServerAddress.addTextChangedListener(new TextChangeWatcher() {

            @Override
            public void afterTextChanged(Editable editable) {
                mPresenter.setOtaServerAddress(editable.toString());
            }
        });

        mEditTextOtaFilename = getView().findViewById(R.id.editTextOtaFilename);
        updateOtaFilename(mPresenter.getOtaFilename());
        mEditTextOtaFilename.addTextChangedListener(new TextChangeWatcher() {

            @Override
            public void afterTextChanged(Editable editable) {
                mPresenter.setOtaFilename(editable.toString());
            }
        });

        mEditTextOtaUsername = getView().findViewById(R.id.editTextOtaUsername);
        updateOtaUsername(mPresenter.getOtaUsername());
        mEditTextOtaUsername.addTextChangedListener(new TextChangeWatcher() {

            @Override
            public void afterTextChanged(Editable editable) {
                mPresenter.setOtaUsername(editable.toString());
            }
        });

        mEditTextOtaPassword = getView().findViewById(R.id.editTextOtaPassword);
        updateOtaPassword(mPresenter.getOtaPassword());
        mEditTextOtaPassword.addTextChangedListener(new TextChangeWatcher() {

            @Override
            public void afterTextChanged(Editable editable) {
                mPresenter.setOtaPassword(editable.toString());
            }
        });
    }

    @Override
    public void updateOtaServerAddress(String address) {
        mEditTextOtaServerAddress.setText(address);
    }

    @Override
    public void updateOtaFilename(String filename) {
        mEditTextOtaFilename.setText(filename);
    }

    @Override
    public void updateOtaUsername(String username) {
        mEditTextOtaUsername.setText(username);
    }

    @Override
    public void updateOtaPassword(String password) {
        mEditTextOtaPassword.setText(password);
    }

    @Override
    public void setModel(OtaConfigModel model) {
        mPresenter = new OtaConfigPresenter(model, this);
    }

    @Override
    public OtaConfigModel getModel() {
        return mPresenter.getModel();
    }

    @Override
    public void updateDisplayedErrors() {
        if ("".equals(mPresenter.getOtaServerAddress())) {
            mEditTextOtaServerAddress.setError(getString(R.string.text_cant_be_empty));
        }
        else {
            mEditTextOtaServerAddress.setError(null);
        }

        if ("".equals(mPresenter.getOtaFilename())) {
            mEditTextOtaFilename.setError(getString(R.string.text_cant_be_empty));
        }
        else {
            mEditTextOtaFilename.setError(null);
        }

        if ("".equals(mPresenter.getOtaUsername())) {
            mEditTextOtaUsername.setError(getString(R.string.text_cant_be_empty));
        }
        else {
            mEditTextOtaUsername.setError(null);
        }

        if ("".equals(mPresenter.getOtaPassword())) {
            mEditTextOtaPassword.setError(getString(R.string.text_cant_be_empty));
        }
        else {
            mEditTextOtaPassword.setError(null);
        }
    }
}
