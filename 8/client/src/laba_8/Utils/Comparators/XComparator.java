package laba_8.Utils.Comparators;

import laba_8.Classes.Organization;

import java.util.Comparator;

public class XComparator implements Comparator<Organization> {
    @Override
    public int compare(Organization o1, Organization o2) {
        String x = String.valueOf(o1.getCoordinates().getX());
        String y = String.valueOf(o2.getCoordinates().getX());
        return x.compareTo(y);
    }
}
