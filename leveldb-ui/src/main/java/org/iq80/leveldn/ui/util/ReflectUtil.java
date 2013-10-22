package org.iq80.leveldn.ui.util;

import java.lang.reflect.Field;

public class ReflectUtil {
	public static  Object getFieldValue(Object obj,String name) throws Exception{
		Field field = obj.getClass().getDeclaredField(name);
		field.setAccessible(true);
		return field.get(obj);
	}
}
