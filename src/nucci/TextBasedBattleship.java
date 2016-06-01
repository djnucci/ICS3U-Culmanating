package nucci;

import java.util.Scanner;

/**
 * @version June 1st, 2016
 * @author Daniel Nucci
 * 
 */
public class TextBasedBattleship {
	public static boolean onePlayer = false;
	
	public static void main(String[] args) {
		Scanner read = new Scanner(System.in);
		
		String userInput = "";
		
		char[][] field = new char[10][10];
		for(int i = 0; i < 10; i++){
			for (int j = 0; j < 10; j++){
				field[i][j] = '~';
			}
		}
		
		System.out.println("Please enter the number of players (One / Two)");
		userInput = read.nextLine();
		
		
		
		printPlayerField(field);

	}

	/**
	 * This method prints the field according to the char[][] of field elements
	 * 
	 * @param playerElements
	 * 			- char[][] The predetermined field elements
	 */
	public static void printPlayerField (char[][] playerElements){
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
	public static int[] makeCoords (String x, String y){
		
		return null;
		
	}
}
