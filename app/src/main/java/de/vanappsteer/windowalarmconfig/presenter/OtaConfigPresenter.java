package de.vanappsteer.windowalarmconfig.presenter;

import de.vanappsteer.windowalarmconfig.interfaces.ConfigController;
import de.vanappsteer.windowalarmconfig.interfaces.OtaConfigView;
import de.vanappsteer.windowalarmconfig.models.OtaConfigModel;

public class OtaConfigPresenter implements ConfigController<OtaConfigModel> {

    private OtaConfigModel mModel;
    private OtaConfigView mView;

    public OtaConfigPresenter(OtaConfigModel model, OtaConfigView view) {
        mModel = model;
        mView = view;
    }

    @Override
    public void updateView() {
        mView.updateOtaServerAddress(mModel.getOtaServerAddress());
        mView.updateOtaFilename(mModel.getOtaFilename());
        mView.updateOtaUsername(mModel.getOtaUsername());
        mView.updateOtaPassword(mModel.getOtaPassword());
    }

    @Override
    public OtaConfigModel getModel() {
        return mModel;
    }


    /* BEGIN GETTER */
    public String getOtaServerAddress() {
        return mModel.getOtaServerAddress();
    }

    public String getOtaFilename() {
        return mModel.getOtaFilename();
    }

    public String getOtaUsername() {
        return mModel.getOtaUsername();
    }

    public String getOtaPassword() {
        return mModel.getOtaPassword();
    }
    /* END GETTER */


    /* BEGIN SETTER */
    public void setOtaServerAddress(String serverAddress) {
        mModel.setOtaServerAddress(serverAddress);
    }

    public void setOtaFilename(String filename) {
        mModel.setOtaFilename(filename);
    }

    public void setOtaUsername(String username) {
        mModel.setOtaUsername(username);
    }

    public void setOtaPassword(String password) {
        mModel.setOtaPassword(password);
    }
    /* END SETTER */
}
