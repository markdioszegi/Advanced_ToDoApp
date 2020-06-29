package com.company;

import java.io.File;
import java.util.List;

public class Menu {
    private List<Todo> todos;
    private File file;
    private final String appName = "ToDo List";
    private boolean showMenu = true;

    public Menu(File file) {
        this.file = file;
        todos = IOManager.loadXML(file);
    }

    public void init() {
        Console.info("Welcome to the " + appName + " application!");

        while (showMenu) {
            showMainMenu();
            int choice = Console.getInt();

            if (choice == 5) {
                showMenu = false;
            } else if (choice == 1) {
                showTodos();
            } else if (choice == 2) {
                todos.add(createTodo());
            } else if (choice == 4) {
                removeTodo();
            } else {
                Console.warning("No such menu item!");
            }
        }

        IOManager.saveXML(file, todos);
        Console.info("App terminated successfully.");
    }

    private void showMainMenu() {
        Console.info("|--- Main menu ---|");
        Console.info("1. View my Todos");
        Console.info("2. Add a Todo");
        Console.info("3. Mark a Todo finished");
        Console.info("4. Remove a Todo");
        Console.info("5. Exit (pls no)");
    }

    private void showTodos() {
        if (!todos.isEmpty()) {
            for (Todo todo :
                    todos) {
                Console.info("-----" + todo.getName() + "-----");
                Console.info(todo.getDescription());
                if (todo.isFinished()) {
                    Console.info("It's done! :)");
                } else {
                    Console.info("In progress...");
                }
            }
        } else {
            Console.warning("Your todo list is empty!");
        }
    }

    private Todo createTodo() {
        String name = Console.getString("Name");
        String description = Console.getString("Description");
        return new Todo(name, description, false);
    }

    private void removeTodo() {
        String name = Console.getString("Enter the name of the Todo");
        if (todos.removeIf(todo -> todo.getName().equals(name))) {
            Console.info(name + " removed!");
        } else {
            Console.warning(name + " is not in your Todo list!");
        }
    }
}