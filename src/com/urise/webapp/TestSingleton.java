package com.urise.webapp;

import com.urise.webapp.model.SectionType;

public class TestSingleton {
    private static TestSingleton instance; //= new TestSingleton();

    public static TestSingleton getInstance() {
        if (instance == null) {
            instance = new TestSingleton();
        }
        return instance;
    }

    private TestSingleton() {
    }

    public static void main(String[] args) {
        //getInstance().toString();
        TestSingleton.getInstance().toString();
        System.out.println(TestSingleton.getInstance().toString());
        final Singleton instance = Singleton.valueOf("INSTANCE");
        System.out.println(instance.toString());
        System.out.println(instance.name());
        System.out.println(instance.ordinal());

        for (SectionType type : SectionType.values()) {
            System.out.println(type.getTitle());
        }
    }

    public enum Singleton {
        INSTANCE
    }
}
