package laba_6.Utils.CollectionUtils;

import laba_6.StoredClasses.Organization;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Менеджер коллекций: описывает команды, связанные с коллекцией.
 */

public class CollectionManager {
    /**
     * Поле - коллекция.
     */
    private static ArrayDeque<Organization> arrayDeque;
    /**
     * Поле - время инициализации коллекции.
     */
    private static LocalDateTime initializationDate;

    /**
     * Метод - инициализация коллекции.
     */
    public static void initializeCollection() {
        if (arrayDeque == null) {
            arrayDeque = new ArrayDeque<>();
            initializationDate = LocalDateTime.now();
        }
    }

    /**
     * Метод - возвращаение коллеции.
     *
     * @return array Deque - коллекция
     */
    public static ArrayDeque<Organization> getCollection() {
        return arrayDeque;
    }

    /**
     * Метод - запись организации в Json-файл.
     *
     * @param organization - записываемая организация.
     */
    public static void addJson(Organization organization) {
        organization.setId(IDGenerator.generateID());
        arrayDeque.add(organization);
    }

    /**
     * Метод - вывод информации о коллекции.
     */
    public static String information() {
        return "Тип коллекции: " + arrayDeque.getClass().getSimpleName() + ".\nДата инициализации: " + initializationDate + ".\nКоличество элементов: " + arrayDeque.size() + ".";
    }

    /**
     * Метод - вывод полной информации о коллекции и её элементах.
     *
     * @return
     */
    public static String fullInformation() throws IOException {
        StringBuilder str = new StringBuilder();
        if (arrayDeque.size() == 0) {
            str.append("Коллекция пуста.");
        } else {
            Organization[] arr = arrayDeque.toArray(new Organization[0]);
            Arrays.sort(arr, new OrganizationComparator());
            clearCollection();
            arrayDeque.addAll(Arrays.asList(arr));
            arrayDeque.forEach(organization -> {
               str.append(CollectionUtils.organizationInfo(organization));
           });
        }
        return String.valueOf(str);
    }

    /**
     * Метод - добавление организации в коллекцию.
     *
     * @param organization - добавляемая организация.
     */
    public static String addOrganization(Organization organization) {
        organization.setId(IDGenerator.generateID());
        arrayDeque.add(organization);
        return "Организация добавлена в коллекцию.";
    }

    /**
     * Метод - обновление значения элемента коллекции.
     *
     * @param newOrganization - новое значние.
     * @param ID              - ID обновляемого элемента.
     */
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

    /**
     * Метод - удаление элемента.
     *
     * @param ID - ID удаляемого элемента.
     */
    public static void removeElement(int ID) {
        arrayDeque.forEach(organization -> {
            if (organization.getId()==ID){
                arrayDeque.remove(organization);
            }
        });
    }

    /**
     * Метод - очищение коллекции.
     */
    public static void clearCollection() {
        arrayDeque.clear();
    }

    /**
     * Метод - удаление первого элемента коллекции с выводом.
     *
     * @return
     */
    public static String removeHead() throws IOException {
        StringBuilder str = new StringBuilder();
        if (arrayDeque.size() != 0) {
            str.append(CollectionUtils.organizationInfo(arrayDeque.getFirst()));
            arrayDeque.removeFirst();
        } else {
            str.append("В этой коллекции нет элементов.");
        }
        return String.valueOf(str);
    }

    /**
     * Метод - добавление элемента с условием минимальноести.
     *
     * @param organization добавляемый элемент.
     * @return
     */
    public static String addIfMin(Organization organization) {
        if (arrayDeque.size() == 0) {
            addOrganization(organization);
            return "Организация добавлена.";
        } else {
            AtomicBoolean flag = new AtomicBoolean(true);
            arrayDeque.forEach(organization1 -> {
                if (organization1.compareTo(organization)<0){
                    flag.set(false);
                }
            });
            if (flag.get()) {
                addOrganization(organization);
                return "Организация добавлена.";
            } else {
                return "Организация не добавлена.";
            }
        }
    }

    /**
     * Метод - выбор элемента по годовому обороту.
     *
     * @param annualTurnover - годовой оборот, по которому выбор.
     * @return
     */
    public static String filterByAnnualTurnover(Double annualTurnover) throws IOException {
        StringBuilder str = new StringBuilder();
        Organization[] arr = arrayDeque.toArray(new Organization[0]);
        Arrays.sort(arr, new OrganizationComparator());
        clearCollection();
        arrayDeque.addAll(Arrays.asList(arr));
        arrayDeque.forEach(organization -> {
            if (organization.getAnnualTurnover().equals(annualTurnover)) {
                str.append(CollectionUtils.organizationInfo(organization));
            }
        });
        return String.valueOf(str);
    }

    /**
     * Метод - выбор элемента по началу имени.
     *
     * @param name - начало имени.
     * @return
     */
    public static String filterStartsWithName(String name) throws IOException {
        StringBuilder str = new StringBuilder();
        Organization[] arr = arrayDeque.toArray(new Organization[0]);
        Arrays.sort(arr, new OrganizationComparator());
        clearCollection();
        arrayDeque.addAll(Arrays.asList(arr));
        arrayDeque.forEach(organization -> {
            if (organization.getName().startsWith(name)) {
                str.append(CollectionUtils.organizationInfo(organization));
            }
        });
        return String.valueOf(str);
    }

    /**
     * Метод - вывод годовых оборотов в порядке убывания.
     *
     * @return
     */
    public static String printFieldDescendingAnnualTurnover() throws IOException {
        StringBuilder str = new StringBuilder();
        Double[] arr = new Double[arrayDeque.size()];
        int i = 0;
        for (Organization organization : arrayDeque) {
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