import java.io.*;
import java.util.Scanner;
import jpb.*;

public class Personnel implements Serializable {
    public static void main(String[] args) {
        // Create an array to store the employee records
        Employee[] employees = new Employee[1];

        // Declare a variable that tracks the number of employees
        int numEmployees = 0;

        // Read and execute commands
        while (true) {

            // Display list of commands
            System.out.println(
                            "----------------------------------\n" +
                            "|Commands: n - New employee      |\n" +
                            "|          c - Compute paychecks |\n" +
                            "|          r - Raise wages       |\n" +
                            "|          p - Print records     |\n" +
                            "|          d - Download data     |\n" +
                            "|          u - Upload data       |\n" +
                            "|          q - Quit              |\n" +
                            "----------------------------------");

            // Prompt the user to enter a command
            SimpleIO.prompt("Enter command: ");
            String command = SimpleIO.readLine().trim();

            // Use a cascaded if statement to determine which
            // command was entered
            if (command.equalsIgnoreCase("p")) {
                clearScreen();
                for (int i = 0; i < numEmployees; i++)
                    System.out.println(employees[i]);

                System.out.println("<End of Database>");
                returnToMenu();
            } else if (command.equalsIgnoreCase("u")) {
                clearScreen();
                String fileName = "employees.dat";
                try {
                    FileInputStream fileIn =
                            new FileInputStream(fileName);
                    ObjectInputStream in =
                            new ObjectInputStream(fileIn);
                    Employee[] temp = (Employee[]) in .readObject();
                    System.out.println("Now uploading these records...");
                    for (int i = 0; i < temp.length; i++)
                        if (temp[i] != null)
                            System.out.println(temp[i]);
                    for (int i = 0; i < temp.length; i++) {
                        if (numEmployees == employees.length) {
                            Employee[] tempArray =
                                    new Employee[employees.length * 2];
                            for (int j = 0; j < employees.length; j++)
                                tempArray[j] = employees[j];
                            employees = tempArray;
                        }
                        employees[numEmployees] = temp[i];
                        numEmployees++;
                    }

                    // Prompt user for name of new employee
                    in .close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                } catch (ClassNotFoundException e) {
                    System.out.println(e.getMessage());
                }
                System.out.println("\nUpload complete.");
                returnToMenu();
            } else if (command.equalsIgnoreCase("d")) {
                clearScreen();
                String fileName = "employees.dat";
                try {
                    FileOutputStream fileOut =
                            new FileOutputStream(fileName);
                    ObjectOutputStream out =
                            new ObjectOutputStream(fileOut);
                    out.writeObject(employees);
                    System.out.println("Now downloading these records...");
                    for (int i = 0; i < numEmployees; i++)
                        System.out.println(employees[i]);
                    out.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }

                System.out.println("\nDownload complete.");
                returnToMenu();
            } else if (command.equalsIgnoreCase("n")) {
                clearScreen();

                // *** New employee ***
                // If the employees array is full, double its size
                if (numEmployees == employees.length) {
                    Employee[] tempArray =
                            new Employee[employees.length * 2];
                    for (int i = 0; i < employees.length; i++)
                        tempArray[i] = employees[i];
                    employees = tempArray;
                }

                // Prompt user for name of new employee
                SimpleIO.prompt("Enter name of new employee: ");
                String name = SimpleIO.readLine().trim();

                // Repeat until valid input is entered
                while (true) {

                    // Ask user whether employee will be hourly or
                    // salaried
                    SimpleIO.prompt("Hourly (h) or salaried (s): ");
                    String status = SimpleIO.readLine().trim();

                    // Create a new HourlyEmployee or SalariedEmployee
                    // object and store in employees array
                    if (status.equalsIgnoreCase("h")) {
                        double wage = readDouble("Enter hourly wage: ");
                        employees[numEmployees] =
                                new HourlyEmployee(name, wage);
                        break;
                    } else if (status.equalsIgnoreCase("s")) {
                        double salary = readDouble("Enter annual salary: ");
                        employees[numEmployees] =
                                new SalariedEmployee(name, salary);
                        break;
                    } else
                        System.out.println("Input was not h or s; please " +
                                "try again.");
                }
                numEmployees++;
                System.out.println("\nEmployee added!");
                returnToMenu();

            } else if (command.equalsIgnoreCase("c")) {

                clearScreen();

                // *** Compute paychecks ***
                for (int i = 0; i < numEmployees; i++) {
                    double hoursWorked =
                            readDouble("Enter number of hours worked by " +
                                    employees[i].getName() + ": ");
                    double pay = employees[i].computePay(hoursWorked);
                    System.out.println("Pay: $" + toDollars(pay));
                }

                System.out.println("\nAll paychecks computed.");
                returnToMenu();

            } else if (command.equalsIgnoreCase("r")) {

                /*       // *** Raise wages ***
        double percentage = readDouble("Enter percentage increase: ");
        System.out.println("\nNew Wages\n---------");
        for (int i = 0; i < numEmployees; i++) {
          employees[i].increasePay(percentage);
          System.out.println(employees[i]);
        }
*/
                clearScreen();

                System.out.println("\nEnter percentage increase: ");
                Scanner stdin = new Scanner(System.in);
                double percentage = stdin.nextDouble();

                for (int i = 0; i < numEmployees; i++) {
                    double currentHourlyWage = employees[i].getHourlyWage();

                    employees[i].setHourlyWage(((currentHourlyWage) * (percentage / 100.0)) + (currentHourlyWage));
                }

                System.out.println("New Wages\n-------");
                for (int i = 0; i < numEmployees; i++) {
                    System.out.println(employees[i].toString());
                }

                System.out.println();
                returnToMenu();

            } else if (command.equalsIgnoreCase("q")) {

                // *** Quit ***
                return;

            } else {

                // *** Illegal command ***
                System.out.println("Command was not recognized; " +
                        "please try again.");
            }

            System.out.println();
        }
    }

    ///////////////////////////////////////////////////////////
    // NAME:       readDouble
    // BEHAVIOR:   Prompts the user to enter a number, reads
    //             the user's input, and converts it to double
    //             form.
    // PARAMETERS: prompt - the prompt to be displayed
    // RETURNS:    User's input after conversion to double
    ///////////////////////////////////////////////////////////
    private static double readDouble(String prompt) {
        SimpleIO.prompt(prompt);
        String userInput = SimpleIO.readLine();
        return Convert.toDouble(userInput);
    }

    public static String toDollars(double amount) {
        long roundedAmount = Math.round(amount * 100);
        long dollars = roundedAmount / 100;
        long cents = roundedAmount % 100;

        if (cents <= 9)
            return dollars + ".0" + cents;
        else
            return dollars + "." + cents;
    }

    public static void returnToMenu() {
        System.out.print("Please hit return to go back to main menu...");

        Scanner stdin = new Scanner(System.in);
        if (stdin.hasNextLine()) {}

    }

    public static void clearScreen() {
        System.out.println("\u001b[H\u001b[2J");
    }
}