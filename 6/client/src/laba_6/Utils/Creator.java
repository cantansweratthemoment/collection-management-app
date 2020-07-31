package laba_6.Utils;

import laba_6.StoredClasses.*;

import java.util.Scanner;

/**
 * Создатель: создаёт организации.
 */
public class Creator {
    /**
     * Метод - создание организации с помощью консоли.
     *
     * @return созданная организация.
     */
    public static Organization createOrganizationFromConsole(Scanner sc) {
        String name = Readers.readName(sc);
        Double cX = Readers.readCoordinatesX(sc);
        double cY = Readers.readCoordinatesY(sc);
        Double annualTurnover = Readers.readAnnualTurnover(sc);
        OrganizationType type = Readers.readType(sc);
        String street = Readers.readStreet(sc);
        String zipCode = Readers.readZipCode(sc);
        float lX = Readers.readLocation("X", sc);
        float lY = Readers.readLocation("Y", sc);
        String locationName = Readers.readTownName(sc);
        return new Organization(name, new Coordinates(cX, cY), annualTurnover, type, new Address(street, zipCode, new Location(lX, lY, locationName)));
    }
}