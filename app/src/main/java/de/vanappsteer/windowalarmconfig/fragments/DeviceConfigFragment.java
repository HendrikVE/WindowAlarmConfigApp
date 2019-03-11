package de.vanappsteer.windowalarmconfig.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.vanappsteer.windowalarmconfig.R;
import de.vanappsteer.windowalarmconfig.presenter.DeviceConfigPresenter;
import de.vanappsteer.windowalarmconfig.interfaces.DeviceConfigView;
import de.vanappsteer.windowalarmconfig.models.DeviceConfigModel;
import de.vanappsteer.windowalarmconfig.util.TextChangeWatcher;

public class DeviceConfigFragment extends Fragment implements DeviceConfigView {

    private TextInputEditText mEditTextDeviceRoom;
    private TextInputEditText mEditTextDeviceID;

    private DeviceConfigPresenter mPresenter;

    public DeviceConfigFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_device_config, container, false);
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        initViews();
    }

    @Override
    public void onDestroyView() {

        mEditTextDeviceRoom = null;
        mEditTextDeviceID = null;

        super.onDestroyView();
    }

    private void initViews() {

        mEditTextDeviceRoom = getView().findViewById(R.id.editTextDeviceRoom);
        updateDeviceRoom(mPresenter.getDeviceRoom());
        mEditTextDeviceRoom.addTextChangedListener(new TextChangeWatcher() {

            @Override
            public void afterTextChanged(Editable editable) {
                mPresenter.setDeviceRoom(editable.toString());
            }
        });

        mEditTextDeviceID = getView().findViewById(R.id.editTextDeviceID);
        updateDeviceId(mPresenter.getDeviceId());
        mEditTextDeviceID.addTextChangedListener(new TextChangeWatcher() {

            @Override
            public void afterTextChanged(Editable editable) {
                mPresenter.setDeviceId(editable.toString());
            }
        });
    }

    @Override
    public void updateDeviceRoom(String room) {
        mEditTextDeviceRoom.setText(room);
    }

    @Override
    public void updateDeviceId(String id) {
        mEditTextDeviceID.setText(id);
    }

    @Override
    public void setModel(DeviceConfigModel model) {
        mPresenter = new DeviceConfigPresenter(model, this);
    }

    @Override
    public DeviceConfigModel getModel() {
        return mPresenter.getModel();
    }
}
