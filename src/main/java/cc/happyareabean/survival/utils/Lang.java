package cc.happyareabean.survival.utils;

import cc.happyareabean.survival.Survival;

public class Lang {
    public static String prefix;
    public static String password;

    static {
        prefix = Color.translate("&8[&a" + Survival.getInstance().getDescription().getName() + "&8] &e");
        password = "gNUxaYZx3oWaWggw";
    }
}
