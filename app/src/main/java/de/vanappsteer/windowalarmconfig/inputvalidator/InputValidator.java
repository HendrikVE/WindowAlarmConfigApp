package de.vanappsteer.windowalarmconfig.inputvalidator;

public interface InputValidator<T> {

    boolean validate(T object);

    String getValidRangeString();

}
