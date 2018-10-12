package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume r = new Resume("1", "Kotov Vasilii");
        Field field = r.getClass().getDeclaredFields()[0];
        System.out.println(field.getName());
        field.setAccessible(true);
        System.out.println(field.get(r));
        field.set(r, "new_uuid");
        System.out.println(r);

        // invoke r.toString via reflection
        Method method = r.getClass().getDeclaredMethod("toString");
        System.out.println(method.getName());
        System.out.println(method.invoke(r));
    }
}
