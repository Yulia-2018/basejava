package com.urise.webapp.util;

public class LazySingleton {
/*    private static volatile LazySingleton INSTANCE;
    private static int i;

    private LazySingleton() {
    }

    public static LazySingleton getInstance() {
        if (INSTANCE == null) {
            synchronized (LazySingleton.class) {
                if (INSTANCE == null) {
                    i = 10;
                    INSTANCE = new LazySingleton();
                }
            }
        }
        return INSTANCE;
    }*/

    private LazySingleton() {
    }

    private static class LazySingletonHolder {
        private static final LazySingleton INSTANCE = new LazySingleton();
    }

    public static LazySingleton getInstance() {
        return LazySingletonHolder.INSTANCE;
    }
}
