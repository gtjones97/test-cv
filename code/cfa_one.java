import java.text.NumberFormat;
import java.util.*;

public class cfa_one {

    public static String membershipLevel;
    public static int currentPoints = 0;
    public static int pointsUntilLevel = 0;
    public static int totalPointsToDate = 0;
    public static String nextLevel = "";

    public static void main(String[] args) {

        welcome();

        System.out.println("\n******************************************************************\n");
        System.out.println("Points currently available for rewards: " + currentPoints);
        System.out.println("\nWith " + currentPoints + " points, you can redeem one of the following items for free: ");

        ArrayList<String> rewardsOptions = listItems(currentPoints, itemizer());
        for(int i = 0; i < rewardsOptions.size(); i++){
            System.out.println(rewardsOptions.get(i));
        }

        System.out.println("\nYou have spent about " + NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(dollarsSpent(totalPointsToDate)) + " in the current cycle.");
        System.out.println("You will be upgraded to the next membership tier in " + NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(whenUpgraded()) + ".");

    }

    public static void welcome(){

        System.out.println("\nWelcome to the Chick-fil-A One calculator! Let's get started..");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        askMembership();
        askCurrentPoints();
        askPointsUntilLevel();
        askTotalPointsToDate();
    }

    public static void askMembership(){

        Scanner scan = new Scanner(System.in);

        System.out.print("What is your current membership level (normal, silver, or red)? ");

        if(scan.hasNextLine()){

            String answer = new String();

            answer = scan.nextLine();


            if(answer.compareTo("red") == 0){
                membershipLevel = "red";
                nextLevel = "null";

            }
            else if(answer.compareTo("silver") == 0){
                membershipLevel = "silver";
                nextLevel = "red";
            }
            else if(answer.compareTo("normal") == 0){
                membershipLevel = "normal";
                nextLevel = "silver";
            }
            else{
                System.out.println("You did not enter a valid answer. Please try again using 'red', 'silver', or 'normal'.");
                askMembership();
            }
        }

        else{
            System.out.println("You did not enter a valid answer.");
        }
    }

    public static void askTotalPointsToDate(){

        System.out.print("How many total points do you have in the current cycle? ");

        Scanner scan = new Scanner(System.in);

        if(scan.hasNextInt()){
            totalPointsToDate = scan.nextInt();
        }
    }
    public static void askCurrentPoints(){

        System.out.print("How many points do you have available for rewards? ");

        Scanner scan = new Scanner(System.in);

        if(scan.hasNextInt()){
            currentPoints = scan.nextInt();
        }
    }

    public static void askPointsUntilLevel(){

        if(nextLevel.compareTo("null") == 0) {
            return;
        }
        else{

            System.out.print("How many points until you reach " + nextLevel + " level membership? ");

            Scanner scan = new Scanner(System.in);

            if(scan.hasNextInt()){
                pointsUntilLevel = scan.nextInt();
            }
        }

    }

    public static Object[][][] itemizer(){

        Object[][][] items =
                {{
                        {0, "Cookie", 150},
                        {1, "Hash Browns", 150},
                        {2, "Sm IceDream", 150},
                        {3, "M Waffle Fry", 200},
                        {4, "M Soda/Iced Tea", 250},
                        {5, "Sm Milkshake", 550},
                        {6, "Lg Milkshake", 650},
                        {7, "Cool Wrap", 900},
                        {8, "Cobb Salad", 1500},
                        {9, "Chicken Biscuit", 350},
                        {10, "M Lemonade", 350},
                        {11, "M Fruit Cup", 450},
                        {12, "4ct Minis", 500},
                        {13, "Chicken Sandwich", 500},
                        {14, "Spicy Sandwich", 550},
                        {15, "Grilled Sandwich", 750},
                        {16, "Deluxe Sandwich", 650},
                        {17, "Market Salad", 1500},
                        {18, "8ct Nuggets", 500},
                        {19, "8ct Nuggets Grilled", 700},
                        {20, "Egg White Grill", 550},
                        {21, "Burrito", 550},
                        {22, "Sm Frosted Beverage", 550},
                        {23, "Lg Frosted Beverage", 650},
                        {24, "Grilled Club", 950},
                        {25, "4ct Strips", 800},
                        {26, "Spicy Southwest Salad", 1500},
                }};

        return items;

    }

    public static ArrayList listItems(int currentPoints, Object[][][] itemizer){

        int points = currentPoints;

        ArrayList<String> eligibleItems = new ArrayList<>();

        for(int i=0 ; i<itemizer.length ; i++){
            for(int j=0 ; j<itemizer[i].length ; j++){
                for(int k=0 ; k<itemizer[i][j].length ; k++){
                    if((int)itemizer[0][j][2] <= currentPoints){
                        eligibleItems.add(itemizer[0][j][1].toString() + " = " + itemizer[0][j][2] + " points");
                    }
                }
            }
        }

        //add items to HashSet and then back to the ArrayList to remove duplicates
        Set<String> set = new HashSet<>(eligibleItems);
        eligibleItems.clear();
        eligibleItems.addAll(set);

        return eligibleItems;
    }

    public static double whenUpgraded() {

        //multiplier is how many points obtained per dollar spent
        int multiplier = 0;

        if(membershipLevel == "normal"){
            multiplier = 10;
        }
        else if(membershipLevel == "silver"){
            multiplier = 11;
        }

        if(membershipLevel == "red"){
            return 0;
        }

        return pointsUntilLevel / multiplier;
    }

    public static double dollarsSpent(int totalPointsToDate){

        //multiplier is how many points obtained per dollar spent
        int multiplier = 0;

        if(membershipLevel == "normal"){
            multiplier = 10;
        }
        else if(membershipLevel == "silver"){
            multiplier = 11;
        }
        else if(membershipLevel == "red"){
            multiplier = 12;
        }

        return totalPointsToDate / multiplier;
    }
}

