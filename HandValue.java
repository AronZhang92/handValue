
/**
 * This is the project to check the hand value for Cribbage game. There will be
 * five input which stand for the five cards. The first four card is the hand
 * cards, and the last card is the start card. The aim of this project is
 * check specific combination to define the value of these five cards.
 * 
 * @author BO ZHANG
 */

public class HandValue {
	// Create a int to calculate the total score, start with 0.
	private static int point = 0;

	public static void main(String[] args) {
		// Create a int list to store the number
		int[] cardnumber = new int[5];
		// Create a char list to store the suit infomation
		char[] cardsuit = new char[5];

		// Get all the number in put from commoned line BUT first The program need to make sure the input is correct

		
		// First check the input is enough for 5 cards
		if (args.length < 5) {
			System.err.println("Not Enough parameters, try again");
			
		}
		// Secondly check each card has its own number and suit
		else if (args[0].length() < 2 || args[1].length() < 2 || args[2].length() < 2 || args[3].length() < 2
				|| args[4].length() < 2) {
			System.err.println("Each input needs two parameters, the number and the suit, try again");
		} else {
			//Create a boolean to make sure all card is legal.
			boolean illegal = false;
			
			//Use for loop to go though all the input
			for (int i = 0; i < args.length; i++) {
				//Use switch to get the number and change the char to int
				//Example: change A to 1, T to 10
				switch (args[i].charAt(0)) {
				case 'A':
					cardnumber[i] = 1;
					break;
				case 'T':
					cardnumber[i] = 10;
					break;
				case 'J':
					cardnumber[i] = 11;
					//Check the 
					// make sure this card is not the |||||||| card
					//
					if (i != 4 && cardsuit[i] == cardsuit[4]) {
						point = point + 1;
					}
					break;
				case 'Q':
					cardnumber[i] = 12;
					break;
				case 'K':
					cardnumber[i] = 13;
					break;
				default:
					//Check the card number is in the range (1 - 13)
					if (args[i].charAt(0) > '9' || args[i].charAt(0) < '1') {
						illegal = true;
					}
					//Use "-'0'" is the best way to change a char to int
					//This is based on the SCAII numer.
					cardnumber[i] = args[i].charAt(0) - '0';
					break;
				}
				//Check the suit is correct
				if (args[i].charAt(1) != 'C') {
					illegal = true;

				}
				//Get the suit of each card.
				cardsuit[i] = args[i].charAt(1);
			}
			//if the card is illegal, stop the program and output the error
			if (illegal) {
				System.err.println("unaccaptable input, try again");
			} 
			// If the card is legal, check the point.
			else {
				//Create new methods to check the point one by one
				//Because the point is global value, change the point in the method matter the value itself.
				check15s(cardnumber);
				//checkpairs(cardnumber);

				//checkflushes(cardsuit);
				//Based on the check runs method, the card need sort first, so , create a sort method to sort both card number and card suit 
				//to make sure all the card remain no change.
			//	sort(cardnumber, cardsuit);

			//	checkruns(cardnumber);

				System.out.println(point);
			}
		}
	}

	public static void display(int[] A) {
		for (int i = 0; i < A.length; i++) {
			System.out.print(A[i] + " ");
		}
		/*System.out.println();
		for (int i = 0; i < B.length; i++) {
			System.out.print(B[i] + " ");
		}*/

	}

	// CHECK THE 15S
	public static void check15s(int[] cardnumber) {
		int sum = 0;
		//First, calculate the sum of all the input
		for (int i = 0; i < cardnumber.length; i++) {
			sum = sum + cardnumber[i];
		}
		//If the sum of all 5 card is 15, then 4 input cannot be 15 anymore
		if (sum == 15) {
			//add 2 point for the 15s
			point = point + 2;
		} 
		//Only if sum of all card is bigger than 15, delete one card could make sum be 15
		else if (sum > 15) {
			//Use a new method to check the sum of cards which delete the 'i' card.
			for (int i = 0; i < cardnumber.length; i++) {
				checksum(cardnumber, i);
				
			}
		}
	}

	public static void checksum(int[] cardnumber, int a) {
		display(cardnumber);
		int sum = 0;
		int backup = 0;
		//Because the cardnumber is an array of int, it need to be remain no change, the backup is make sure it will remain number after the method.
		backup = cardnumber[a];
		//Because the sum is calculated, so, change one element to 0 means delete that element
		cardnumber[a] = 0;
		
		//calculate the sum of cards without the 'a' index card
		for (int i = 0; i < cardnumber.length; i++) {
			sum = sum + cardnumber[i];
		}
		//The sum of other four element is 15
		if (sum == 15) {
			//add 2 point
			point = point + 2;
		} else if (sum > 15) {
			for (int i = a; i < cardnumber.length; i++) {
				if (cardnumber[i] != 0) {
					checksum(cardnumber, i);
				}
			}
		}

		cardnumber[a] = backup;

	}

	// CHECK THE PAIRS.
	public static void checkpairs(int[] cardnumber) {
		for (int i = 0; i < cardnumber.length - 1; i++) {
			for (int j = i + 1; j < cardnumber.length; j++) {
				if (cardnumber[i] == cardnumber[j]) {
					point = point + 2;
				}
			}
		}
	}

	// CHECK THE RUNS
	public static void sort(int[] cardnumber, char[] cardsuit) {
		int min = 100;
		int minorder = 0;
		int swap;
		char swapc;
		for (int i = 0; i < cardnumber.length; i++) {
			min = 100;
			for (int j = i; j < cardnumber.length; j++) {
				if (min > cardnumber[j]) {
					min = cardnumber[j];
					minorder = j;
				}
			}
			swap = cardnumber[i];
			cardnumber[i] = cardnumber[minorder];
			cardnumber[minorder] = swap;

			swapc = cardsuit[i];
			cardsuit[i] = cardsuit[minorder];
			cardsuit[minorder] = swapc;
		}

	}

	public static void checkruns(int[] cardnumber) {
		boolean check = false;
		for (int i = 0; i < cardnumber.length - 2; i++) {
			if (!check && cardnumber[i] == (cardnumber[i + 1] - 1) && cardnumber[i] == (cardnumber[i + 2] - 2)) {
				point = point + 3;
				check = true;
				int j = 3;
				while (i + j < cardnumber.length) {
					if (cardnumber[i] == cardnumber[i + j] - j) {
						point++;
					}
					j++;
				}
			}
		}

	}

	// CHECK THE FLUSHES
	public static void checkflushes(char[] cardsuit) {
		boolean flushes = true;
		for (int i = 0; i < cardsuit.length - 2; i++) {
			if (cardsuit[i] != cardsuit[i + 1]) {
				flushes = false;
			}
		}
		if (flushes) {
			point = point + 4;
			if (cardsuit[4] == cardsuit[3]) {
				point++;
			}
		}

	}

}
