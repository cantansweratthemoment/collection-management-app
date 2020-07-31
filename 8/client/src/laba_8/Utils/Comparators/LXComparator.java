package laba_8.Utils.Comparators;

import laba_8.Classes.Organization;

import java.util.Comparator;

public class LXComparator implements Comparator<Organization> {
    @Override
    public int compare(Organization o1, Organization o2) {
        String q = String.valueOf(o1.getOfficialAddress().getTown().getX());
        String w = String.valueOf(o2.getOfficialAddress().getTown().getX());
        return q.compareTo(w);
    }
}
