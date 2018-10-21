package com.urise.webapp;

public class MainString {
    public static void main(String[] args) {
        /*String[] strArray = new String[]{"1", "2", "3", "4", "5"};
        String result = "";
        for (String str : strArray) {
            result = result + str +", ";
        }
        System.out.println(result);*/

        String[] strArray = new String[]{"1", "2", "3", "4", "5"};
        StringBuilder sb = new StringBuilder();
        for (String str : strArray) {
            sb.append(str).append(", ");
        }
        System.out.println(sb.substring(0, sb.length() - 2));
        System.out.println(sb);
    }
}
