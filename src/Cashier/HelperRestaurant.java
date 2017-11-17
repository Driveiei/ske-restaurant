package Cashier;

import java.util.Scanner;

/**
 * HelperRestaurantManager is a class that seperate many unnecessaries method to
 * this class from SkeRestaurant
 * 
 * @author Kornphon Noiprasert
 */
public class HelperRestaurant {
	static Scanner scan = new Scanner(System.in);
	static RestaurantManager manager;

	/*
	 * The box to make a small frame
	 */
	public static void lineLong() {
		System.out.println("+--------------------------------+------------+-------------+");
	}

	/*
	 * The box to make a big frame
	 */
	public static void lineShort() {
		System.out.println("+---------------------------------------------------+");
	}

	/*
	 * Auto two line spaces
	 */
	public static void twoSpace() {
		System.out.println();
		System.out.println();
	}

	/*
	 * input choice (command or menu number) and return it.
	 * 
	 * @param choice is menu or command that user input it.
	 */
	public static String putChoice(String choice) {
		System.out.print("Enter your choice : ");
		choice = scan.next();
		choice = errorInput(choice);
		return choice;
	}

	/*
	 * check the error choice if it isn't command or not in a menulist.
	 * 
	 * @param choice is menu or command that user input it.
	 */
	public static String errorInput(String choice) {
		while (manager.check.indexOf(choice) == -1) {
			System.out.println("Error input. Please try again.");
			choice = putChoice(choice);
		}
		return choice;
	}

	/*
	 * input quantity and return it if quantity more than 0.
	 */
	public static int putQuan() {
		System.out.print("Enter your quantity : ");
		int quan = scan.nextInt();
		while (quan <= 0) {
			System.out.println("Input wrong quantity. Please try again.");
			quan = scan.nextInt();
		}
		return quan;
	}

	/*
	 * printOrNot is return true when customer want to checkout if it's a
	 * showOrder but not check out it will return false then it will not print
	 * date and time including money and change
	 * 
	 * @param choice is menu or command that user input it.
	 */
	public static boolean printOrNot(String choice) {
		if (choice.equals("lastReceipt")) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Change words to number
	 * 
	 * @param word is a phrase or sentence that you get.
	 */
	public static int changeToInt(String word) {
		int num = Integer.parseInt(word);
		return num;
	}

	/*
	 * check that user input are not out of bound from the command or menu
	 * numbers.
	 * 
	 * @param choice is menu or command that user input it.
	 */
	public static boolean checkCondition(String choice) {
		if (manager.check.indexOf(choice) != -1) {
			return true;
		} else {
			return false;
		}
	}

}
