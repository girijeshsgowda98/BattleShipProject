package battleshipmain;

import java.util.Locale;
import java.util.Scanner;

public class Main {
    static String[][] field = new String[10][10];
    static String[][] fogfield1 = new String[10][10];
    static String[][] field2 = new String[10][10];
    static String[][] fogfield2 = new String[10][10];
    static Scanner scanner = new Scanner(System.in);
    static int x1, y1;
    static boolean condition = false;
    static boolean condition2 = false;
    static boolean battleTwoEntryCondition = false;
    static boolean battleCondition = true;
    static int count = 0;
    static int count2 = 0;

    enum Ship {
        AIRCRAFT_CARRIER("Aircraft Carrier", 5),
        BATTLESHIP("Battleship", 4),
        SUBMARINE("Submarine", 3),
        CRUISER("Cruiser", 3),
        DESTROYER("Destroyer", 2);

        String name;
        int length;

        Ship(String name, int length) {
            this.name = name;
            this.length = length;
        }

        public String getName() {
            return name;
        }

        public int getLength() {
            return length;
        }
    }


    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                field[i][j] = "~";
            }
        }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                fogfield1[i][j] = "~";
            }
        }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                field2[i][j] = "~";
            }
        }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                fogfield2[i][j] = "~";
            }
        }
        battleFightCreateField();
    }



    private static void battleFightCreateField() {
        System.out.println("Player 1, place your ships on the game field\n");
        printField();
        entryShip();
        //System.out.println("Press Enter and pass the move to another player");
        promptEnterKey();
        System.out.println("Player 2, place your ships on the game field\n");
        battleTwoEntryCondition = true;
        printField();
        entryShip();
        startGame();
    }
    public static void promptEnterKey() {
        System.out.println("Press Enter and pass the move to another player");
        try {
            System.in.read();
        } catch (Exception e) {
            System.out.println("...");
        }
    }

    private static void startGame() {
        if (!condition) {
            promptEnterKey();
            if (battleCondition) {
                print();
                battleTwoEntryCondition = false;
                printField();
                System.out.println("Player 1, it's your turn:\n");
                battleTwoEntryCondition = false;
                entryBattle();
            } else {
                print();
                battleTwoEntryCondition = true;
                printField();
                System.out.println("Player 2, it's your turn:\n");
                battleTwoEntryCondition = true;
                entryBattle();
            }
        }
        else{
            System.out.println("You sank the last ship. You won. Congratulations!");
        }
    }

    private static void entryBattle() {
        if (!condition) {
            try {
                String[] arrayCoordinates = scanner.nextLine().trim().toUpperCase(Locale.ROOT).split("\\s+");
                if (arrayCoordinates.length < 2) {
                    String oneCoordinate = arrayCoordinates[0];
                    x1 = oneCoordinate.charAt(0) - 65;
                    y1 = Integer.parseInt(oneCoordinate.substring(1)) - 1;
                    if ((x1 <= 9 && x1 >= 0) && (y1 <= 9 && y1 >= 0)) {
                        entryBattleFight(x1, y1);
                    } else {
                        System.out.println("Error! You entered the wrong coordinates! Try again:\n");
                        entryBattle();
                    }
                } else {
                    // System.out.println("err of the length\n");
                    entryBattle();
                }
            } catch (Exception e) {
                //System.out.println("error re enter the battle field\n");
                entryBattle();
            }
        } else {
            System.out.println("You sank the last ship. You won. Congratulations!");
            condition = true;
            startGame();
        }

    }

    private static void entryBattleFight(int x1, int y1) {
        if (battleTwoEntryCondition) {
            if (field[x1][y1] == "O") {
                field[x1][y1] = "X";
                battleShipDamage(x1, y1);
                if (!condition2) {
                    if (!condition) {
                        System.out.println("You hit a ship!");
                        battleCondition = true;
                        //condition2 = false;
                    }
                    count = 0;

                } else {
                    if (!condition) {
                        System.out.println("You sank a ship! Specify a new target:");
                        battleCondition = true;
                    }
                    count = 0;
                    condition2 = false;
                    //entryBattle();
                }

            } else {
                if (field[x1][y1] != "O" && field[x1][y1] != "X") {
                    field[x1][y1] = "M";
                    System.out.println("You missed!");
                    count = 0;
                    battleCondition = true;
                    //entryBattle();
                } else {
                    field[x1][y1] = "X";
                    System.out.println("already entry. Try again:\n");
                    count = 0;
                    battleCondition = true;
                    //entryBattle();
                }
            }
        }

        //entry fog field second entry
        else {
            if (field2[x1][y1] == "O") {
                field2[x1][y1] = "X";
                battleShipDamage(x1, y1);
                if (!condition2) {
                    if (!condition) {
                        System.out.println("You hit a ship! ");
                        battleCondition = false;
                    }
                    count2 = 0;
                } else {
                    if (!condition) {
                        System.out.println("You sank a ship! Specify a new target:");
                        battleCondition = false;
                    }
                    count2 = 0;
                    condition2 = false;
                    //entryBattle();
                }
            } else {
                if (field2[x1][y1] != "O" && field2[x1][y1] != "X") {
                    field2[x1][y1] = "M";
                    System.out.println("You missed!");
                    count2 = 0;
                    battleCondition =  false;
                } else {
                    field2[x1][y1] = "X";
                    // printField2();
                    System.out.println("already entry. Try again:\n");
                    count2 = 0;
                    battleCondition = false;
                    //entryBattle();
                }
            }

        }
        startGame();
    }

    private static void battleShipDamage(int x1, int y1) {
        if (battleTwoEntryCondition) {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (field[i][j] == "X") {
                        count++;
                    }
                }
            }
            if (count == 17) {
                condition = true;
            }
            if (x1 >= 0 && y1 >= 0 && x1 < 9 && y1 < 9) {
                if (field[x1 + 1][y1] != "O" && field[x1][y1 + 1] != "O") {
                    condition2 = true;
                }
            } else if (x1 <= 9 && y1 <= 9 && x1 > 0 && y1 > 0) {
                if (x1 == 9 && y1 > 9) {
                    if (field[x1 - 1][y1] != "O" && field[x1][y1 - 1] != "O" && field[x1][y1 + 1] != "O") {
                        condition2 = true;
                    }
                } else if (y1 == 9 && x1 < 9) {
                    if (field[x1 - 1][y1] != "O" && field[x1 - 1][y1 - 1] != "O" && field[x1 + 1][y1] != "O") {
                        condition2 = true;
                    }
                } else if (x1 == 9 && y1 == 9) {
                    if (field[x1 - 1][y1] != "O" && field[x1][y1 - 1] != "O") {
                        condition2 = true;
                    }
                }

            } else if (x1 > 0 && x1 < 9 && y1 < 9 && y1 > 0) {
                if (field[x1 + 1][y1] != "O" && field[x1][y1 + 1] != "O" && field[x1 - 1][y1] != "O" && field[x1][y1 - 1] != "O") {
                    condition2 = true;
                }
            }
        }
        // second fog damage condition
        else {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (field2[i][j] == "X") {
                        count2++;
                    }
                }
            }
            if (count2 == 17) {
                condition = true;
            }
            if (x1 >= 0 && y1 >= 0 && x1 < 9 && y1 < 9) {
                if (field2[x1 + 1][y1] != "O" && field2[x1][y1 + 1] != "O") {
                    condition2 = true;
                }
            } else if (x1 <= 9 && y1 <= 9 && x1 > 0 && y1 > 0) {
                if (x1 == 9 && y1 > 9) {
                    if (field2[x1 - 1][y1] != "O" && field2[x1][y1 - 1] != "O" && field2[x1][y1 + 1] != "O") {
                        condition2 = true;
                    }
                } else if (y1 == 9 && x1 < 9) {
                    if (field2[x1 - 1][y1] != "O" && field2[x1 - 1][y1 - 1] != "O" && field2[x1 + 1][y1] != "O") {
                        condition2 = true;
                    }
                } else if (x1 == 9 && y1 == 9) {
                    if (field2[x1 - 1][y1] != "O" && field2[x1][y1 - 1] != "O") {
                        condition2 = true;
                    }
                }

            } else if (x1 > 0 && x1 < 9 && y1 < 9 && y1 > 0) {
                if (field2[x1 + 1][y1] != "O" && field2[x1][y1 + 1] != "O" && field2[x1 - 1][y1] != "O" && field2[x1][y1 - 1] != "O") {
                    condition2 = true;
                }
            }
        }

    }

    private static void entryShip() {
        for (battleshipmain.Main.Ship ship : battleshipmain.Main.Ship.values()) {
            System.out.println("Enter the coordinates of the " + ship.getName() + " (" + ship.getLength() + " cells):\n");
            while (true) {
                if (arrangeShip(ship)) {
                    break;
                }
            }
            System.out.println();
            printField();

        }
    }

    private static boolean arrangeShip(battleshipmain.Main.Ship ship) {

        String[] arrayCoordinates = scanner.nextLine().trim().toUpperCase(Locale.ROOT).split("\\s+");
        try {
            if (arrayCoordinates.length < 2) {
                return false;
            } else {
                String oneCoordinate = arrayCoordinates[0];
                String twoCoordinate = arrayCoordinates[1];
                int x1 = oneCoordinate.charAt(0) - 65;
                int y1 = Integer.parseInt(oneCoordinate.substring(1)) - 1;
                int x2 = twoCoordinate.charAt(0) - 65;
                int y2 = Integer.parseInt(twoCoordinate.substring(1)) - 1;
                if (x1 == x2) {
                    if (Math.abs(y1 - y2) != ship.getLength() - 1) {
                        System.out.println("Error! Wrong length of the " + ship.getName() + "! Try again:\n");
                        return false;
                    } else {
                        int tempMax = Math.max(y1, y2);
                        int tempMin = Math.min(y1, y2);
                        if (Math.max(y1, y2) != 9) {
                            tempMax += 1;
                            if (!battleTwoEntryCondition) {
                                if (field[x1][tempMax].equalsIgnoreCase("O")) {
                                    System.out.println("Error! You placed it too close to another one. Try again:\n");
                                    return false;
                                }
                            } else {
                                if (field2[x1][tempMax].equalsIgnoreCase("O")) {
                                    System.out.println("Error! You placed it too close to another one. Try again:\n");
                                    return false;
                                }
                            }
                        }
                        if (Math.min(y1, y2) != 0) {
                            tempMin -= 1;
                            if (!battleTwoEntryCondition) {
                                if (field[x1][tempMin].equalsIgnoreCase("O")) {
                                    System.out.println("Error! You placed it too close to another one. Try again:\n");
                                    return false;
                                }
                            } else {
                                if (field2[x1][tempMin].equalsIgnoreCase("O")) {
                                    System.out.println("Error! You placed it too close to another one. Try again:\n");
                                    return false;
                                }
                            }
                        }
                        for (int i = tempMax; i <= tempMin; i++) {
                            if (x1 != 0) {
                                if (!battleTwoEntryCondition) {
                                    if (field[x1 - 1][i].equalsIgnoreCase("O")) {
                                        System.out.println("Error! You placed it too close to another one. Try again:\n");
                                        return false;
                                    }
                                } else {
                                    if (field2[x1 - 1][i].equalsIgnoreCase("O")) {
                                        System.out.println("Error! You placed it too close to another one. Try again:\n");
                                        return false;
                                    }
                                }
                            }
                            if (x1 != 9) {
                                if (!battleTwoEntryCondition) {
                                    if (field[x1 + 1][i].equalsIgnoreCase("O")) {
                                        System.out.println("Error! You placed it too close to another one. Try again:\n");
                                        return false;
                                    }
                                } else {
                                    if (field2[x1 - 1][i].equalsIgnoreCase("O")) {
                                        System.out.println("Error! You placed it too close to another one. Try again:\n");
                                        return false;
                                    }
                                }
                            }
                        }
                        for (int j = Math.min(y1, y2); j <= Math.max(y1, y2); j++) {
                            if (!battleTwoEntryCondition) {
                                if (field[x1][j].equalsIgnoreCase("O")) {
                                    System.out.println("Error! This space is occupied by another ship. Try again:\n");
                                    return false;
                                } else {
                                    field[x1][j] = "O";
                                }
                            } else {
                                if (field2[x1][j].equalsIgnoreCase("O")) {
                                    System.out.println("Error! This space is occupied by another ship. Try again:\n");
                                    return false;
                                } else {
                                    field2[x1][j] = "O";
                                }
                            }
                        }
                    }
                } else if (y1 == y2) {
                    if (Math.abs(x1 - x2) != ship.getLength() - 1) {
                        System.out.println("Error! Wrong length of the " + ship.getName() + "! Try again:\n");
                        return false;
                    }
                    int tempMax = Math.max(x1, x2);
                    int tempMin = Math.min(x1, x2);
                    if (Math.max(x1, x2) != 9) {
                        tempMax += 1;
                        if (!battleTwoEntryCondition) {
                            if (field[tempMax][y1].equalsIgnoreCase("O")) {
                                System.out.println("Error! You placed it too close to another one. Try again:\n");
                                return false;
                            }
                        } else {
                            if (field2[tempMax][y1].equalsIgnoreCase("O")) {
                                System.out.println("Error! You placed it too close to another one. Try again:\n");
                                return false;
                            }
                        }
                    }
                    if (Math.min(x1, x2) != 0) {
                        tempMin -= 1;
                        if (!battleTwoEntryCondition) {
                            if (field[tempMin][y1].equalsIgnoreCase("O")) {
                                System.out.println("Error! You placed it too close to another one. Try again:\n");
                                return false;
                            }
                        } else {
                            if (field2[tempMin][y1].equalsIgnoreCase("O")) {
                                System.out.println("Error! You placed it too close to another one. Try again:\n");
                                return false;
                            }
                        }
                    }
                    for (int i = tempMax; i <= tempMin; i++) {
                        if (y1 != 0) {
                            if (!battleTwoEntryCondition) {
                                if (field[i][y1 - 1].equalsIgnoreCase("O")) {
                                    System.out.println("Error! You placed it too close to another one. Try again:\n");
                                    return false;
                                }
                            } else {
                                if (field2[i][y1 - 1].equalsIgnoreCase("O")) {
                                    System.out.println("Error! You placed it too close to another one. Try again:\n");
                                    return false;
                                }
                            }
                        }
                        if (y1 != 9) {
                            if (!battleTwoEntryCondition) {
                                if (field[i][y1 + 1].equalsIgnoreCase("O")) {
                                    System.out.println("Error! You placed it too close to another one. Try again:\n");
                                    return false;
                                }
                            } else {
                                if (field2[i][y1 + 1].equalsIgnoreCase("O")) {
                                    System.out.println("Error! You placed it too close to another one. Try again:\n");
                                    return false;
                                }
                            }
                        }
                    }
                    for (int j = Math.min(x1, x2); j <= Math.max(x1, x2); j++) {
                        if (!battleTwoEntryCondition) {
                            if (field[j][y1].equalsIgnoreCase("O")) {
                                System.out.println("Error! You placed it too close to another one. Try again:\n");
                                return false;
                            } else {
                                field[j][y1] = "O";
                            }
                        } else {
                            if (field2[j][y1].equalsIgnoreCase("O")) {
                                System.out.println("Error! You placed it too close to another one. Try again:\n");
                                return false;
                            } else {
                                field2[j][y1] = "O";
                            }
                        }
                    }
                } else {
                    System.out.println("Error! Wrong ship location! Try again:\n");
                    return false;
                }
            }
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    public static void printField() {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        char wordField = 'A';
        for (int i = 0; i < 10; i++, wordField++) {
            System.out.print(wordField);
            for (int j = 0; j < 10; j++) {
                if (!battleTwoEntryCondition) {
                    System.out.print(" " + field[i][j]);
                } else {
                    System.out.print(" " + field2[i][j]);
                }
            }
            System.out.println();
        }
        System.out.println();

    }
    public static void print() {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        char wordField = 'A';
        for (int i = 0; i < 10; i++, wordField++) {
            System.out.print(wordField);
            for (int j = 0; j < 10; j++) {
                System.out.print(" "+"~");
            }
            System.out.println();
        }
        System.out.println("---------------------");
    }
}