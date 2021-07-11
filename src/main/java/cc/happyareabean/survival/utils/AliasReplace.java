package cc.happyareabean.survival.utils;

import cc.happyareabean.survival.Survival;

public class AliasReplace {

	public String getAlias(final String s) {
		for (final String s2 : Survival.getInstance().getConfig().getStringList("alias")) {
			if (!s2.split(":")[0].equalsIgnoreCase(s)) {
				continue;
			}
			return s2.split(":")[1];
		}
		return s;
	}

	public String getWorld(final String s) {
		for (final String s2 : Survival.getInstance().getConfig().getStringList("alias")) {
			if (!s.equalsIgnoreCase(s2.split(":")[1])) {
				continue;
			}
			return s2.split(":")[0];
		}
		return s;
	}
}
