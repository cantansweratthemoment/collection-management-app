package laba_8.Utils.Comparators;

import laba_8.Classes.Organization;

import java.util.Comparator;

public class YComparator implements Comparator<Organization> {
    @Override
    public int compare(Organization o1, Organization o2) {
        String x = String.valueOf(o1.getCoordinates().getY());
        String y = String.valueOf(o2.getCoordinates().getY());
        return x.compareTo(y);
    }
}

