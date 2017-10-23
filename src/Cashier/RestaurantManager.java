package Cashier;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The basic calculate program for restaurant manager.
 * 
 * @author Kornphon Noiprasert
 */
public class RestaurantManager {

	static Scanner scan = new Scanner(System.in);
	static ArrayList<String> names = new ArrayList<>();
	static ArrayList<Double> prices = new ArrayList<>();
	static ArrayList<String> check = new ArrayList<>();
	static ArrayList<String> command = new ArrayList<>();
	static double total = 0;
	
	/*The box to make a frame*/
	private static void line(){
		System.out.println("+--------------------------------+------------+-------------+");
	}
	
	/*Auto two line spaces*/
	private static void twoSpace(){
		System.out.println();
		System.out.println();
	}
	
	/*load the text from menu.txt and insert it in names and prices*/
	private static void loadText(){
		String filename = "Data/menu.txt";
		ClassLoader loader = RestaurantManager.class.getClassLoader();
		InputStream in = loader.getResourceAsStream(filename);
		if(in == null){
			System.out.println("Error reading file: "+ filename);
			return;
		}
		Scanner console = new Scanner(in);
		String[] seperate = new String[2];
		while(console.hasNextLine()){
			String list = console.nextLine();
			if(list.startsWith("#")){
				continue;
			} 
			seperate = list.split(":");
			names.add(seperate[0]);
			prices.add(Double.parseDouble(seperate[1]));
		}
		console.close();
	}
	
	/*put the condition from menu.txt and command.txt*/
	private static void solCondition(){//use one time (for check condition)
		String addCondition; 
		loadText();
		for(int i = 0; i<names.size() ; i++){
			addCondition = Integer.toString(i+1); 
			check.add(addCondition);
		}
		command = SkeRestaurant.loadTextTwo();
		for(int j = 0; j < command.size(); j++){
			check.add(command.get(j));
		}
	}
	
	/*Show Menu order when you press [p] button*/
	private static void showMenu(){
		SkeRestaurant.orderMenu();
		SkeRestaurant.commandMenu();
		System.out.println();
	}
	
	/*Input choice*/
	private static String putChoice(String choice){
		System.out.print("Enter your choice : ");
		choice = scan.next();
		choice = errorInput(choice);
		return choice;
	}
	
	/*Check the choice that you input*/
	private static String errorInput(String choice){
		while(check.indexOf(choice) == -1){
			System.out.println("Error input. Please try again.");
			choice = putChoice(choice);
		} 
		return choice;
	}
	
	/*add total price of your new menu order */
	private static double totalPricesPlus(double total, int quan,String choice){
			total = total + quan*prices.get(Integer.parseInt(choice)-1);
		return total;
	}
	
	/*minus total price of order that you edit*/
	private static double totalPricesMinus(double total, int quan,String choice){
		total = total - quan*prices.get(Integer.parseInt(choice)-1);
	return total;
	}
	
	/*Input quantity*/
	private static int putQuan(){
		System.out.print("Enter your quantity : ");
		int quan = scan.nextInt();
		return quan;
	}
	
	/*Change words to number*/
	private static int changeToInt(String word){
		int num = Integer.parseInt(word);
		return num; 
	}
	
	/*Show all order and total that you already order it
	 * when you press [s] button*/
	private static void showOrder(int[] quantity){
		line();
		System.out.printf("|\t\tMenu\t\t |  %8s  |   %6s    |\n","Quantity","Prices");
		line();
		for(int i = 0;i<names.size();i++){
			if(quantity[i] != 0){
			System.out.printf("| [%d] %-26s | %10d | %11.2f |\n",i+1,names.get(i),quantity[i],quantity[i]*prices.get(i));
			}
		}
		line();
		System.out.printf("| %-31s| %24.2f |\n","Total",total);
		line();
		twoSpace();
	}
	
	/*Cancel all order when you press [x] button*/
	private static void cancelOrder(int[] quantity){
		total = 0;
		for(int l = 0; l<names.size(); l++){
			quantity[l] = 0;
		}
		System.out.println("The Order are Cancel.");
		twoSpace();
	}
	
	/*Edit order that you want when you press [e] button
	 *It will minus from your orders that you choose it*/
	private static int[] editOrder(String choice,int quan,int num,int[] quantity){
		System.out.print("Enter your edit choice : ");
		choice = scan.next();
		quan = putQuan();
		num = changeToInt(choice);
		quantity[num-1] = quantity[num-1] - quan;
		if(quantity[num-1] <= 0){
			quantity[num-1] = quantity[num-1] + quan;
			total = totalPricesMinus(total,quantity[num-1],choice);
			quantity[num-1] = 0;
			System.out.printf("[%d] %s is 0 piece by now.",num,names.get(num-1));
		} else {
			System.out.printf("[%d] %s are minus %d pieces by now.",num,names.get(num-1),quan);
			total = totalPricesMinus(total,quan,choice);
		}	
		twoSpace();
		return quantity;
	}
	
	/*add the order when your choice is [number] button*/
	private static int[] addOrder(int quan,int num,String choice,int[] quantity){
		quan = putQuan();
		num = changeToInt(choice);
		quantity[num-1] = quantity[num-1] + quan;
		System.out.printf("[%d] %s are add %d pieces by now.",num,names.get(num-1),quan);
		total = totalPricesPlus(total,quan,choice);
		twoSpace();
		return quantity;
	}
	
	/*This method spread the command what you want*/
	private static void spreadSpace(){
		int quan = 0;
		int num = 0;
		String choice = "";
		int[] quantity = new int[names.size()];
		choice = putChoice(choice);
		while(!choice.equalsIgnoreCase("c")){
			if(choice.equalsIgnoreCase("p")){
				showMenu();
			} else if (choice.equalsIgnoreCase("x")){
				cancelOrder(quantity);
			} else if (choice.equalsIgnoreCase("c")){
				break;
			} else if (choice.equalsIgnoreCase("e")){
				editOrder(choice,quan,num,quantity);				
			} else if(choice.equalsIgnoreCase("s")){
				showOrder(quantity);				
			} else {
				addOrder(quan,num,choice,quantity);				
			}			
			choice = putChoice(choice);
		}
		showOrder(quantity); // Show total when press [c] button
	}
	
	public static void main(String[] args)  {
		solCondition();
		showMenu();
		twoSpace();
		spreadSpace();
		System.out.println("Thank you");
	}

		
	
	
	

}
