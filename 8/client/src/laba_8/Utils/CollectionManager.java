package laba_8.Utils;

import laba_8.Classes.Organization;

import java.time.LocalDateTime;
import java.util.*;

public class CollectionManager {
    private static List<Organization> organizationCollection = Collections.synchronizedList(new ArrayList<Organization>());
    private static LocalDateTime initializationDate;


    public static void initializeCollection() {
        organizationCollection = new ArrayList<>();
        initializationDate = LocalDateTime.now();
    }

    public static List<Organization> getCollection() {
        return organizationCollection;
    }

    public static void addJson(Organization organization) {
        organizationCollection.add(organization);
    }

    public static String information() {
        return "Тип коллекции: " + organizationCollection.getClass().getSimpleName() + ".\nДата инициализации: " + initializationDate + ".\nКоличество элементов: " + organizationCollection.size() + ".";
    }


    public static void updateElement(Organization newOrganization, int ID) {
        organizationCollection.forEach(organization -> {
            if (organization.getId() == ID) {
                organization.setName(newOrganization.getName());
                organization.setCoordinates(newOrganization.getCoordinates());
                organization.setAnnualTurnover(newOrganization.getAnnualTurnover());
                organization.setType(newOrganization.getType());
                organization.setOfficialAddress(newOrganization.getOfficialAddress());
            }
        });
    }

    public static void removeElement(int ID) {
        organizationCollection.removeIf(organization -> (organization.getId() == ID));
    }

    public static void clearCollection() {
        organizationCollection.clear();
    }

    public static String printFieldDescendingAnnualTurnover() {
        StringBuilder str = new StringBuilder();
        Double[] arr = new Double[organizationCollection.size()];
        int i = 0;
        for (Organization organization : organizationCollection) {
            arr[i] = organization.getAnnualTurnover();
            i++;
        }
        Arrays.sort(arr);
        str.append("Годовые обороты компаний в порядке убывания: ");
        for (int k = 0; k < arr.length; k++) {
            if (k == arr.length - 1) {
                str.append(String.format("%.3f. \n", arr[arr.length - (k + 1)]));
            } else {
                str.append(String.format("%.3f, ", arr[arr.length - (k + 1)]));
            }
        }
        return String.valueOf(str);
    }
}