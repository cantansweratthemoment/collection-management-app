package laba_8.Properties;


import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

public class Bundle {
    public static ResourceBundle resourceBundle = ResourceBundle.getBundle("laba_8/Properties/resources",new Locale("ru","RUS"));

    public static void setResourceBundle(String language) {
        if(language.equals("English")) {
            resourceBundle = ResourceBundle.getBundle("laba_8/Properties/resources", new Locale("en","UK"));
        }
        if(language.equals("Polish")){
            resourceBundle=ResourceBundle.getBundle("laba_8/Properties/resources",new Locale("pl","POL"));
        }
        if(language.equals("Norwegian")){
            resourceBundle = ResourceBundle.getBundle("laba_8/Properties/resources",new Locale("no","NOR"));
        }
        if(language.equals("Russian")){
            resourceBundle=ResourceBundle.getBundle("laba_8/Properties/resources",new Locale("ru","RUS"));
        }
    }

    public static ResourceBundle getResourceBundle() {
        return resourceBundle;
    }
}
