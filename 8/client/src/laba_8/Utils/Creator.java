package laba_8.Utils;

import laba_8.Client.Receiver;
import laba_8.Classes.*;

public class Creator {

    public static Organization create(String name, Double x, Double y, Double annualTurnover, OrganizationType type, String street, String zipcode, Float lx, Float ly, String town) {
        return new Organization(name, new Coordinates(x, y), annualTurnover, type, new Address(street, zipcode, new Location(lx, ly, town)), Receiver.myLogin, Receiver.myColor);
    }
}