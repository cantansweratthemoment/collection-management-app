package laba_6.Utils.CollectionUtils;

import laba_6.StoredClasses.Organization;

import java.util.Comparator;

public class OrganizationComparator implements Comparator<Organization> {
    @Override
    public int compare(Organization organization, Organization t1) {
        return organization.getOfficialAddress().getTown().getName().compareTo(t1.getOfficialAddress().getTown().getName());
    }
}
