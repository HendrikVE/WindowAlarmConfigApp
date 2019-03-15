package de.vanappsteer.windowalarmconfig.presenter;

import de.vanappsteer.windowalarmconfig.interfaces.ConfigController;
import de.vanappsteer.windowalarmconfig.interfaces.MqttConfigView;
import de.vanappsteer.windowalarmconfig.models.MqttConfigModel;

public class MqttConfigPresenter extends ConfigController<MqttConfigModel> {

    private MqttConfigModel mModel;
    private MqttConfigView mView;

    public MqttConfigPresenter(MqttConfigModel model, MqttConfigView view) {
        mModel = model;
        mView = view;
    }

    @Override
    public void updateView() {
        mView.updateMqttUsername(mModel.getMqttUsername());
        mView.updateMqttPassword(mModel.getMqttUsername());
        mView.updateMqttBrokerAddress(mModel.getMqttBrokerAddress());
        mView.updateMqttBrokerPort(mModel.getMqttBrokerPort());
    }

    @Override
    public MqttConfigModel getModel() {
        return mModel;
    }


    /* BEGIN GETTER */
    public String getMqttUsername() {
        return mModel.getMqttUsername();
    }

    public String getMqttPassword() {
        return mModel.getMqttPassword();
    }

    public String getMqttBrokerAddress() {
        return mModel.getMqttBrokerAddress();
    }

    public String getMqttBrokerPort() {
        return mModel.getMqttBrokerPort();
    }
    /* END GETTER */


    /* BEGIN SETTER */
    public void setMqttUsername(String username) {
        mModel.setMqttUsername(username);
    }

    public void setMqttPassword(String password) {
        mModel.setMqttPassword(password);
    }

    public void setMqttBrokerAddress(String address) {
        mModel.setMqttBrokerAddress(address);
    }

    public void setMqttBrokerPort(String port) {
        mModel.setMqttBrokerPort(port);
    }
    /* END SETTER */
}
