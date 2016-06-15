package nucci;

import java.awt.Color;

import hsa_new.Console;

/**
 * This program is a text based battleship game
 * 
 * @version June 1st, 2016
 * @author Daniel Nucci
 */
public class TextBasedBattleship {

	public static boolean singlePlayer = false;
	public static Console console = new Console(28, 84);
	public static int turn = 1; // if turn is an odd number it is player 1's turn and vice versa
	public static boolean destroyerSunk = false;
	public static boolean cruiserSunk = false;
	public static boolean submarineSunk = false;
	public static boolean battleshipSunk = false;
	public static boolean carrierSunk = false;
	public static boolean hitLastShot = false;

	public static int[] previousCoordinants = new int[2];

	/**
	 * This is the main method
	 * 
	 * @param args
	 *            String[]
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		console.setTextBackgroundColor(Color.BLACK);
		console.setTextColor(Color.GREEN);
		console.clear();

		String numberOfPlayers = "";

		int[] currentCoordinants = new int[2];

		boolean error = false;

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

		String playerOneShot = new String();
		String playerTwoShot = new String();

		// start of the priliminary game
		console.println("Please enter the number of players (One / Two)");
		do {
			numberOfPlayers = console.readLine();
			if (numberOfPlayers.equalsIgnoreCase("One")) {
				singlePlayer = true;
				error = false;
			}
			else if (numberOfPlayers.equalsIgnoreCase("Two")) {
				singlePlayer = false;
				error = false;
			}
			else {
				console.println("Please enter a valid answer");
				Thread.sleep(1000);
				console.clear();
				error = true;
			}
		} while (error);
		console.clear();
		
		//if there is only one player get all the inputs from the user and then set up the ai field
		if (numberOfPlayers() == 1) {
			printPlayerField(playerOneField);
			for (int i = 0; i < 5; i++) {
				console.println("Where would you like to put your " + listOfShips[i] + "? (e.g. b, 5)");
				playerOneShipLocations[i] = console.readLine();
				currentCoordinants = makeCoords(playerOneShipLocations[i]);
				console.println("What direction would you like to put your " + listOfShips[i] + "? (e.g. up, down)");
				playerOneShipDirections[i] = console.readLine();
				if (isValidShip(playerOneField, currentCoordinants, listOfShips[i], playerOneShipDirections[i])) {
					addShip(playerOneField, playerOneShips, currentCoordinants, listOfShips[i], playerOneShipDirections[i]);
				}
				else {
					console.println("That was not a valid input, please enter a correct input for your " + listOfShips[i] + ".");
					Thread.sleep(2000);
					i--;
				}
				console.clear();
				printPlayerField(playerOneField);
			}
			console.println("The AI will now set its field");
			makeAIField(playerTwoField, listOfShips, playerTwoShips);
		}
		
		//if there is two players get all the inputs from both users to set the field
		else if (numberOfPlayers() == 2) {
			console.clear();
			console.println("Player 2 please look away from the screen as Player 1 inputs his ships.");
			Thread.sleep(5000);
			console.clear();
			printPlayerField(playerOneField);
			for (int i = 0; i < 5; i++) {
				console.println("Player 1 where would you like to put your " + listOfShips[i] + "? (e.g. b, 5)");
				playerOneShipLocations[i] = console.readLine();
				currentCoordinants = makeCoords(playerOneShipLocations[i]);
				console.println("Player 1 what direction would you like to put your " + listOfShips[i] + "? (e.g. up, down)");
				playerOneShipDirections[i] = console.readLine();
				if (isValidShip(playerOneField, currentCoordinants, listOfShips[i], playerOneShipDirections[i])) {
					addShip(playerOneField, playerOneShips, currentCoordinants, listOfShips[i], playerOneShipDirections[i]);
				}
				else {
					console.println("That was not a valid input, please enter a correct input for your " + listOfShips[i] + ".");
					Thread.sleep(2000);
					i--;
				}
				console.clear();
				printPlayerField(playerOneField);
			}
			console.clear();
			console.println("Player 1 please look away from the screen as Player 2 inputs his ships.");
			Thread.sleep(5000);
			console.clear();
			printPlayerField(playerTwoField);
			for (int i = 0; i < 5; i++) {
				console.println("Player 2 where would you like to put your " + listOfShips[i] + "? (e.g. b, 5)");
				playerTwoShipLocations[i] = console.readLine();
				currentCoordinants = makeCoords(playerOneShipLocations[i]);
				console.println("Player 2 what direction would you like to put your " + listOfShips[i] + "? (e.g. up, down)");
				playerTwoShipDirections[i] = console.readLine();
				if (isValidShip(playerTwoField, currentCoordinants, listOfShips[i], playerOneShipDirections[i])) {
					addShip(playerTwoField, playerTwoShips, currentCoordinants, listOfShips[i], playerOneShipDirections[i]);
				}
				else {
					console.println("That was not a valid input, please enter a correct input for your " + listOfShips[i] + ".");
					Thread.sleep(2000);
					i--;
				}
				console.clear();
				printPlayerField(playerTwoField);
			}
		}
		// end of the set-up and the start of the battling portion

		while (isAlive(playerOneField) && isAlive(playerTwoField)) {
			console.clear();
			
			//player 1's turn if the number of turns is odd
			if (turn % 2 == 1) {
				printOpponentsField(playerTwoField);
				console.println("Where would you like to shoot, player 1?");
				playerOneShot = console.readLine();
				currentCoordinants = makeCoords(playerOneShot);
				if (isValidShot(playerTwoField, currentCoordinants)) {
					takeShot(playerTwoField, playerTwoShips, currentCoordinants);
					console.clear();
					printOpponentsField(playerTwoField);
					createDeathMessage(playerTwoShips);
					Thread.sleep(3000);
				}
				//if there was an invalid shot tell the user and subtract the turn
				else {
					console.println("Please enter a correct coordinant for your shot.");
					console.println("(make sure that you haven't already shot there)");
					Thread.sleep(2000);
					turn--;
				}
				console.clear();
			}
			
			//player 2's (or the ai's if single player is true) turn if the number of turns is even
			else {
				if (singlePlayer) {
					aiShoot(playerOneField, playerOneShips);
				}
				else {
					printOpponentsField(playerOneField);
					console.println("Where would you like to shoot, player 2?");
					playerTwoShot = console.readLine();
					currentCoordinants = makeCoords(playerTwoShot);
					if (isValidShot(playerOneField, currentCoordinants)) {
						takeShot(playerOneField, playerOneShips, currentCoordinants);
						console.clear();
						printOpponentsField(playerOneField);
						createDeathMessage(playerOneShips);
						Thread.sleep(3000);
					}
					//if there was an invalid shot tell the user and subtract the turn
					else {
						console.println("Please enter a correct coordinant for your shot.");
						console.println("(make sure that you haven't already shot there)");
						Thread.sleep(2000);
						turn--;
					}
					console.clear();
				}
			}
			//add a turn every turn
			turn++;
		}
		
		//end game to see who beat who
		console.clear();
		if (isAlive(playerOneField)) {
			console.println("Congradulations player 1, you win!");
		}
		else if (isAlive(playerTwoField)) {
			if (!singlePlayer) {
				console.println("Congradulations player 2, you win!");
			}
			else {
				console.println("You lost to the AI, good job.");
			}
		}
		else {
			console.println("Some how you broke the game.");
		}

	}

	/**
	 * print the field according to the char[][] of field elements
	 * 
	 * @param playerElements
	 *            - char[][] The predetermined field elements
	 */
	public static void printPlayerField(char[][] playerElements) {
		console.println("    1   2   3   4   5   6   7   8   9  10  ");
		console.println("  /---------------------------------------\\");
		console.println("a | " + playerElements[0][0] + " | " + playerElements[0][1] + " | " + playerElements[0][2] + " | " + playerElements[0][3] + " | " + playerElements[0][4] + " | " + playerElements[0][5] + " | " + playerElements[0][6] + " | " + playerElements[0][7] + " | " + playerElements[0][8] + " | " + playerElements[0][9] + " |");
		console.println("  |---------------------------------------|");
		console.println("b | " + playerElements[1][0] + " | " + playerElements[1][1] + " | " + playerElements[1][2] + " | " + playerElements[1][3] + " | " + playerElements[1][4] + " | " + playerElements[1][5] + " | " + playerElements[1][6] + " | " + playerElements[1][7] + " | " + playerElements[1][8] + " | " + playerElements[1][9] + " |");
		console.println("  |---------------------------------------|");
		console.println("c | " + playerElements[2][0] + " | " + playerElements[2][1] + " | " + playerElements[2][2] + " | " + playerElements[2][3] + " | " + playerElements[2][4] + " | " + playerElements[2][5] + " | " + playerElements[2][6] + " | " + playerElements[2][7] + " | " + playerElements[2][8] + " | " + playerElements[2][9] + " |");
		console.println("  |---------------------------------------|");
		console.println("d | " + playerElements[3][0] + " | " + playerElements[3][1] + " | " + playerElements[3][2] + " | " + playerElements[3][3] + " | " + playerElements[3][4] + " | " + playerElements[3][5] + " | " + playerElements[3][6] + " | " + playerElements[3][7] + " | " + playerElements[3][8] + " | " + playerElements[3][9] + " |");
		console.println("  |---------------------------------------|");
		console.println("e | " + playerElements[4][0] + " | " + playerElements[4][1] + " | " + playerElements[4][2] + " | " + playerElements[4][3] + " | " + playerElements[4][4] + " | " + playerElements[4][5] + " | " + playerElements[4][6] + " | " + playerElements[4][7] + " | " + playerElements[4][8] + " | " + playerElements[4][9] + " |");
		console.println("  |---------------------------------------|");
		console.println("f | " + playerElements[5][0] + " | " + playerElements[5][1] + " | " + playerElements[5][2] + " | " + playerElements[5][3] + " | " + playerElements[5][4] + " | " + playerElements[5][5] + " | " + playerElements[5][6] + " | " + playerElements[5][7] + " | " + playerElements[5][8] + " | " + playerElements[5][9] + " |");
		console.println("  |---------------------------------------|");
		console.println("g | " + playerElements[6][0] + " | " + playerElements[6][1] + " | " + playerElements[6][2] + " | " + playerElements[6][3] + " | " + playerElements[6][4] + " | " + playerElements[6][5] + " | " + playerElements[6][6] + " | " + playerElements[6][7] + " | " + playerElements[6][8] + " | " + playerElements[6][9] + " |");
		console.println("  |---------------------------------------|");
		console.println("h | " + playerElements[7][0] + " | " + playerElements[7][1] + " | " + playerElements[7][2] + " | " + playerElements[7][3] + " | " + playerElements[7][4] + " | " + playerElements[7][5] + " | " + playerElements[7][6] + " | " + playerElements[7][7] + " | " + playerElements[7][8] + " | " + playerElements[7][9] + " |");
		console.println("  |---------------------------------------|");
		console.println("i | " + playerElements[8][0] + " | " + playerElements[8][1] + " | " + playerElements[8][2] + " | " + playerElements[8][3] + " | " + playerElements[8][4] + " | " + playerElements[8][5] + " | " + playerElements[8][6] + " | " + playerElements[8][7] + " | " + playerElements[8][8] + " | " + playerElements[8][9] + " |");
		console.println("  |---------------------------------------|");
		console.println("j | " + playerElements[9][0] + " | " + playerElements[9][1] + " | " + playerElements[9][2] + " | " + playerElements[9][3] + " | " + playerElements[9][4] + " | " + playerElements[9][5] + " | " + playerElements[9][6] + " | " + playerElements[9][7] + " | " + playerElements[9][8] + " | " + playerElements[9][9] + " |");
		console.println("  \\---------------------------------------/");

	}

	/**
	 * print the field according to the char[][] of field elements without the ships
	 * 
	 * @param playerElements
	 *            - char[][] The predetermined field elements
	 */
	public static void printOpponentsField(char[][] playerElements) {
		char[][] foggyField = new char[10][10];

		for (int i = 0; i < playerElements.length; i++) {
			for (int j = 0; j < playerElements[0].length; j++) {
				if (playerElements[i][j] != '#') {
					foggyField[i][j] = playerElements[i][j];
				}
				else {
					foggyField[i][j] = '~';
				}
			}
		}

		console.println("    1   2   3   4   5   6   7   8   9  10  ");
		console.println("  /---------------------------------------\\");
		console.println("a | " + foggyField[0][0] + " | " + foggyField[0][1] + " | " + foggyField[0][2] + " | " + foggyField[0][3] + " | " + foggyField[0][4] + " | " + foggyField[0][5] + " | " + foggyField[0][6] + " | " + foggyField[0][7] + " | " + foggyField[0][8] + " | " + foggyField[0][9] + " |");
		console.println("  |---------------------------------------|");
		console.println("b | " + foggyField[1][0] + " | " + foggyField[1][1] + " | " + foggyField[1][2] + " | " + foggyField[1][3] + " | " + foggyField[1][4] + " | " + foggyField[1][5] + " | " + foggyField[1][6] + " | " + foggyField[1][7] + " | " + foggyField[1][8] + " | " + foggyField[1][9] + " |");
		console.println("  |---------------------------------------|");
		console.println("c | " + foggyField[2][0] + " | " + foggyField[2][1] + " | " + foggyField[2][2] + " | " + foggyField[2][3] + " | " + foggyField[2][4] + " | " + foggyField[2][5] + " | " + foggyField[2][6] + " | " + foggyField[2][7] + " | " + foggyField[2][8] + " | " + foggyField[2][9] + " |");
		console.println("  |---------------------------------------|");
		console.println("d | " + foggyField[3][0] + " | " + foggyField[3][1] + " | " + foggyField[3][2] + " | " + foggyField[3][3] + " | " + foggyField[3][4] + " | " + foggyField[3][5] + " | " + foggyField[3][6] + " | " + foggyField[3][7] + " | " + foggyField[3][8] + " | " + foggyField[3][9] + " |");
		console.println("  |---------------------------------------|");
		console.println("e | " + foggyField[4][0] + " | " + foggyField[4][1] + " | " + foggyField[4][2] + " | " + foggyField[4][3] + " | " + foggyField[4][4] + " | " + foggyField[4][5] + " | " + foggyField[4][6] + " | " + foggyField[4][7] + " | " + foggyField[4][8] + " | " + foggyField[4][9] + " |");
		console.println("  |---------------------------------------|");
		console.println("f | " + foggyField[5][0] + " | " + foggyField[5][1] + " | " + foggyField[5][2] + " | " + foggyField[5][3] + " | " + foggyField[5][4] + " | " + foggyField[5][5] + " | " + foggyField[5][6] + " | " + foggyField[5][7] + " | " + foggyField[5][8] + " | " + foggyField[5][9] + " |");
		console.println("  |---------------------------------------|");
		console.println("g | " + foggyField[6][0] + " | " + foggyField[6][1] + " | " + foggyField[6][2] + " | " + foggyField[6][3] + " | " + foggyField[6][4] + " | " + foggyField[6][5] + " | " + foggyField[6][6] + " | " + foggyField[6][7] + " | " + foggyField[6][8] + " | " + foggyField[6][9] + " |");
		console.println("  |---------------------------------------|");
		console.println("h | " + foggyField[7][0] + " | " + foggyField[7][1] + " | " + foggyField[7][2] + " | " + foggyField[7][3] + " | " + foggyField[7][4] + " | " + foggyField[7][5] + " | " + foggyField[7][6] + " | " + foggyField[7][7] + " | " + foggyField[7][8] + " | " + foggyField[7][9] + " |");
		console.println("  |---------------------------------------|");
		console.println("i | " + foggyField[8][0] + " | " + foggyField[8][1] + " | " + foggyField[8][2] + " | " + foggyField[8][3] + " | " + foggyField[8][4] + " | " + foggyField[8][5] + " | " + foggyField[8][6] + " | " + foggyField[8][7] + " | " + foggyField[8][8] + " | " + foggyField[8][9] + " |");
		console.println("  |---------------------------------------|");
		console.println("j | " + foggyField[9][0] + " | " + foggyField[9][1] + " | " + foggyField[9][2] + " | " + foggyField[9][3] + " | " + foggyField[9][4] + " | " + foggyField[9][5] + " | " + foggyField[9][6] + " | " + foggyField[9][7] + " | " + foggyField[9][8] + " | " + foggyField[9][9] + " |");
		console.println("  \\---------------------------------------/");

	}

	//@formatter:off
	/**
	 * turn a user input to broken up coordinants
	 * 
	 * @param userInput
	 * 			- String the users input to be turned into coordinants
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
	 * return the number of players in the current game
	 * 
	 * @return the number of players in the current game (-1 if invalid)
	 */
	public static int numberOfPlayers() {
		if (singlePlayer) {
			return 1;
		}
		else if (!singlePlayer) {
			return 2;
		}
		return -1;
	}

	/**
	 * check if a places a ship in the desired location on the board
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
	 * place a ship in the desired location on the board
	 * 
	 * @param playerElements
	 *            char[][] - the board
	 * @param shipPlacement
	 *            String[][] - the location of each individual ship for recording purposes
	 * @param coordinants
	 *            int[] - the coordinants on the board where the ship is to be placed
	 * @param shipType
	 *            String - the type of ship (e.g. battleship, cruiser, submarine)
	 * @param direction
	 *            String - the direction (e.g. up, down, left, right)
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
				shipPlacement[coordinants[0] + i][coordinants[1]] = shipType;
			}
		}
		else if (direction.equalsIgnoreCase("right")) {
			for (int i = 0; i < shipSize; i++) {
				playerElements[coordinants[0]][coordinants[1] + i] = '#';
				shipPlacement[coordinants[0]][coordinants[1] + i] = shipType;
			}
		}
		else if (direction.equalsIgnoreCase("up")) {
			for (int i = 0; i < shipSize; i++) {
				playerElements[coordinants[0] - i][coordinants[1]] = '#';
				shipPlacement[coordinants[0] - i][coordinants[1]] = shipType;
			}
		}
		else if (direction.equalsIgnoreCase("left")) {
			for (int i = 0; i < shipSize; i++) {
				playerElements[coordinants[0]][coordinants[1] - i] = '#';
				shipPlacement[coordinants[0]][coordinants[1] - i] = shipType;
			}
		}
	}

	/**
	 * the ai making a field filled with ships
	 * 
	 * @param aiElements
	 *            char[][] - the ai's field elements
	 * @param ships
	 *            String[] - the list of ships
	 * @param shipLocations
	 *            String[][] - the location of each individual ship for recording purposes
	 */
	public static void makeAIField(char[][] aiElements, String[] ships, String[][] shipLocations) {
		for (int i = 0; i < 5; i++) {
			int[] randomPlace = randomCoord(10, 10);
			String directionWord = randomDirection();
			if (isValidShip(aiElements, randomPlace, ships[i], directionWord)) {
				addShip(aiElements, shipLocations, randomPlace, ships[i], directionWord);
			}
			else {
				i--;
			}

		}
	}

	/**
	 * make a new ai shot
	 * 
	 * @param playerElements
	 *            char[][] - the board
	 * @param shipPlacement
	 *            String[][] - the ship locations with the names labelled
	 * @param playerOneField 
	 * @throws InterruptedException 
	 */
	public static void aiShoot(char[][] playerElements, String[][] shipPlacement) throws InterruptedException {
		int[] coordinants = smartCoord();

		if (isValidShot(playerElements, coordinants)) {
			takeShot(playerElements, shipPlacement, coordinants);
			if (playerElements[coordinants[0]][coordinants[1]] == '#') {
				hitLastShot = true;
				previousCoordinants[0] = coordinants[0];
				previousCoordinants[0] = coordinants[1];
			}
			else {
				hitLastShot = false;
			}
			printOpponentsField(playerElements);
			createDeathMessage(shipPlacement);
			console.clear();
			printPlayerField(playerElements);
			Thread.sleep(3000);
			console.clear();
		}
		else {
			turn--;
		}
	}

	/**
	 * make a coordinant for the ai's shot
	 * 
	 * @return the ai's shot coordinant
	 */
	public static int[] smartCoord() {
		int[] coordinants = new int[2];

		if (hitLastShot) {
			String direction = randomDirection();

			switch (direction) {
				case "left" :
					coordinants[1] = previousCoordinants[1] - 1;
					coordinants[0] = previousCoordinants[0];
					break;
				case "right" :
					coordinants[1] = previousCoordinants[1] + 1;
					coordinants[0] = previousCoordinants[0];
					break;
				case "up" :
					coordinants[0] = previousCoordinants[0] - 1;
					coordinants[1] = previousCoordinants[1];
					break;
				case "down" :
					coordinants[0] = previousCoordinants[0] + 1;
					coordinants[1] = previousCoordinants[1];
					break;
			}
		}
		else {
			coordinants = randomCoord(10, 10);
		}

		return coordinants;
	}

	/**
	 * return if there is a ship left on the field
	 * 
	 * @param fieldElements
	 *            char[][] - the board
	 * @return if the field has a ship on it
	 */
	public static boolean isAlive(char[][] fieldElements) {
		for (int i = 0; i < fieldElements.length; i++) {
			for (int j = 0; j < fieldElements[0].length; j++) {
				if (fieldElements[i][j] == '#') {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * make a random number (mostly redundant, i just felt like making it)
	 * 
	 * @param num1
	 *            int - starting number
	 * @param num2
	 *            int - number you want to multiply by
	 * @return (int) ((Math.random() * num2) + num1)
	 */
	public static int newRandom(int num1, int num2) {
		return (int) ((Math.random() * num2) + num1);
	}

	/**
	 * make a new random coordinant point for a grid
	 * 
	 * @param x
	 *            int - # of rows in the grid
	 * @param y
	 *            int - # of columns in the grid
	 * @return an int array of the coordinants
	 */
	public static int[] randomCoord(int x, int y) {
		int[] coordinants = {newRandom(0, x), newRandom(0, y)};

		return coordinants;
	}

	/**
	 * make sure the shot at these coordinants is valid
	 * 
	 * @param playerElements
	 *            char[][] - the board of your opponent
	 * @param coordinants
	 *            int[] - the coordinants of the shot
	 * @return true if a vaild shot and false if not
	 */
	public static boolean isValidShot(char[][] playerElements, int[] coordinants) {
		try {
			if (playerElements[coordinants[0]][coordinants[1]] == '#' || playerElements[coordinants[0]][coordinants[1]] == '~') {
				return true;
			}
			else {
				return false;
			}
		}
		catch (ArrayIndexOutOfBoundsException ie) {
			return false;
		}
	}

	/**
	 * take a shot at a coordinant on a field
	 * 
	 * @param playerElements
	 *            char[][] - the board of your opponent
	 * @param shipPlacement
	 *            String[][] - the ship locations with the names labelled
	 * @param coordinants
	 *            int[] - the coordinants of the shot
	 */
	public static void takeShot(char[][] playerElements, String[][] shipPlacement, int[] coordinants) {
		if (playerElements[coordinants[0]][coordinants[1]] == '#') {
			playerElements[coordinants[0]][coordinants[1]] = '!';
			shipPlacement[coordinants[0]][coordinants[1]] += " - HIT";
		}
		else if (playerElements[coordinants[0]][coordinants[1]] == '~') {
			playerElements[coordinants[0]][coordinants[1]] = '+';
		}

	}

	/**
	 * make a death message appear if the player destroyed a ship (this is called after every shot taken)
	 * 
	 * @param shipPlacement
	 *            String[][] - the ship locations with the names labelled
	 */
	public static void createDeathMessage(String[][] shipPlacement) {
		int destroyerHits = 0;
		int cruiserHits = 0;
		int submarineHits = 0;
		int battleshipHits = 0;
		int carrierHits = 0;

		for (int i = 0; i < shipPlacement.length; i++) {
			for (int j = 0; j < shipPlacement[0].length; j++) {
				try {
					if (shipPlacement[i][j].equalsIgnoreCase("Destroyer - HIT")) {
						destroyerHits++;
					}
					else if (shipPlacement[i][j].equalsIgnoreCase("Cruiser - HIT")) {
						cruiserHits++;
					}
					else if (shipPlacement[i][j].equalsIgnoreCase("Submarine - HIT")) {
						submarineHits++;
					}
					else if (shipPlacement[i][j].equalsIgnoreCase("Battleship - HIT")) {
						battleshipHits++;
					}
					else if (shipPlacement[i][j].equalsIgnoreCase("Aircraft Carrier - HIT")) {
						carrierHits++;
					}

				}
				catch (NullPointerException ie) {
				}
			}
		}

		if (destroyerHits == 2 && !destroyerSunk) {
			console.println("You sunk your opponents destroyer.");
			destroyerSunk = true;
		}
		else if (cruiserHits == 3 && !cruiserSunk) {
			console.println("You sunk your opponents cruiser.");
			cruiserSunk = true;
		}
		else if (submarineHits == 3 && !submarineSunk) {
			console.println("You sunk your opponents submarine.");
			submarineSunk = true;
		}
		else if (battleshipHits == 4 && !battleshipSunk) {
			console.println("You sunk your opponents battleship.");
			battleshipSunk = true;
		}
		else if (carrierHits == 5 && !carrierSunk) {
			console.println("You sunk your opponents airship carrier.");
			carrierSunk = true;
		}
	}

	/**
	 * make a random diection
	 * 
	 * @return the direction in all lower case letters
	 */
	public static String randomDirection() {
		int randomDirection = newRandom(1, 4);
		String directionWord = "";
		switch (randomDirection) {
			case 1 :
				directionWord = "up";
				break;
			case 2 :
				directionWord = "right";
				break;
			case 3 :
				directionWord = "down";
				break;
			default :
				directionWord = "left";
				break;
		}
		return directionWord;
	}
}
