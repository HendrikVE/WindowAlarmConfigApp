package de.vanappsteer.windowalarmconfig.inputvalidator;

public class PortInputValidator implements InputValidator<Integer> {

    @Override
    public boolean validate(Integer port) {

        return port >= 0 && port <= 65535;
    }

    @Override
    public String getValidRangeString() {
        return "Needs to be between 0 and 65535";
    }
}
