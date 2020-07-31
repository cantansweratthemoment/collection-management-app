package laba_7.Utils;

import laba_7.Classes.Organization;

import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Arrays;

public class CollectionManager {
    private static ArrayDeque<Organization> arrayDeque;
    private static LocalDateTime initializationDate;

    public static ArrayDeque<Organization> getCollection() {
        return arrayDeque;
    }

    public static void addJson(Organization organization) {
        organization.setId(IDGenerator.generateID());
        arrayDeque.add(organization);
    }

    public static void information() {
        System.out.println("Тип коллекции: " + arrayDeque.getClass().getSimpleName() + ".\nДата инициализации: " + initializationDate + ".\nКоличество элементов: " + arrayDeque.size() + ".");
    }

    public static void fullInformation() {
        if (arrayDeque.size() == 0) {
            System.out.println("Коллекция пуста.");
        } else
            arrayDeque.forEach(CollectionUtils::organizationInfo);
    }

    public static void addOrganization(Organization organization) {
        arrayDeque.add(organization);
    }

    public static void updateElement(Organization newOrganization, int ID) {
        arrayDeque.forEach(organization -> {
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
        for (Organization organization : arrayDeque) {
            if (organization.getId() == ID) {
                arrayDeque.remove(organization);
                break;
            }
        }
    }

    public static void clearCollection() {
        arrayDeque.clear();
    }

    public static void removeHead() {
        if (arrayDeque.size() != 0) {
            CollectionUtils.organizationInfo(arrayDeque.getFirst());
            arrayDeque.removeFirst();
        } else {
            System.out.println("В этой коллекции нет элементов.");
        }
    }

    public static void addIfMin(Organization organization) {
        if (arrayDeque.size() == 0) {
            addOrganization(organization);
        } else {
            boolean flag = true;
            for (Organization organization1 : arrayDeque) {
                if (organization1.compareTo(organization) < 0) {
                    flag = false;
                    break;
                }
            }
            if (flag) addOrganization(organization);
        }
    }

    public static void filterByAnnualTurnover(Double annualTurnover) {
        arrayDeque.forEach(organization -> {
            if (organization.getAnnualTurnover().equals(annualTurnover)) {
                CollectionUtils.organizationInfo(organization);
            }
        });
    }

    public static void filterStartsWithName(String name) {
        arrayDeque.forEach(organization -> {
            if (organization.getName().startsWith(name)) {
                CollectionUtils.organizationInfo(organization);
            }
        });
    }

    public static void printFieldDescendingAnnualTurnover() {
        Double[] arr = new Double[arrayDeque.size()];
        int i = 0;
        for (Organization organization : arrayDeque) {
            arr[i] = organization.getAnnualTurnover();
            i++;
        }
        Arrays.sort(arr);
        System.out.println("Годовые обороты компаний в порядке убывания: ");
        for (int ii = 0; ii < arr.length; ii++) {
            if (ii == arr.length - 1) {
                System.out.printf("%.3f. \n", arr[arr.length - (ii + 1)]);
            } else {
                System.out.printf("%.3f, ", arr[arr.length - (ii + 1)]);
            }
        }
    }
}