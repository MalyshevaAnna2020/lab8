package client.gui.simplify;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


public class LocaleRegular {
    java.util.Locale locale;
    ResourceBundle bundle;

    public LocaleRegular(){
        locale = new java.util.Locale("en", "GB");
        bundle = ResourceBundle.getBundle("Locale", locale);
    }

    private static final Map<String, java.util.Locale> setOfLocales = new HashMap<String, java.util.Locale>(){{
        put("English (United Kingdom)", new java.util.Locale("en", "EN"));
        put("Русский", new java.util.Locale("ru", "RU"));
        put("Português", java.util.Locale.forLanguageTag("pt-PT"));
        put("Latviešu", java.util.Locale.forLanguageTag("lv-LV"));
    }};

    public void setLocale(String choice){
        for (String keyScript : setOfLocales.keySet()) {
            if (choice.equals(keyScript)) {
                locale = setOfLocales.get(keyScript);
            }
        }
        bundle = ResourceBundle.getBundle("Locale", locale);
    }

    public ResourceBundle getBundle(){
        return bundle;
    }

}
