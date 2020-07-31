package laba_7.Utils;

import laba_7.Classes.Organization;

public class CollectionUtils {
    public static void organizationInfo(Organization organization) {
        System.out.println("ID: " + organization.getId() + ".");
        System.out.println("Название организации: " + organization.getName());
        System.out.println("Координаты: X=" + organization.getCoordinates().getX() + ", Y=" + organization.getCoordinates().getY() + ".");
        System.out.println("Время создания: " + organization.getCreationDate() + ".");
        System.out.println("Годовой оборот организации: " + organization.getAnnualTurnover() + ".");
        System.out.println("Тип организации: " + organization.getType() + ".");
        System.out.println("Адрес организации: улица " + organization.getOfficialAddress().getStreet() + ", почтовый индекс " + organization.getOfficialAddress().getZipCode() + ", город " + organization.getOfficialAddress().getTown().getName() + " (X=" + organization.getOfficialAddress().getTown().getX() + ", Y=" + organization.getOfficialAddress().getTown().getY() + ").");
    }

    public static boolean doesExist(int ID) {
        for (Organization organization : CollectionManager.getCollection()) {
            return organization.getId() == (ID);
        }
        return false;
    }
}
