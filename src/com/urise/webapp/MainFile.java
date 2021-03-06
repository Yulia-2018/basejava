package com.urise.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) {
        //File file = new File("C:\\Source\\basejava\\.gitignore");

        final String filePath = ".\\.gitignore";
        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        /*FileInputStream fis = null;
        try {
            fis = new FileInputStream(filePath);
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }*/

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("");

        //File dir = new File(".\\src\\com\\urise\\webapp");
        File dir = new File(".\\src");
        printDirectoryDeeply(new StringBuilder(), dir);
    }

    private static void printDirectoryDeeply(StringBuilder indent, File dir) {
        final File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                System.out.println(indent + file.getName());
                if (file.isDirectory()) {
                    indent.append("  ");
                    printDirectoryDeeply(indent, file);
                    indent.delete(indent.length() - 2, indent.length());
                }
            }
        }
    }
}
