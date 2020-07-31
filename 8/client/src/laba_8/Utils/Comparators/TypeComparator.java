package laba_8.Utils.Comparators;

import laba_8.Classes.Organization;

import java.util.Comparator;

public class TypeComparator implements Comparator<Organization> {
    @Override
    public int compare(Organization o1, Organization o2) {
        String q = String.valueOf(o1.getType());
        String w = String.valueOf(o2.getType());
        return o1.compareTo(o2);
    }
}