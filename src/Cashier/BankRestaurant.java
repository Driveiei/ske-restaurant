package Cashier;

import java.util.Scanner;

/**
 * BankRestaurant is a class for Cashier Change List to know how much types of
 * bank,coin that cashier should change to customer when customer pay money for
 * the order.
 * 
 * @author Kornphon Noiprasert
 */
public class BankRestaurant {
	static Scanner scan = new Scanner(System.in);
	static HelperRestaurant help;

	/*
	 * input money and check that money from customer are correctly or not.
	 * 
	 * @param money - money receive from customer.
	 * @param total - total of prices that customer must paid.
	 */
	static double receive(double money, double total) {
		System.out.print("Receive from customer : ");
		money = scan.nextDouble();
		while (money < total) {
			System.out.printf("Error. Please try again.\n");
			System.out.print("Receive from customer : ");
			money = scan.nextDouble();
		}
		System.out.println();
		return money;
	}

	/*
	 * contains the list of banks and coins that cashier should change to
	 * customer. and return change(total) money to customer.
	 * 
	 * @param money - money receive from customer.
	 */
	static double moneyBank(double money) {
		double[] typeBanks = { 1000, 500, 100, 50, 20 };
		double[] typeCoins = { 10, 5, 2, 1 };
		double[] typeSatangs = { 0.5, 0.25 };
		System.out.printf("| %-23s |%16s \t    |\n", "Change bank list", "Amount");
		help.lineShort();
		double totalBank = 0;
		for (double banks : typeBanks) {
			if (money / banks >= 1) {
				totalBank = threeTypesCurrency(money, totalBank, "notes", banks);
				money = modMoney(money, banks);
			}
		}
		for (double coins : typeCoins) {
			if (money / coins >= 1) {
				totalBank = threeTypesCurrency(money, totalBank, "coins", coins);
				money = modMoney(money, coins);
			}
		}
		for (double types : typeSatangs) {
			if (money / types >= 1) {
				System.out.printf("| %-4.2f %-18s |    %17.0f ea |\n", types, "satang-coins",
						Math.floor(money / types));
				totalBank = totalBank + types * Math.floor(money / types);
				money = modMoney(money, types);
			}
		}
		return totalBank;
	}

	/*
	 * calculate and print list of banks and coins to cashier. and return
	 * totalBank to moneyBank.
	 * 
	 * @param types - types of money.
	 * @param word - contains phrase"notes , banks and satang - coins"
	 * @param money - money receive from customer.
	 * @param totalBank - total of prices that customer must paid.
	 */
	static double threeTypesCurrency(double money, double totalBank, String words, double types) {
		System.out.printf("| %4.0f %-18s |    %17.0f ea |\n", types, words, Math.floor(money / types));
		totalBank = totalBank + types * Math.floor(money / types);
		return totalBank;
	}

	/*
	 * calculate money by subtract it if cashier change this types of currency
	 * to customer and return money back.
	 * 
	 * @param money - money receive from customer.
	 * @param types - types of money.
	 */
	public static double modMoney(double money, double types) {
		money = money % types;
		return money;
	}

	/*
	 * print the table Cashier change list shows receives and types of money
	 * that cashier should change to customer.
	 * 
	 * @param money - money receive from customer.
	 * @param total - total of prices that customer must paid.
	 */
	static double tableBank(double money, double total) {
		help.lineShort();
		System.out.printf("| %-49s |\n", "Cashier Change List");
		help.lineShort();
		System.out.printf("| %-23s |  %16.2f Baht. |\n", "Receive", money);
		help.lineShort();
		total = moneyBank(money - total);
		help.lineShort();
		return total;
	}
}
