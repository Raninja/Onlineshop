package com.example.onlineshop;

public class passProductId {

    private static int id;
    public static String filepath;
    public static String menuselect;

    public static String getMenuselect() {
        return menuselect;
    }

    public static void setMenuselect(String menuselect) {
        passProductId.menuselect = menuselect;
    }

    public static String getFilepath() {
        return filepath;
    }

    public static void setFilepath(String filepath) {
        passProductId.filepath = filepath;
    }

    public passProductId(int id, String filepath, String menuselect) {
        passProductId.id = id;
        passProductId.filepath = filepath;
        passProductId.menuselect = menuselect;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        passProductId.id = id;
    }

}
