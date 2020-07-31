package laba_8.Utils;

import javafx.scene.control.Alert;
import laba_8.Classes.OrganizationType;

import java.util.Arrays;

public class Readers {
    public static boolean doesThisOrganizationTypeExist(String organizationType) {
        return Arrays.stream(OrganizationType.values()).anyMatch(organizationType1 -> organizationType1.name().equals(organizationType));
    }

    public static OrganizationType readType(String ttyype) {
        String type = ttyype.trim();
        if (!doesThisOrganizationTypeExist(type) || type.equals("")) {
            while (!doesThisOrganizationTypeExist(type)) {
                return null;
            }
        }
        return Enum.valueOf(OrganizationType.class, type);
    }

    public static String readName(String s) {
        String name = s.trim();
        if (name.equals("")) {
            return null;
        }
        return name;
    }

    public static Double readX(String x) {
        String cX_s = x.trim();
        Double cX;
        while (true) {
            try {
                cX = Double.parseDouble(cX_s);
                if (cX <= 643) {
                    return cX;
                } else {
                    return null;
                }
            } catch (NumberFormatException e) {
                return null;
            }
        }
    }

    public static Double readY(String y) {
        String cY_s = y.trim();
        Double cY;
        while (true) {
            try {
                cY = Double.parseDouble(cY_s);
                return cY;
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                return null;
            }
        }
    }

    public static Double readAnnualTurnover(String turnover) {
        String annualTurnover_s = turnover.trim();
        Double annualTurnover;
        while (true) {
            try {
                annualTurnover = Double.parseDouble(annualTurnover_s);
                if (annualTurnover > 0) {
                    return annualTurnover;
                } else {
                    return null;
                }
            } catch (NumberFormatException e) {
                return null;
            }
        }
    }

    public static String readStreet(String street) {
        String adress = street.trim();
        if (adress.length() > 136) {
            while (adress.length() > 136) {
                return null;
            }
        }
        return adress;
    }

    public static String readZipCode(String zip) {
        String zipCode = zip.trim();
        if (zipCode.length() > 26) {
            while (zipCode.length() > 26) {
                return null;
            }
        }
        if (zipCode.equals("")) {
            while (zipCode.equals("")) {
                return null;
            }
        }
        return zipCode;
    }

    public static Float readLocation(String ll) {
        String l_s;
        Float l;
        while (true) {
            l_s = ll.trim();
            try {
                l = Float.parseFloat(l_s);
                return l;
            } catch (NumberFormatException e) {
                return null;
            }
        }
    }

    public static String readTownName(String town) {
        String name = town.trim();
        if (name.equals("")) {
            while (name.equals("")) {
                return null;
            }
        }
        return name;
    }
}