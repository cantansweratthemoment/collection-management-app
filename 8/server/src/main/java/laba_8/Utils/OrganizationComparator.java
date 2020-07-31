package laba_8.Utils;

import laba_8.Classes.Organization;

import java.util.Comparator;

public class OrganizationComparator implements Comparator<Organization> {
    @Override
    public int compare(Organization organization, Organization t1) {
        return organization.getOfficialAddress().getTown().getName().compareTo(t1.getOfficialAddress().getTown().getName());
    }
}
