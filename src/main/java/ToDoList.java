import entity.Tasks;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * To-Do List class for Assignment 2, CEN 4025C-15911, Software Development II, Valencia College.
 *
 * @author	Stephen Sturges Jr
 * @version	10/08/2022
 */
public class ToDoList {
    // Scanner for this Object.
    private static Scanner input = new Scanner(System.in);

    // Create an ArrayList to hold To-Do List items.
    private ArrayList<String> toDoList = new ArrayList<String>();

    // Constants.
    private final String noTasksMessage = "\nThere are no tasks on the list.\n";

    /**
     * Requests the user to enter a task and adds that task to the To-Do list.
     *
     * @deprecated  Replaced by addTaskDB.
     */
    public void addTask() {
        String task = userInputString("Please enter your task: ");
        toDoList.add(task);
    }

    /**
     * Requests user to enter a task and then adds the task to the tasks table in the database.
     */
    public void addTaskDB(){
        String task = userInputString("Please enter your task: ");

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            Tasks newTask = new Tasks();
            newTask.setTask(task);
            entityManager.persist(newTask);
            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
    } // End of addTasksDB method.

    /**
     * Adds a task to the To-Do list. (Overloaded method.)
     *
     * @param task	A String entered by the user describing the item they wish to put on the To-Do list.
     */
    public void addTask(String task) {
        toDoList.add(task);
    }

    /**
     * Loops through the ArrayList containing To-Do list tasks and outputs tasks to the console.
     *
     * @deprecated  Replaced by displayTasksDB.
     */
    public void displayTasks() {
        if (toDoList.size() == 0) {
            System.out.println(noTasksMessage);
        } else {
            System.out.println("\nTo-Do List:");
            for (int i = 0; i < toDoList.size(); i++) {
                System.out.println("[" + (i + 1) + "] " + toDoList.get(i));
            }
        }
        System.out.println(); // Blank line for formatting.
    }

    // TODO Finish JAVADOC comment.
    /**
     *
     */
    public void displayTasksDB() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            TypedQuery<Tasks> allTasks = entityManager.createNamedQuery("displayTasks", Tasks.class);
            int i = 0;
            for (Tasks task : allTasks.getResultList()) {
                i++;
                System.out.println(i + ". " + task.getTask());
            }
            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
    } // End of displayTasksDB method.

    /**
     * Removes a task from the To-Do list.
     *
     * @deprecated  Replaced by removeTaskDB.
     */
    public void removeTask() {
        if (toDoList.size() == 0) {
            System.out.println(noTasksMessage);
        } else {
            displayTasks();
            System.out.println("Or enter [" + (toDoListSize() + 1) + "] to return to the main menu.");
            int index = userInputInt("\nPlease enter the number of the task you wish to remove: ", 1, toDoListSize() + 1);
            if (index != toDoListSize() + 1) {
                toDoList.remove(index - 1);
                System.out.println("\nTask [" + (index - 1) + "] removed.\n");
            }
        }
    }

    /**
     * Removes a task from the To-Do list. (Overloaded method.)
     *
     * @param index	An integer value pertaining to the location of the item the user wants to remove from the To-Do list.
     * @deprecated  No longer needed with the database.
     */
    public void removeTask(int index) {
        if (toDoList.size() == 0) {
            System.out.println(noTasksMessage);
        } else {
            toDoList.remove(index - 1);
        }
    }

    // TODO Finish JAVADOC comment.
    /**
     *
     */
    public void removeTaskDB() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            TypedQuery<Tasks> allTasks = entityManager.createNamedQuery("displayTasks", Tasks.class);
            int i = 0;
            for (Tasks task : allTasks.getResultList()) {
                i++;
                System.out.println("Task ID: " + task.getTaskId() + ": " + task.getTask());
            }
            int taskIDToRemove = userInputInt("\nPlease enter the TaskID number of the task you wish to remove: ", allTasks.getFirstResult(), allTasks.getMaxResults());
            Query removeTask = entityManager.createNativeQuery("DELETE FROM tasks WHERE task_id =:taskIDToRemove");
            removeTask.setParameter("taskIDToRemove",taskIDToRemove);
            removeTask.executeUpdate(); // This is needed to actually remove the task entry.
            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
    } // End of removeTaskDB method.

    /**
     * Returns the size of the ArrayList containing To-Do list items.
     *
     * @return	    An integer pertaining to how many items are in the To-Do list.
     * @deprecated  Not needed while using the database.
     */
    public int toDoListSize() {
        return toDoList.size();
    }

    /**
     * Used to get an integer value from the user and validate it is within bounds. Handles incorrect inputs such as strings, doubles, etc.
     *
     * @param inputPrompt	A string prompting the user for input.
     * @param lowerBound	An integer representing the lowest acceptable value the user can input.
     * @param upperBound	An integer representing the highest acceptable value the user can input.
     * @return				Validated integer input from the user.
     */
    public static int userInputInt(String inputPrompt, int lowerBound, int upperBound) {
        int userInput = 0;	// Return variable.
        boolean goodInput = false;	// Loop control variable.
        // User input validation loop.
        while (!goodInput) {
            try {
                System.out.print(inputPrompt);
                userInput = Integer.parseInt(input.nextLine());
                if (userInput >= lowerBound && userInput <= upperBound) {
                    goodInput = true;
                } else {
                    System.out.println("\nINVALID ENTRY: Please enter a value between " + lowerBound + " and " + upperBound + ".");
                    goodInput = false;
                } // End of if-else statement.
            } catch (InputMismatchException ime) {
                System.out.println("\nINVALID ENTRY: Please enter a whole number only.");
                goodInput = false;
            } // End of try-catch statement.
        } // End of while loop.
        return userInput;
    } // End of userInputInt method.

    /**
     * Prompts the user for String input and returns the input String.
     *
     * @param inputPrompt	A string prompting the user for input.
     * @return				A string containing the user's input.
     */
    public static String userInputString(String inputPrompt) {
        System.out.print(inputPrompt);
        String userInput = input.nextLine();
        return userInput;
    }

} // End of ToDoList class.
