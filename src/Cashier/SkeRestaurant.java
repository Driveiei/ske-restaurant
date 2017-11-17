package Cashier;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * SkeRestaurant is a main class which has ordering system that can helps
 * manager or cashier to manage the order and checkout of it
 * 
 * @author Kornphon Noiprasert
 */
public class SkeRestaurant {

	static Scanner scan = new Scanner(System.in);
	static RestaurantManager manager;
	static BankRestaurant bank;
	static HelperRestaurant help;
	static private String[] food;
	static private double[] price;
	static private String[] command;

	/*
	 * get menu(food) , price and command from RestaurantManager.java
	 */
	public static void initial() {
		manager.loadTextMenu();
		manager.loadTextCommand();
		food = manager.getMenuItems();
		price = manager.getPrices();
		command = manager.getCommands();
	}

	/*
	 * showMenu shows menu list of all menu order and command. from below
	 * methods. when user choose 's' button.
	 */
	public static void showMenu() {
		orderMenu();
		commandMenu();
		System.out.println();
	}

	/*
	 * orderMenu shows menu list of all menu order .
	 */
	public static void orderMenu() {
		System.out.println("+------------ WELCOME TO SKE RESTAURANT ------------+");
		for (int i = 0; i < food.length; i++) {
			if (i < 9) {
				System.out.printf("| [%d.] %-29s| %-7.2f Baht. |\n", i + 1, food[i], price[i]);
			} else {
				System.out.printf("| [%d] %-29s| %-7.2f Baht. |\n", i + 1, food[i], price[i]);
			}
		}
		help.lineShort();
	}

	/*
	 * commandMenu shows menu list of all command.
	 */
	public static void commandMenu() {
		for (int i = 0; i < command.length; i++) {
			System.out.printf("| %-49s |\n", command[i]);
		}
		help.lineShort();
	}

	/*
	 * cancelOrder cancels menu that customer order to initial (clear all menu).
	 * 
	 * @param quantity - quantity of all order.
	 * 
	 * @param total - total price of menus that customer must paid.
	 * 
	 * @param choice - command or menu number that user input.
	 */
	private static double cancelOrder(int[] quantity, double total, String choice) {
		total = 0;
		for (int l = 0; l < food.length; l++) {
			quantity[l] = 0;
		}
		if (choice.equalsIgnoreCase("c")) {
			System.out.println("The Order is Cancel.");
			help.twoSpace();
		}
		return total;
	}

	/*
	 * editInput modify quantity of order that customer want. and return total
	 * quantity. when user choose 'e' button.
	 * 
	 * @param choice - command or menu number that user input.
	 * 
	 * @param quan - the quantity that user want to add.
	 * 
	 * @param num - number of menu.
	 * 
	 * @param quantity - quantity of all order.
	 */
	private static int[] editInput(String choice, int quan, int num, int[] quantity) {
		num = help.changeToInt(choice);
		quantity[num - 1] = quantity[num - 1] - quan;
		editOrder(choice, quan, num, quantity);
		return quantity;
	}

	/*
	 * editOrder modify quantity of order that customer want. and print the
	 * result of quality that user edit.
	 * 
	 * @param choice - command or menu number that user input.
	 * 
	 * @param quan - the quantity that user want to add.
	 * 
	 * @param num - number of menu.
	 * 
	 * @param quantity - quantity of all order.
	 */
	private static void editOrder(String choice, int quan, int num, int[] quantity) {
		if (quantity[num - 1] <= 0) {
			quantity[num - 1] = quantity[num - 1] + quan;
			quantity[num - 1] = 0;
			System.out.printf("[%d] %s is 0 piece by now.", num, food[num - 1]);
		} else {
			System.out.printf("[%d] %s are minus %d pieces by now.", num, food[num - 1], quan);
		}
		help.twoSpace();
	}

	/*
	 * showOrder review order and Checkout show date , time, order , total ,
	 * change , and receive in the frame of box when user choose 'r' button.
	 * 
	 * @param quantity - quantity of all order.
	 * 
	 * @param choice - command or menu number that user input.
	 * 
	 * @param total - total price of menus that customer must paid.
	 * 
	 * @param money - money that customer pay when checkout.
	 * 
	 * @param change - money that cashier should change to customer.
	 * 
	 * @param time - the format time for recording.
	 * 
	 */
	private static String showOrder(int[] quantity, String choice, double total, double money, double change,
			String time) {
		help.lineLong();
		if (help.printOrNot(choice)) { // date
			time = dateAndTime(time);
			help.lineLong();
		}
		System.out.printf("|\t\tMenu\t\t |  %-10s|   %-10s|\n", "Quantity", "Prices");
		help.lineLong();
		for (int i = 0; i < food.length; i++) {
			if (quantity[i] != 0) {
				System.out.printf("| [%d] %-26s | %10d | %11.2f |\n", i + 1, food[i], quantity[i],
						quantity[i] * price[i]);
			}
		}
		help.lineLong();
		System.out.printf("| %-31s| %24.2f |\n", "Total", total);
		help.lineLong();
		if (help.printOrNot(choice)) {
			System.out.printf("| %-29s %27.2f |\n", "Receive", money);
			System.out.printf("| %-29s %27.2f |\n", "Change", change);
			help.lineLong();
		}
		help.twoSpace();
		return time;
	}

	/*
	 * addOrder add the quantity of order and shows what order user add. when
	 * user choose [number] button.
	 * 
	 * @param quan - the quantity that user want to add.
	 * 
	 * @param num - number of menu.
	 * 
	 * @param choice - command or menu number that user input.
	 * 
	 * @param quantity - quantity of all order.
	 */
	private static int[] addOrder(int quan, int num, String choice, int[] quantity) {
		quantity[num - 1] = quantity[num - 1] + quan;
		System.out.printf("[%d] %s are add %d pieces by now.", num, food[num - 1], quan);
		help.twoSpace();
		return quantity;
	}

	/*
	 * spreadSpace contains of many variables and spread method which user input
	 * to others method to add,edit,cancel,print menu,review and checkout.
	 */
	private static void spreadSpace() {
		int quan = 0;
		double money = 0;
		int num = 0;
		double change = 0;
		double total = 0.0;
		String choice = "";
		String time = "";
		String menuData = "";
		choice = help.putChoice(choice);
		int[] quantity = new int[food.length];
		while (help.checkCondition(choice)) {
			if (choice.equalsIgnoreCase("p")) {
				showMenu();
			} else if (choice.equalsIgnoreCase("c")) {
				total = cancelOrder(quantity, total, choice);
			} else if (choice.equalsIgnoreCase("r")) {
				showOrder(quantity, choice, total, money, change, time);
				money = bank.receive(money, total);
				change = bank.tableBank(money, total);
				choice = "lastReceipt";
				time = showOrder(quantity, choice, total, money, change, time);
				menuData = menuToString(menuData, quantity);
				manager.recordOrder(total, menuData, money, change, time);
				menuData = "";
				total = cancelOrder(quantity, total, choice);
			} else if (choice.equalsIgnoreCase("e")) {
				System.out.print("Enter your edit choice : ");
				choice = scan.next();
				quan = help.putQuan();
				num = help.changeToInt(choice);
				total = calculateEditTotal(total, quan, num, quantity);
				editInput(choice, quan, num, quantity);
			} else if (choice.equalsIgnoreCase("s")) {
				showOrder(quantity, choice, total, money, change, time);
			} else if (choice.equalsIgnoreCase("x")) {
				System.out.println("Good Bye");
				System.exit(0);
			} else {
				quan = help.putQuan();
				num = help.changeToInt(choice);
				addOrder(quan, num, choice, quantity);
				total = totalPricesPlus(total, quan, num);
			}
			choice = help.putChoice(choice);
		}
	}

	public static void main(String[] args) {
		initial();
		showMenu();
		spreadSpace();
	}

	/*
	 * add total price of your new menu order when user add new quantity to
	 * order.
	 * 
	 * @param total - total price of menus that customer must paid.
	 * 
	 * @param quan - the quantity that user want to add.
	 * 
	 * @param num - number of menu.
	 */
	public static double totalPricesPlus(double total, int quan, int num) {
		total = total + (quan * price[num - 1]);
		return total;
	}

	/*
	 * modify total price of your new menu order when user edit quantity order.
	 * 
	 * @param total - total price of menus that customer must paid.
	 * 
	 * @param quan - the quantity that user want to modify.
	 * 
	 * @param num - number of menu.
	 * 
	 * @param quantity - quantity of all order.
	 */
	public static double calculateEditTotal(double total, int quan, int num, int[] quantity) {
		if (quantity[num - 1] < quan) {
			total = totalPricesMinus(total, quantity[num - 1], num);
		} else {
			total = totalPricesMinus(total, quan, num);
		}
		return total;
	}

	/*
	 * minus total price of order when user edit quantity order.
	 * 
	 * @param total - total price of menus that customer must paid.
	 * 
	 * @param quan - the quantity that user want to modify.
	 * 
	 * @param num - number of menu.
	 */
	public static double totalPricesMinus(double total, int quan, int num) {
		total = total - (quan * price[num - 1]);
		return total;
	}

	/*
	 * shows date and times and return time for record.
	 * 
	 * @param timers - record date and time in one line format.
	 */
	public static String dateAndTime(String timers) {
		LocalDate date = LocalDate.now();
		LocalTime time = LocalTime.now();
		DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm");
		String timer = formatTime.format(time);
		System.out.printf("| Curent time : %10s %-32s |\n", date, timer);
		return String.format("%-10s %6s", date, timer);
	}

	/*
	 * menuToString return all order that customer checkout for record into a
	 * file.
	 * 
	 * @param menuData - all order and price that should be record.
	 * 
	 * @param quantity - quantity of all order.
	 */
	public static String menuToString(String menuData, int[] quantity) {
		for (int i = 0; i < food.length; i++) {
			if (quantity[i] != 0) {
				menuData = menuData.concat(String.format("[%d] %-24s - %d ea , %5.2f Baht.\n", i + 1, food[i],
						quantity[i], quantity[i] * price[i]));
			}
		}
		return menuData;
	}
}
