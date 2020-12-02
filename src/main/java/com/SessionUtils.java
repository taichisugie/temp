package com;

import javax.servlet.http.HttpSession;

//これでうまくいく？

public class SessionUtils {
	public static void updateSessionScope(HttpSession s, String updateKey, Object updateValue) {
		s.removeAttribute(updateKey);
		s.setAttribute(updateKey, updateValue);
	}
}
