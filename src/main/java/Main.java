import entity.Tasks;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * For Assignment 5, CEN 4025C-15911, Software Development II, Valencia College.
 * A To-Do list program that allows the user to add, display, and remove tasks.
 * The list is stored in a MySQL database through Hibernate.
 *
 * @author	Stephen Sturges Jr
 * @version	10/08/2022
 */
public class Main {

    // Scanner for this Class.
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        ToDoList list = new ToDoList();
        int userInput;

        // The following lines are added for quick testing of the program.
        list.addTask("Example: Task one.");
        list.addTask("Example: Task two.");
        list.addTask("Example: Task three.");
        list.addTask("Example: Task four.");

        do {
            displayMenu();
            userInput = userInputInt("\nPlease enter your selection: ", 1, 4);
            switch (userInput) {
                case 1:
//                    list.displayTasks();
                    list.displayTasksDB();
                    break;
                case 2:
//                    list.addTask();
                    list.addTaskDB();
                    break;
                case 3:
//                    list.removeTask();
                    list.removeTaskDB();
                    break;
                case 4:
                    marquee("Have a great day!", '-', 3);
                    break;
            } // End of switch statement.
        } while (userInput != 4);
    } // End of main method.

    /**
     * Outputs a menu to the console.
     */
    public static void displayMenu() {
        marquee("Main To-Do List Menu", '=', 3);
        System.out.println("[1] Display To-Do List.");
        System.out.println("[2] Add Task.");
        System.out.println("[3] Remove Task.");
        System.out.println("[4] Exit.");
    } // End of displayMenu method.

    /**
     * Displays a message surrounded by a border consisting of the given character.
     *
     * @param message			A String from the user to be displayed inside the border.
     * @param symbol			A char used to create the border.
     * @param horizontalPadding	An integer representing the space between the
     */
    public static void marquee(String message, char symbol, int horizontalPadding) {
        int horizontalPaddingBorder = (horizontalPadding * 2) + 2;
        StringBuilder builder = new StringBuilder();

        // Build string for top and bottom borders.
        for (int i = 0; i < message.length() + (horizontalPaddingBorder); i++) {
            builder.append(symbol);
        }
        String border = builder.toString();
        // Clear border string.
        builder.delete(0, message.length() + horizontalPaddingBorder);

        // Build string for message line.
        for (int i = 0; i < horizontalPadding; i++) {
            builder.append(symbol);
        }
        builder.append(" " + message + " ");
        for (int i = 0; i < horizontalPadding; i++) {
            builder.append(symbol);
        }
        String messageLine = builder.toString();

        // Output marquee.
        System.out.println(border);
        System.out.println(messageLine);
        System.out.println(border);
    } // End of marquee method.

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
                userInput = input.nextInt();
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

} // End of Main class.
