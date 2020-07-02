package com.company;

import java.io.File;
import java.util.ArrayList;
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

            if (choice == 6) {
                showMenu = false;
            } else if (choice == 1) {
                Console.listTodos(todos);
            } else if (choice == 2) {
                todos.add(createTodo());
            } else if (choice == 3) {
                markTodo();
            } else if (choice == 4) {
                updateTodo();
            } else if (choice == 5) {
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
        Console.info("3. Mark a Todo done");
        Console.info("4. Update a Todo");
        Console.info("5. Remove a Todo");
        Console.info("6. Exit (pls no)");
    }

    private Todo createTodo() {
        String name = Console.getString("Name");
        String description = Console.getString("Description");
        return new Todo(name, description, false);
    }

    private void removeTodo() {
        String name = Console.getString("Enter the name of the Todo");

        Todo todo = checkIdentical(name);

        if (todo != null) {
            todos.remove(todo);
            Console.info(name + " removed!");
        }
    }

    private void updateTodo() {
        Console.info("Which Todo would you like to update?");
        String name = Console.getString("Todo name");

        Todo todo = checkIdentical(name);

        if (todo != null) {
            String newName = Console.getString("New Todo name (enter to leave it unchanged)");
            String newDescription = Console.getString("New Todo description (enter to leave it unchanged)");
            if (!newName.equals("")) {
                todo.setName(newName);
            }
            if (!newDescription.equals("")) {
                todo.setDescription(newDescription);
            }
        }
    }

    private Todo checkIdentical(String name) {
        List<Todo> foundTodos = new ArrayList<Todo>();

        for (Todo todo :
                todos) {
            if (todo.getName().equals(name)) {
                foundTodos.add(todo);
            }
        }

        if (foundTodos.size() > 1) {
            Console.info("Found multiple Todos with " + name + "!");
            Console.info("Which one?");
            for (int i = 0; i < foundTodos.size(); i++) {
                Console.info("[" + i + "] " + foundTodos.get(i).getName());
            }
            int index = Console.getInt();

            return foundTodos.get(index);
        } else if (!foundTodos.isEmpty()) {
            return foundTodos.get(0);
        } else {
            Console.warning(name + " is not in your list!");
            return null;
        }
    }

    private void markTodo() {
        String name = Console.getString("Which Todo would you like to mark as done?");

        Todo todo = checkIdentical(name);

        if (todo != null) {
            if (todo.isFinished()) {
                Console.warning("It's already marked as done!");
            } else {
                todo.setFinished(true);
                Console.info(todo.getName() + " marked done!");
            }
        }
    }
}