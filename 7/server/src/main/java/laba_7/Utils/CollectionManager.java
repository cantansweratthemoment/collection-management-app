package laba_7.Utils;

import laba_7.Classes.Organization;
import laba_7.Database.DatabaseCommunicator;
import laba_7.Database.OrganizationsBase;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class CollectionManager {
    // private static List<HumanBeing> humans = Collections.synchronizedList(new ArrayList<HumanBeing>());
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
        organization.setId(IDGenerator.generateID());
        organizationCollection.add(organization);
    }

    public static String information() {
        return "Тип коллекции: " + organizationCollection.getClass().getSimpleName() + ".\nДата инициализации: " + initializationDate + ".\nКоличество элементов: " + organizationCollection.size() + ".";
    }

    public static String fullInformation() {
        StringBuilder str = new StringBuilder();
        if (organizationCollection.size() == 0) {
            str.append("Коллекция пуста.");
        } else {
            Organization[] arr = organizationCollection.toArray(new Organization[0]);;
            clearCollection();
            organizationCollection.addAll(Arrays.asList(arr));
            organizationCollection.forEach(organization -> str.append(CollectionUtils.organizationInfo(organization)));
        }
        return String.valueOf(str);
    }

    public static String addOrganization() {
        try {
            OrganizationsBase.loadCollection(getCollection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Организация добавлена в коллекцию.";
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
        organizationCollection.removeIf(organization ->(organization.getId() == ID));
    }

    public static void clearCollection() {
        organizationCollection.clear();
    }

    public static void clearCollectionOnDataBase(String owner){
        OrganizationsBase.clearCollectionOnDataBase(owner);
    }

    public static String removeHead() {
        StringBuilder str = new StringBuilder();
        if (organizationCollection.size() != 0) {
            str.append(CollectionUtils.organizationInfo(organizationCollection.get(0)));
            try {
                DatabaseCommunicator.getOrganizations().deleteOrganizationFromDataBase(organizationCollection.get(0).getId());
            } catch (SQLException e) {
            }
            organizationCollection.remove(0);
        } else {
            str.append("В этой коллекции нет элементов.");
        }
        return String.valueOf(str);
    }

    public static String addIfMin(Organization organization) {
        if (organizationCollection.size() == 0) {
            addOrganization();
            return "Организация добавлена.";
        } else {
            AtomicBoolean flag = new AtomicBoolean(true);
            organizationCollection.forEach(organization1 -> {
                if (organization1.compareTo(organization) < 0) {
                    flag.set(false);
                }
            });
            if (flag.get()) {
                DatabaseCommunicator.getOrganizations().addOrganizationToTheBase(organization,-1);
                addOrganization();
                return "Организация добавлена.";
            } else {
                return "Организация не добавлена.";
            }
        }
    }

    public static String filterByAnnualTurnover(Double annualTurnover) {
        StringBuilder str = new StringBuilder();
        Organization[] arr = organizationCollection.toArray(new Organization[0]);
        Arrays.sort(arr, new OrganizationComparator());
        clearCollection();
        organizationCollection.addAll(Arrays.asList(arr));
        organizationCollection.forEach(organization -> {
            if (organization.getAnnualTurnover().equals(annualTurnover)) {
                str.append(CollectionUtils.organizationInfo(organization));
            }
        });
        return String.valueOf(str);
    }

    public static String filterStartsWithName(String name) {
        StringBuilder str = new StringBuilder();
        Organization[] arr = organizationCollection.toArray(new Organization[0]);
        Arrays.sort(arr, new OrganizationComparator());
        clearCollection();
        organizationCollection.addAll(Arrays.asList(arr));
        organizationCollection.forEach(organization -> {
            if (organization.getName().startsWith(name)) {
                str.append(CollectionUtils.organizationInfo(organization));
            }
        });
        return String.valueOf(str);
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