package laba_6.Utils.CollectionUtils;

import laba_6.StoredClasses.Organization;


/**
 * Утилиты для работы с коллецией.
 */
public class CollectionUtils {
    /**
     * Метод - вывод полной информации об организации.
     *
     * @param organization - организация.
     */
    public static String organizationInfo(Organization organization) {
        return ("ID: " + organization.getId() + ".\n" +
                "Название организации: " + organization.getName() + "\n" +
                "Координаты: X=" + organization.getCoordinates().getX() + ", Y=" + organization.getCoordinates().getY() + "." + "\n" +
                "Время создания: " + organization.getCreationDate() + "." + "\n" +
                "Годовой оборот организации: " + organization.getAnnualTurnover() + "." + "\n" +
                "Тип организации: " + organization.getType() + "." + "\n" +
                "Адрес организации: улица " + organization.getOfficialAddress().getStreet() + ", почтовый индекс " + organization.getOfficialAddress().getZipCode() + ", город " + organization.getOfficialAddress().getTown().getName() + " (X=" + organization.getOfficialAddress().getTown().getX() + ", Y=" + organization.getOfficialAddress().getTown().getY() + ")." + "\n");
    }

    /**
     * Метод - проверка на существование организации с данным ID.
     *
     * @param ID - данный ID.
     * @return правда/ложь
     */
    public static boolean doesExist(int ID) {
        for (Organization organization : CollectionManager.getCollection()) {
            return organization.getId() == (ID);
        }
        return false;
    }
}
