package com.company;

import java.io.File;

public class Main {
    final static File DEFAULT_FILE = new File("todo.xml");

    public static void main(String[] args) {
        Menu menu;
        if (args.length > 0) {
            menu = new Menu(new File(args[0]));
        } else {
            menu = new Menu(DEFAULT_FILE);
        }
        menu.init();
    }
}