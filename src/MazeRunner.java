import java.util.*;

public class MazeRunner {
    public static Maze myMap = new Maze();
    public static Scanner input = new Scanner(System.in);
    public static int movesCount = 0;

    public static void main(String[] args) {
        //Welcome to the maze
        intro(myMap);

        //Go through maze until User won (left the maze) or exceeded the 100 moves
        while (myMap.didIWin() == false && movesCount < 100) {
            //User selects a direction
            String userChoice = userMove();
            //Then check if direction is free from walls and pits (and move)
            boolean ifCanMove = tryMove(userChoice);
            if (ifCanMove) {
                myMap.printMap();
                movesCount++;
            }
            else if (myMap.isThereAPit(userChoice)){
                navigatePit(userChoice);
            }
            else {
                System.out.println("Sorry, you've hit a wall");
            }
            //Message should notify if there is 50 or less moves left
            movesMessage(movesCount);
        }

        //Check if User win or lose
        if (myMap.didIWin()==true){
            System.out.println("Congratulations, you made it out alive!");
            System.out.println("And you did it in " + movesCount + " moves");
        }
        else {
            System.out.println("Sorry, but you didn't escape in time - you lose!");
        }
    }

    private static void intro(Maze newMaze){
        System.out.println("Welcome to Maze Runner! \nHere is your current position:");
        newMaze.printMap();
    }

    private static String userMove(){
        System.out.print("Where would you like to move? (R, L, U, D) ");
        String choice = input.next();
        if (choice.equalsIgnoreCase("R") || choice.equalsIgnoreCase("L") || choice.equalsIgnoreCase("U") || choice.equalsIgnoreCase("D")){
            return choice.toUpperCase();
        }
        else {
            System.out.println("Choose the valid move!");
            return userMove();
        }
    }

    private static boolean tryMove(String choice){
        if (choice.equals("R") && myMap.canIMoveRight()){
            myMap.moveRight();
            return true;
        }
        if (choice.equals("L") && myMap.canIMoveLeft()){
            myMap.moveLeft();
            return true;
        }
        if (choice.equals("U") && myMap.canIMoveUp()){
            myMap.moveUp();
            return true;
        }
        if (choice.equals("D") && myMap.canIMoveDown()){
            myMap.moveDown();
            return true;
        }
        return false;
    }

    private static void navigatePit(String choice) {
        System.out.print("Watch out! There's a pit ahead, jump it? ");
        String jumpAnswer = input.next();
        if (jumpAnswer.startsWith("y")) {
            myMap.jumpOverPit(choice);
            myMap.printMap();
            movesCount++;
        } else {
            System.out.println("Bad decision! Select 'yes' next time");
        }
    }

    private static void movesMessage(int moves){
        switch(moves){
            case 50:
                System.out.println("Warning: You have made 50 moves, you have 50 remaining before the maze exit closes");
                break;

            case 75:
                System.out.println("Alert! You have made 75 moves, you only have 25 moves left to escape.");
                break;

            case 90:
                System.out.println("DANGER! You have made 90 moves, you only have 10 moves left to escape!!");
                break;

            case 100:
                System.out.println("Oh no! You took too long to escape, and now the maze exit is closed FOREVER >:[");
                break;
        }
    }
}
