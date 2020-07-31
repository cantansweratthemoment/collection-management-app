package laba_7.Classes;

import java.io.Serializable;

public enum OrganizationType implements Serializable {
    COMMERCIAL("COMMERCIAL"),
    PUBLIC("PUBLIC"),
    TRUST("TRUST"),
    PRIVATE_LIMITED_COMPANY("PRIVATE_LIMITED_COMPANY"),
    OPEN_JOINT_STOCK_COMPANY("OPEN_JOINT_STOCK_COMPANY");
    private String string;

    OrganizationType(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}