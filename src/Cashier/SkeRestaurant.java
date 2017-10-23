package Cashier;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The method for restaurant manager.
 * 
 * @author Kornphon Noiprasert
 */
public class SkeRestaurant {

	static Scanner scan = new Scanner(System.in);
	static ArrayList<String> command = new ArrayList<>();
	static ArrayList<String> describe = new ArrayList<>();

	/* Show the menu when you press [p] button */
	public static void orderMenu() {
		System.out.println("+------------ WELCOME TO SKE RESTAURANT ------------+");
		for (int i = 0; i < RestaurantManager.names.size(); i++) {
			if (i <= 9) {
				System.out.printf("| [%d.] %-29s|%8.2f Baht. |\n", i + 1, RestaurantManager.names.get(i),
						RestaurantManager.prices.get(i));
			} else {
				System.out.printf("| [%d] %-30s|%8.2f Baht. |\n", i + 1, RestaurantManager.names.get(i),
						RestaurantManager.prices.get(i));
			}
		}
		line();
	}

	/*
	 * Load the information command from command.txt and send back to
	 * RestaurantManager
	 */
	public static ArrayList<String> loadTextTwo() {
		String filename = "Data/command.txt";
		ClassLoader loader = RestaurantManager.class.getClassLoader();
		InputStream in = loader.getResourceAsStream(filename);
		if (in == null) {
			System.out.println("Error reading file: " + filename);
			return command;
		}
		Scanner console = new Scanner(in);
		String[] seperate = new String[2];
		while (console.hasNextLine()) {
			String list = console.nextLine();
			if (list.startsWith("#")) {
				continue;
			}
			seperate = list.split(":");
			command.add(seperate[0]);
			describe.add(seperate[1]);
		}
		console.close();
		return command;
	}

	/* Print the command menu */
	public static void commandMenu() {
		for (int i = 0; i < command.size(); i++) {
			System.out.printf("| [%1s] - %-43s |\n", command.get(i), describe.get(i));
		}
		line();
	}

	/* Line for this method */
	private static void line() {
		System.out.println("+---------------------------------------------------+");
	}

}
