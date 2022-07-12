import java.util.Scanner;

/**
 * The <code>CashRegister</code> class contains all functionality of the
 * purchasing and modifying of items that are being sold.
 * 
 * @author Jordan Anodjo
 */
public class CashRegister {
	/**
	 * Starts a session in where the user must input prompted values to purchase
	 * items.
	 * After the user will be able to edit the stock items were bought from.
	 */
	public static void promptStartShopping() {

		// Greet the user.
		System.out.println("Welcome to POST system!\n");

		// Declaration of Scanner
		Scanner input = new Scanner(System.in);

		// Fill up the stock of the store with a file
		Stock stock = promptSelectFile(input);

		// Variable used to record all transactions;
		float grandTotal = 0;

		// The main session loop to continuously prompt the user.
		session: while (true) {

			// Loop the selection to start shopping or end program now.
			if (!promptStartShopping(input)) {
				System.out.printf("The total sale for the day is%3s%7.2f\n", "$", grandTotal);
				break session;
			}

			// Create the user's shopping cart
			Receptacle shoppingCart = new Receptacle();

			// Continuously prompt the user to select which items and
			// they want to purchase and how many of said items.
			shopping: while (true) {

				System.out.print("Enter product code: ");
				String userInput = input.nextLine(); // Record user's input for later use.

				// Check if the user wants to use other features.
				if (userInput.equals("-1")) {
					// Negative 1 is the key to continue to the next phase of the program.
					// Prompt the user with what they will be paying.
					grandTotal += promtEndTransaction(shoppingCart, input);
					break shopping;
				} else if (userInput.equals("0000")) {
					System.out.println("\n" + stock + "\n");
					continue shopping;
				}

				// At this point check if code given is valid.
				Code userCode;

				// Catches the error of user input not matching format.
				try {
					userCode = new Code(userInput);
				} catch (IllegalArgumentException e) {
					System.out.println("!!! Illegal data type\n");
					continue shopping;
				}

				Item item;

				// Catches the error of Item not being in the Receptacle
				try {
					item = stock.copy(userCode);
				} catch (IllegalArgumentException e) {
					System.out.println("!!! Invalid product code\n");
					continue shopping;
				}

				// If the item doesnt already exist
				// Then add it into the shopping cart
				if (!shoppingCart.exist(item))
					shoppingCart.add(item);
				else
					// If the item already exists in the shopping cart
					// reference that item so user can increase amount they have.
					item = shoppingCart.getItem(userCode);

				System.out.printf("%20s%s\n", "Item name: ", item.getName());

				// Prompt the amount of items the user wants to purchase.
				promptQuantity(item, shoppingCart, input);

			}
		}

		// Final prompt to update stock.
		promptUpdateStock(input, stock);

		// Safely exit program.
		System.exit(0);
	}

	/**
	 * Description: Starts a session in where the user must select an apporiate
	 * response. In this session the user submit a path to a file. If the file is
	 * not valid, continuously prompt the user for a valid file, if the user types Q
	 * then end the program.
	 * 
	 * @param input The users input.
	 * @return the stock filled with values from a file.
	 */
	public static Stock promptSelectFile(Scanner input) {
		// Try to open user file.
		System.out.print("Input File (Q to quit): ");
		while (true) {

			// Record user input.
			String userInput = input.nextLine();

			// If user wants to quit submit Q, safely exit program.
			if (userInput.toLowerCase().equals("Q"))
				System.exit(0);

			userInput = "../" + userInput;

			// Catches if the user submits a file that does not exist.
			// Catches if the user submits a file that is not formatted correctly.
			try {
				return new Stock(userInput);
			} catch (java.io.FileNotFoundException e) {
				System.out.printf("!!! File at %s is not found try again: ", userInput);
			} catch (IllegalArgumentException e) {
				System.out.printf("!!! File at %s is not valid try again: ", userInput);
			}

		}
	}

	/**
	 * Description: Starts a session in where the user must select an apporiate
	 * response. In this session the user must select Y (case insenstive) or N (case
	 * insenstive). If the user sends in an invalid response, the user will be
	 * prompted to select an apporiate response until they have selected such.
	 * 
	 * @param input The users input.
	 * @return boolean value if the user selects Y (Yes) or N (no)
	 */
	public static boolean promptStartShopping(Scanner input) {

		// Using an infinite loop to keep the user in the session.
		while (true) {
			System.out.print("Beginning a new sale (Y/N) "); // Prompt user to select (Y/N) Prompt A

			switch (input.nextLine().toUpperCase()) {
				case "Y":
					System.out.println("--------------------");
					return true;
				case "N":
					System.out.println("\n====================");
					return false;
				default:
					// Default: invalid input, prompt user to start again.
					System.out.println("!!! Invalid input");
					break;
			}
		}
	}

	/**
	 * Description: Starts a session in where the user must select an apporiate
	 * response. In this session the user must pay out what they have in the
	 * shopping cart.
	 * 
	 * @param input        The user input.
	 * @param shoppingCart The Receptacle that holds items that is being purchased.
	 * @return subTotal full cost of the current session.
	 */
	public static float promtEndTransaction(Receptacle shoppingCart, Scanner input) {

		// Sort list from longest name to sortest name.
		shoppingCart.sort();

		System.out.println("----------------------------");
		System.out.println("Item list:");

		// Print each Item in the shopping cart
		System.out.println(shoppingCart);

		// Variable used to store the tax.
		double tax = 0.06;

		// Variable used to store the amount of money needed for this transaction.
		double[] bills = shoppingCart.getTotalCost(tax);

		System.out.printf("Subtotal %12s %6.2f\n", "$", bills[0]);
		System.out.printf("Total with Tax (%.0f%%) %s %6.2f\n", tax * 100, "$", bills[1]);

		// Session used to keep user in until an appropriate transaction is made.
		session: while (true) {

			// Format the string the user will be inputting on.
			if (bills[1] > 9.99)
				System.out.printf("Tendered amount%6s%2s", "$", " ");
			else
				System.out.printf("Tendered amount%6s%3s", "$", " ");

			double balance;
			// Catch if the user's input is invalid or not a double.
			try {
				balance = input.nextDouble(); // Store the user's input.
			} catch (Exception e) {
				System.out.println("!!! Invalid input\n");
				continue session;
			}

			// Clear line.
			input.nextLine();

			double change;
			// Catch if the user's input is not greater than the bill.
			try {
				change = purchase(balance, bills[1]);
			} catch (Exception e) {
				System.out.println("!!! Not enough. Enter again.");
				continue session;
			}

			System.out.printf("Change%15s%7.2f\n", "$", change);

			break session;
		}

		System.out.println("----------------------------\n");

		return (float) bills[0];
	}

	/**
	 * Description: Function used to throw an error if the user inputs insufficient
	 * funds.
	 * 
	 * @param balance the amount the user submitted.
	 * @param bill    the cost of all items + tax.
	 * @return returns the change the user will recieve.
	 */
	public static double purchase(double balance, double bill) {
		if (balance < bill)
			throw new IllegalArgumentException("Insufficient funds");
		return balance - bill;
	}

	/**
	 * Description: Starts a session in where the user must select an apporiate
	 * response. In this session the user must select a number greater than 0 to add
	 * to the amount of the item they have in their shopping cart.
	 * 
	 * @param item         The Item that will have its quanity amount raised.
	 * @param shoppingCart The Receptacle the item is in.
	 * @param input        The users input.
	 */
	public static void promptQuantity(Item item, Receptacle shoppingCart, Scanner input) {

		// Looping session.
		session: while (true) {

			// Prompt user.
			System.out.printf("%-20s", "Enter quantity: ");

			int amount;

			// Catches if the user input is not an Integer.
			try {
				// Store the number.
				amount = input.nextInt();
			} catch (java.util.InputMismatchException e) {
				System.out.println("!!! Invalid data type\n");
				// Clear line.
				input.nextLine();
				continue session;
			}

			// Clear line.
			input.nextLine();

			// Catches if the user input a number less than or equal to 0.
			try {
				// Increase the amount of this Item.
				item.addAmount(amount);
			} catch (IllegalArgumentException e) {
				// 0 or negative number
				System.out.println("!!! Invalid quantity\n");
				continue session;
			}

			System.out.printf("%22s%6.2f\n\n", "Item total: $ ", item.getPrice() * amount);
			break;

		}
	}

	/**
	 * Description: Starts a session in where the user must select an apporiate
	 * response. In this session the user must fill out the forms correctly to
	 * create a new <code>Item</code> in the <code>Stock</code>.
	 * 
	 * @param input the user input variable.
	 * @param stock the <code>Stock</code> that a new <code>Item</code> will be
	 *              added to.
	 */
	public static void promptAddItem(Scanner input, Stock stock) {
		Code code;
		String name;
		float price;

		// Catches if the user inputs a invalid field.
		session: while (true) {
			System.out.print("item code: ");
			try {
				code = new Code(input.nextLine());
				break session;
			} catch (IllegalArgumentException e) {
				System.out.println("!!! Invalid code format");

			}
		}

		// Name can be anything.
		System.out.print("item name: ");
		name = input.nextLine().toLowerCase();

		// Catches if the user inputs a invalid price.
		session: while (true) {
			System.out.print("item price: ");

			try {
				price = input.nextFloat();
				break session;
			} catch (Exception e) {
				// Clear line.
				input.nextLine();
				System.out.println("!!! Invalid price");
			}
		}

		// Clear line.
		input.nextLine();

		System.out.printf("\nAre you sure you want to add %s (Y/N) ", name);
		switch (input.nextLine().toUpperCase()) {
			case "Y":
				stock.add(new Item(code, name, price, 0));
				System.out.println("Item add successful!");
				break;

			default:
				System.out.println("Did not add item.");
				break;
		}
	}

	/**
	 * Description: Starts a session in where the user must select an apporiate
	 * response. In this session the user must submit a valid <code>Code</code> to
	 * delete it.
	 * an <code>Item</code> in the <code>Stock</code>.
	 * 
	 * @param input the user input variable.
	 * @param stock the <code>Stock</code> that a new <code>Item</code> will be
	 *              added to.
	 */
	public static void promptDeleteItem(Scanner input, Stock stock) {
		Item item;
		Code code;

		// Catches if the user inputs a invalid field.
		try {
			System.out.print("item code: ");
			code = new Code(input.nextLine());
		} catch (Exception e) {
			System.out.println("!!! Invalid code format");
			return;
		}

		// Catches if the item exists or not.
		try {
			item = stock.getItem(code);
		} catch (Exception e) {
			System.out.println("!!! Item at code does not exist");
			return;
		}

		System.out.printf("Are you sure you want to delete %s (Y/N) ", item.getName());
		switch (input.nextLine().toUpperCase()) {
			case "Y":
				stock.remove(code);
				System.out.println("Item delete successful!");
				break;

			default:
				System.out.println("Did not delete item!");
				break;
		}

	}

	/**
	 * Description: Starts a session in where the user must select an apporiate
	 * response. In this session the user must submit a valid <code>Code</code> to
	 * modify it.
	 * an <code>Item</code> in the <code>Stock</code>.
	 * 
	 * @param input the user input variable.
	 * @param stock the <code>Stock</code> that a new <code>Item</code> will be
	 *              added to.
	 */
	public static void promptModifyItem(Scanner input, Stock stock) {
		Item item;
		Code code;
		String name;
		float price;

		// Catches if the user inputs a invalid code.
		session: while (true) {
			System.out.print("item code: ");
			try {
				code = new Code(input.nextLine());
				break session;
			} catch (Exception e) {
				System.out.println("!!! Invalid code format");
				return;
			}
		}

		// Catches if the item exists.
		try {
			item = stock.getItem(code);
		} catch (Exception e) {
			System.out.println("!!! Item at code does not exist");
			return;
		}

		// Name can be anything.
		System.out.print("item name: ");
		name = input.nextLine().toLowerCase();

		// Catches if the user inputs a invalid price.
		session: while (true) {
			System.out.print("item price: ");
			try {
				price = input.nextFloat();

				break session;
			} catch (Exception e) {
				// Clear line.
				input.nextLine();
				System.out.println("!!! Invalid price");

			}
		}

		// Clear line.
		input.nextLine();

		System.out.printf("Are you sure you want to modify %s (Y/N) ", item.getName());
		switch (input.nextLine().toUpperCase()) {
			case "Y":
				item = new Item(code, name, price, 0);
				// Since it's known that item exist no error handling is need.
				stock.replaceItem(item, code);
				System.out.println("Item modify successful!");
				break;

			default:
				System.out.println("Did not modify item!");
				break;
		}

	}

	/**
	 * Description: Starts a session in where the user must select an apporiate
	 * response. In this session the user must select prompted input to update the
	 * <code>Stock</code>
	 * 
	 * @param item         The Item that will have its quanity amount raised.
	 * @param shoppingCart The Receptacle the item is in.
	 * @param input        The users input.
	 */
	public static void promptUpdateStock(Scanner input, Stock stock) {
		session: while (true) {
			System.out.print("\nDo you want to update the items data? (A/D/M/Q): ");
			switch (input.nextLine().toUpperCase()) {
				case "A":
					promptAddItem(input, stock);
					break;

				case "D":
					promptDeleteItem(input, stock);
					break;

				case "M":
					promptModifyItem(input, stock);
					break;

				case "Q":
					// Exit program.
					input.close();
					System.out.println(stock);
					stock.updateFile();
					System.out.println("\nThanks for using POST system. Goodbye.");
					break session;

				default:
					System.out.println("!!! Invalid input");
					break;
			}
		}

	}
}
