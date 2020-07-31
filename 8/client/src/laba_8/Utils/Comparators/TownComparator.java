package laba_8.Utils.Comparators;

import laba_8.Classes.Organization;

import java.util.Comparator;

public class TownComparator implements Comparator<Organization> {
    @Override
    public int compare(Organization o1, Organization o2) {
        return o1.getOfficialAddress().getTown().getName().compareTo(o1.getOfficialAddress().getTown().getName());
    }
}
