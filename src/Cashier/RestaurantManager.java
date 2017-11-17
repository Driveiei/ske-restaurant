package Cashier;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * RestaurantManager is a class to get menulist,prices and command by reading
 * and loading from menu.txt and command.txt file. Moreover, this class record
 * the order from the customer to the record.txt file.
 * 
 * @author Kornphon Noiprasert
 */
public class RestaurantManager {
	static ArrayList<String> menuItems = new ArrayList<>();
	static ArrayList<Double> prices = new ArrayList<>();
	static ArrayList<String> check = new ArrayList<>();
	static ArrayList<String> command = new ArrayList<>();

	/*
	 * Change menuItems arraylist to array and return allMenu (array) to
	 * SkeRestaurant class.
	 */
	public static String[] getMenuItems() {
		String[] allMenu = menuItems.toArray(new String[menuItems.size()]);
		return allMenu;
	}

	/*
	 * Change prices arraylist to array and return allPrices (array) to
	 * SkeRestaurant class.
	 */
	public static double[] getPrices() {
		double[] allPrices = new double[prices.size()];
		for (int i = 0; i < prices.size(); i++) {
			allPrices[i] = prices.get(i);
		}
		return allPrices;
	}

	/*
	 * Change command arraylist to array and return allCommand (array) to
	 * SkeRestaurant class.
	 */
	public static String[] getCommands() {
		String[] allCommand = command.toArray(new String[command.size()]);
		return allCommand;
	}

	/*
	 * find the location of file, read text in a file
	 */
	private static InputStream readTextMenu() {
		String filename = "data/menu.txt";
		ClassLoader loader = RestaurantManager.class.getClassLoader();
		InputStream in = loader.getResourceAsStream(filename);
		if (in == null) {
			System.out.println("Error reading file from: " + filename);
			return null;
		}
		return in;
	}

	/*
	 * get file from readTextMenu's method and get menu ,price from the text to
	 * Arraylist and add all number of menu to check in Main class.
	 */
	public static void loadTextMenu() {
		InputStream in = readTextMenu();
		Scanner readText = new Scanner(in);
		String[] seperate = new String[2];
		while (readText.hasNextLine()) {
			String list = readText.nextLine();
			if (list.startsWith("#")) {
				continue;
			}
			menuItems.add(list.split(":")[0]);
			prices.add(Double.parseDouble(list.split(":")[1]));
		}
		String addCondition;
		for (int i = 0; i < menuItems.size(); i++) {
			addCondition = Integer.toString(i + 1);
			check.add(addCondition);
		}
		readText.close();
	}

	/*
	 * find the location of file, read text in a file
	 */
	private static InputStream readTextCommand() {
		String filename = "data/command.txt";
		ClassLoader loader = RestaurantManager.class.getClassLoader();
		InputStream in = loader.getResourceAsStream(filename);
		if (in == null) {
			System.out.println("Error reading file from: " + filename);
			return null;
		}
		return in;
	}

	/*
	 * get file from readTextMenuCommand's method and get command from the text
	 * to Arraylist and add all command to check in Main class.
	 */
	public static void loadTextCommand() {
		InputStream in = readTextCommand();
		Scanner readText = new Scanner(in);
		String[] seperate = new String[2];
		while (readText.hasNextLine()) {
			String list = readText.nextLine();
			if (list.startsWith("#")) {
				continue;
			}
			seperate = list.split(":");
			check.add(seperate[0]);
			command.add("[" + seperate[0] + "] - " + seperate[1]);
		}
		readText.close();
	}

	/**
	 * Record the order when customer checkout into a file(record.txt).
	 * 
	 * @param total is the price that customer must paid
	 * for their order.
	 * @param menuData is list of menu that customer order. 
	 * (include menuNumber , menu , quantity and price of menu)
	 * @param money is the value that customer paid to checkout.
	 * @param change is the change that customer 
	 * receives money back from the manager.
	 * @param time is the time that customer checkout
	 * (include year-month-day and hour-minute).
	 */
	public static void recordOrder(double total, String menuData, double money, double change, String time) {
		String outputfile = "src/data/record.txt";
		OutputStream out = null;
		try {
			out = new FileOutputStream(outputfile, true);
		} catch (FileNotFoundException ex) {
			System.out.println("Couldn't open output file " + outputfile);
			return;
		}
		PrintStream printOut = new PrintStream(out);
		printOut.printf("---------------------------------------------------\n");
		printOut.printf("%s\n", time);
		printOut.printf("%s", menuData);
		printOut.printf("Total = %.2f Baht.\n", total);
		printOut.printf("Receive = %.2f Baht.\n", money);
		printOut.printf("Change = %.2f Baht.\n", change);
		printOut.printf("---------------------------------------------------\n\n");
		printOut.close();
	}

}
