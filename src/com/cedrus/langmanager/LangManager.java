package com.cedrus.langmanager;

/**
 * Created by k2 on 3/12/2017.
 */
public class LangManager {

    private static final String SEARCH_TEXT_UA = "Пошук";
    private static final String SEARCH_TEXT_EN = "Search";

    public enum Lang {UA, EN};

    private static LangManager instance = null;

    public static LangManager getInstance() {
        if (instance == null) {
            instance = new LangManager();
        }
        return instance;
    }

    private LangManager() {
    }

    public String getSearchText(Lang language) {

        String langFilePath = "./lang/" + language.toString();

        if (language.equals(Lang.EN)) {
            return SEARCH_TEXT_EN;
        } else if (language.equals(Lang.UA)) {
            return SEARCH_TEXT_UA;
        }
        return "";
    }

    public String getText(Lang language, String text) {

        if (language.equals(Lang.EN)) {
            return SEARCH_TEXT_EN;
        } else if (language.equals(Lang.UA)) {
            return SEARCH_TEXT_UA;
        }
        return "";

    }
}
