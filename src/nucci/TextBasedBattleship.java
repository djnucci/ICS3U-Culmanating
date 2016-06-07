package nucci;

import java.util.Scanner;

/**
 * This program is a text based battleship game
 * @version June 1st, 2016
 * @author Daniel Nucci
 */
public class TextBasedBattleship {

	public static boolean onePlayer = false;

	/**
	 * This is the main method
	 * 
	 * @param args
	 *            String[]
	 */
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner read = new Scanner(System.in);

		String numberOfPlayers = "";

		int[] currentCoordinants = new int[2];

		boolean[] error = new boolean[10];

		char[][] playerOneField = new char[10][10];
		for (int i = 0; i < playerOneField.length; i++) {
			for (int j = 0; j < playerOneField[0].length; j++) {
				playerOneField[i][j] = '~';
			}
		}
		char[][] playerTwoField = new char[10][10];
		for (int i = 0; i < playerTwoField.length; i++) {
			for (int j = 0; j < playerTwoField[0].length; j++) {
				playerTwoField[i][j] = '~';
			}
		}

		String[] listOfShips = {"Destroyer", "Cruiser", "Submarine", "Battleship", "Aircraft Carrier"};

		String[][] playerOneShips = new String[10][10];
		String[][] playerTwoShips = new String[10][10];
		
		String[] playerOneShipLocations = new String[5];
		String[] playerTwoShipLocations = new String[5];

		String[] playerOneShipDirections = new String[5];
		String[] playerTwoShipDirections = new String[5];

		
		//start of the priliminary game
		System.out.println("Please enter the number of players (One / Two)");
		do {
			numberOfPlayers = read.nextLine();
			if (numberOfPlayers.equalsIgnoreCase("One")) {
				onePlayer = true;
				error[0] = false;
			}
			else if (numberOfPlayers.equalsIgnoreCase("Two")) {
				onePlayer = false;
				error[0] = false;
			}
			else {
				System.out.println("Please enter a valid answer");
				error[0] = true;
			}
		} while (error[0]);

		if (numberOfPlayers() == 1) {
			printPlayerField(playerOneField);
			for (int i = 0; i < 5; i++) {
				System.out.println("Where would you like to put your " + listOfShips[i] + "? (e.g. b, 5)");
				playerOneShipLocations[i] = read.nextLine();
				currentCoordinants = makeCoords(playerOneShipLocations[i]);
				System.out.println("What direction would you like to put your " + listOfShips[i] + "? (e.g. up, down)");
				playerOneShipDirections[i] = read.nextLine();
				if (isValidShip(playerOneField, currentCoordinants, listOfShips[i], playerOneShipDirections[i])) {
					addShip(playerOneField, playerOneShips, currentCoordinants, listOfShips[i], playerOneShipDirections[i]);
				}
				else {
					System.out.println("That was not a valid input, please enter a correct input for your " + listOfShips[i] + ".");
					i--;
				}
				printPlayerField(playerOneField);
			}
			System.out.println("The AI will now set its field");
			
		}
		else if (numberOfPlayers() == 2) {
			System.out.println("Player 2 please look away from the screen as Player 1 inputs his ships.");
			printPlayerField(playerOneField);
			for (int i = 0; i < 5; i++) {
				System.out.println("Player 1 where would you like to put your " + listOfShips[i] + "? (e.g. b, 5)");
				playerOneShipLocations[i] = read.nextLine();
				currentCoordinants = makeCoords(playerOneShipLocations[i]);
				System.out.println("Player 1 what direction would you like to put your " + listOfShips[i] + "? (e.g. up, down)");
				playerOneShipDirections[i] = read.nextLine();
				if (isValidShip(playerOneField, currentCoordinants, listOfShips[i], playerOneShipDirections[i])) {
					addShip(playerOneField, playerOneShips, currentCoordinants, listOfShips[i], playerOneShipDirections[i]);
				}
				else {
					System.out.println("That was not a valid input, please enter a correct input for your " + listOfShips[i] + ".");
					i--;
				}
				printPlayerField(playerOneField);
			}
			System.out.println("Player 1 please look away from the screen as Player 2 inputs his ships.");
			printPlayerField(playerTwoField);
			for (int i = 0; i < 5; i++) {
				System.out.println("Player 2 where would you like to put your " + listOfShips[i] + "? (e.g. b, 5)");
				playerTwoShipLocations[i] = read.nextLine();
				currentCoordinants = makeCoords(playerOneShipLocations[i]);
				System.out.println("Player 2 what direction would you like to put your " + listOfShips[i] + "? (e.g. up, down)");
				playerTwoShipDirections[i] = read.nextLine();
				if (isValidShip(playerTwoField, currentCoordinants, listOfShips[i], playerOneShipDirections[i])) {
					addShip(playerTwoField, playerTwoShips, currentCoordinants, listOfShips[i], playerOneShipDirections[i]);
				}
				else {
					System.out.println("That was not a valid input, please enter a correct input for your " + listOfShips[i] + ".");
					i--;
				}
				printPlayerField(playerTwoField);
			}
		}
		//end of the priliminary game and the start of the battling portion
		
		while(isAlive(playerOneField) || isAlive(playerTwoField)){
			break;
		}

	}

	/**
	 * This method prints the field according to the char[][] of field elements
	 * 
	 * @param playerElements
	 *            - char[][] The predetermined field elements
	 */
	public static void printPlayerField(char[][] playerElements) {
		System.out.println("    1   2   3   4   5   6   7   8   9  10  ");
		System.out.println("  <--------------------------------------->");
		System.out.println("a | " + playerElements[0][0] + " | " + playerElements[0][1] + " | " + playerElements[0][2] + " | " + playerElements[0][3] + " | " + playerElements[0][4] + " | " + playerElements[0][5] + " | " + playerElements[0][6] + " | " + playerElements[0][7] + " | " + playerElements[0][8] + " | " + playerElements[0][9] + " |");
		System.out.println("  |---------------------------------------|");
		System.out.println("b | " + playerElements[1][0] + " | " + playerElements[1][1] + " | " + playerElements[1][2] + " | " + playerElements[1][3] + " | " + playerElements[1][4] + " | " + playerElements[1][5] + " | " + playerElements[1][6] + " | " + playerElements[1][7] + " | " + playerElements[1][8] + " | " + playerElements[1][9] + " |");
		System.out.println("  |---------------------------------------|");
		System.out.println("c | " + playerElements[2][0] + " | " + playerElements[2][1] + " | " + playerElements[2][2] + " | " + playerElements[2][3] + " | " + playerElements[2][4] + " | " + playerElements[2][5] + " | " + playerElements[2][6] + " | " + playerElements[2][7] + " | " + playerElements[2][8] + " | " + playerElements[2][9] + " |");
		System.out.println("  |---------------------------------------|");
		System.out.println("d | " + playerElements[3][0] + " | " + playerElements[3][1] + " | " + playerElements[3][2] + " | " + playerElements[3][3] + " | " + playerElements[3][4] + " | " + playerElements[3][5] + " | " + playerElements[3][6] + " | " + playerElements[3][7] + " | " + playerElements[3][8] + " | " + playerElements[3][9] + " |");
		System.out.println("  |---------------------------------------|");
		System.out.println("e | " + playerElements[4][0] + " | " + playerElements[4][1] + " | " + playerElements[4][2] + " | " + playerElements[4][3] + " | " + playerElements[4][4] + " | " + playerElements[4][5] + " | " + playerElements[4][6] + " | " + playerElements[4][7] + " | " + playerElements[4][8] + " | " + playerElements[4][9] + " |");
		System.out.println("  |---------------------------------------|");
		System.out.println("f | " + playerElements[5][0] + " | " + playerElements[5][1] + " | " + playerElements[5][2] + " | " + playerElements[5][3] + " | " + playerElements[5][4] + " | " + playerElements[5][5] + " | " + playerElements[5][6] + " | " + playerElements[5][7] + " | " + playerElements[5][8] + " | " + playerElements[5][9] + " |");
		System.out.println("  |---------------------------------------|");
		System.out.println("g | " + playerElements[6][0] + " | " + playerElements[6][1] + " | " + playerElements[6][2] + " | " + playerElements[6][3] + " | " + playerElements[6][4] + " | " + playerElements[6][5] + " | " + playerElements[6][6] + " | " + playerElements[6][7] + " | " + playerElements[6][8] + " | " + playerElements[6][9] + " |");
		System.out.println("  |---------------------------------------|");
		System.out.println("h | " + playerElements[7][0] + " | " + playerElements[7][1] + " | " + playerElements[7][2] + " | " + playerElements[7][3] + " | " + playerElements[7][4] + " | " + playerElements[7][5] + " | " + playerElements[7][6] + " | " + playerElements[7][7] + " | " + playerElements[7][8] + " | " + playerElements[7][9] + " |");
		System.out.println("  |---------------------------------------|");
		System.out.println("i | " + playerElements[8][0] + " | " + playerElements[8][1] + " | " + playerElements[8][2] + " | " + playerElements[8][3] + " | " + playerElements[8][4] + " | " + playerElements[8][5] + " | " + playerElements[8][6] + " | " + playerElements[8][7] + " | " + playerElements[8][8] + " | " + playerElements[8][9] + " |");
		System.out.println("  |---------------------------------------|");
		System.out.println("j | " + playerElements[9][0] + " | " + playerElements[9][1] + " | " + playerElements[9][2] + " | " + playerElements[9][3] + " | " + playerElements[9][4] + " | " + playerElements[9][5] + " | " + playerElements[9][6] + " | " + playerElements[9][7] + " | " + playerElements[9][8] + " | " + playerElements[9][9] + " |");
		System.out.println("  <--------------------------------------->");

	}

	//@formatter:off
	/**
	 * This method turns a user input to 
	 * 
	 * @param userInput
	 * 			- String the users input to be turned into coordinatnts
	 * @return an array of the coordinants for the board (minimum of 0, 0 and maximum 9, 9) and returns -1 if invalid  
	 */
	public static int[] makeCoords (String userInput){
		String[] splitInput = userInput.split(", ");
		int[] coordinants = new int[2];
		try {
			coordinants[1] = Integer.parseInt(splitInput[1]) - 1;
		}catch(NumberFormatException | ArrayIndexOutOfBoundsException ie){
			coordinants[1] = -1;
		}
		switch(splitInput[0]){
			case "A" : coordinants[0] = 0;
			break;
			case "a" : coordinants[0] = 0;
			break;
			case "B" : coordinants[0] = 1;
			break;
			case "b" : coordinants[0] = 1;
			break;
			case "C" : coordinants[0] = 2;
			break;
			case "c" : coordinants[0] = 2;
			break;
			case "D" : coordinants[0] = 3;
			break;
			case "d" : coordinants[0] = 3;
			break;
			case "E" : coordinants[0] = 4;
			break;
			case "e" : coordinants[0] = 4;
			break;
			case "F" : coordinants[0] = 5;
			break;
			case "f" : coordinants[0] = 5;
			break;
			case "G" : coordinants[0] = 6;
			break;
			case "g" : coordinants[0] = 6;
			break;
			case "H" : coordinants[0] = 7;
			break;
			case "h" : coordinants[0] = 7;
			break;
			case "I" : coordinants[0] = 8;
			break;
			case "i" : coordinants[0] = 8;
			break;
			case "J" : coordinants[0] = 9;
			break;
			case "j" : coordinants[0] = 9;
			break;
			default  : coordinants[0] = -1;
			break;
		}
		return coordinants;
	}
	
	//@formatter:on
	/**
	 * This method returns the number of players in the current game
	 * 
	 * @return the number of players in the current game (-1 if invalid)
	 */
	public static int numberOfPlayers() {
		if (onePlayer) {
			return 1;
		}
		else if (!onePlayer) {
			return 2;
		}
		return -1;
	}

	/**
	 * This method checks if a places a ship in the desired location on the board
	 * 
	 * @param playerElements
	 *            char[][] - the board
	 * @param coordinants
	 *            int[] - the coordinants on the board where the ship is to be placed
	 * @param shipType
	 *            String - the type of ship (e.g. battleship, cruiser, submarine)
	 * @param direction
	 *            String - the direction (e.g. up, down, left, right)
	 * @return if the ship placements is valid
	 */
	public static boolean isValidShip(char[][] playerElements, int[] coordinants, String shipType, String direction) {
		int shipSize = 0;

		if (shipType.equalsIgnoreCase("Destroyer")) {
			shipSize = 2;
		}
		else if (shipType.equalsIgnoreCase("Cruiser") || shipType.equalsIgnoreCase("Submarine")) {
			shipSize = 3;
		}
		else if (shipType.equalsIgnoreCase("Battleship")) {
			shipSize = 4;
		}
		else if (shipType.equalsIgnoreCase("Aircraft Carrier")) {
			shipSize = 5;
		}

		if (direction.equalsIgnoreCase("down")) {
			for (int i = 0; i < shipSize; i++) {
				try {
					if (playerElements[coordinants[0] + i][coordinants[1]] == '#') {
						shipSize = 1 / 0;
					}
					playerElements[coordinants[0] + i][coordinants[1]] = playerElements[coordinants[0] + i][coordinants[1]];
				}
				catch (ArrayIndexOutOfBoundsException | ArithmeticException ie) {
					return false;
				}
			}
		}
		else if (direction.equalsIgnoreCase("right")) {
			for (int i = 0; i < shipSize; i++) {
				try {
					if (playerElements[coordinants[0]][coordinants[1] + i] == '#') {
						shipSize = 1 / 0;
					}
					playerElements[coordinants[0]][coordinants[1] + i] = playerElements[coordinants[0]][coordinants[1] + i];
				}
				catch (ArrayIndexOutOfBoundsException | ArithmeticException ie) {
					return false;
				}
			}
		}
		else if (direction.equalsIgnoreCase("up")) {
			for (int i = 0; i < shipSize; i++) {
				try {
					if (playerElements[coordinants[0] - i][coordinants[1]] == '#') {
						shipSize = 1 / 0;
					}
					playerElements[coordinants[0] - i][coordinants[1]] = playerElements[coordinants[0] - i][coordinants[1]];
				}
				catch (ArrayIndexOutOfBoundsException | ArithmeticException ie) {
					return false;
				}
			}
		}
		else if (direction.equalsIgnoreCase("left")) {
			for (int i = 0; i < shipSize; i++) {
				try {
					if (playerElements[coordinants[0]][coordinants[1] - i] == '#') {
						shipSize = 1 / 0;
					}
					playerElements[coordinants[0]][coordinants[1] - i] = playerElements[coordinants[0]][coordinants[1] - i];
				}
				catch (ArrayIndexOutOfBoundsException | ArithmeticException ie) {
					return false;
				}
			}
		}
		else {
			return false;
		}
		return true;
	}

	/**
	 * This method places a ship in the desired location on the board
	 * 
	 * @param playerElements
	 * 			char[][] - the board
	 * @param shipPlacement
	 * 			String[][] - the location of each individual ship for recording purposes
	 * @param coordinants
	 *          int[] - the coordinants on the board where the ship is to be placed
	 * @param shipType
	 *          String - the type of ship (e.g. battleship, cruiser, submarine)
	 * @param direction
	 *          String - the direction (e.g. up, down, left, right)
	 * @return (never returns) this method edits the main board array
	 */
	public static void addShip(char[][] playerElements, String[][] shipPlacement, int[] coordinants, String shipType, String direction) {
		int shipSize = 0;

		if (shipType.equalsIgnoreCase("Destroyer")) {
			shipSize = 2;
		}
		else if (shipType.equalsIgnoreCase("Cruiser") || shipType.equalsIgnoreCase("Submarine")) {
			shipSize = 3;
		}
		else if (shipType.equalsIgnoreCase("Battleship")) {
			shipSize = 4;
		}
		else if (shipType.equalsIgnoreCase("Aircraft Carrier")) {
			shipSize = 5;
		}

		if (direction.equalsIgnoreCase("down")) {
			for (int i = 0; i < shipSize; i++) {
					playerElements[coordinants[0] + i][coordinants[1]] = '#';
					shipPlacement [coordinants[0] + i][coordinants[1]] = shipType;
			}
		}
		else if (direction.equalsIgnoreCase("right")) {
			for (int i = 0; i < shipSize; i++) {
					playerElements[coordinants[0]][coordinants[1] + i] = '#';
					shipPlacement [coordinants[0]][coordinants[1] + i] = shipType;
			}
		}
		else if (direction.equalsIgnoreCase("up")) {
			for (int i = 0; i < shipSize; i++) {
					playerElements[coordinants[0] - i][coordinants[1]] = '#';
					shipPlacement [coordinants[0] - i][coordinants[1]] = shipType;
			}
		}
		else if (direction.equalsIgnoreCase("left")) {
			for (int i = 0; i < shipSize; i++) {
					playerElements[coordinants[0]][coordinants[1] - i] = '#';
					shipPlacement [coordinants[0]][coordinants[1] - i] = shipType;
			}
		}
	}

	public static void makeAIField(char[][] playerElements, String[] ships){
		for (int i = 0; i < 5; i++){
			int randomDirection = (int) ((Math.random() * 4) + 1);
			String directionWord = "";
			switch(randomDirection){
				case 1 : directionWord = "up";
				break;
				case 2 : directionWord = "right";
				break;
				case 3 : directionWord = "down";
				break;
				default : directionWord = "left";
				break;
			}
			
			
			
			
			
			
			
			
			
			
			
		}
	}
	
	/**
	 * This method returns if there is a ship left on the field
	 * 
	 * @param fieldElements
	 * 			char[][] - the board
	 * @return if the field has a ship on it
	 */
	public static boolean isAlive(char[][] fieldElements){
		for (int i = 0; i < fieldElements.length; i++){
			for (int j = 0; j < fieldElements[0].length; j++){
				if (fieldElements[i][j] == '#'){
					return true;
				}
			}
		}
		return false;
	}
}
