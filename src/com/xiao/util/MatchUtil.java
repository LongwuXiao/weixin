package com.xiao.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchUtil {

	public static boolean matchs(String str,String regEx){
		Pattern p=Pattern.compile(regEx);
		Matcher m=p.matcher(str);
		return m.find();
	}
}
