import java.util.Scanner;

public class ATM {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		//init bank
		Bank theBank = new Bank("Fibank");
		// add a user , which also creates a savings account
		User aUser = theBank.addUser("Ivancho", "Ivanov", "1234");
		//add a checking acc for our user
		Account newAccount = new Account("Checking", aUser,theBank);
		aUser.addAccount(newAccount);
		theBank.addAccounts(newAccount);
		
		User curUser;
		while(true) {
			//stay in prompt until login
			curUser = ATM.mainMenuPrompt(theBank,in);
			
			//stay in main menu until user quits
			ATM.printUserMenu(curUser,in);
		}	
		
	}
	public static User mainMenuPrompt(Bank theBank, Scanner in) {
		
		//inits
		String userID;
		String pin;
		User authUser;	
		//prompt the user for user ID/pin combo until a correct one is reached
		do {		
			System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
			System.out.print("Enter user ID: ");
			userID = in.nextLine();
			System.out.print("Enter a pin: ");
			pin = in.nextLine();
			
			// try to get the user object corresponding to the ID and pin combo
			
			authUser = theBank.userLogin(userID, pin);
			if(authUser == null) {
				System.out.println("Try again invalid ID/pin");
			}
		}while(authUser == null);//looping until success
		
		return authUser;
	}


	public static void printUserMenu(User theUser, Scanner in) {
		//print a summary of the user`s acc
		theUser.printAccountsSummary();
		
		//init
		int choice;
		//user menu
		do {
			System.out.printf("Welcome %s,what u like to do?\n", theUser.getName());
			System.out.println(" 1) Show account atransaction history");
			System.out.println(" 2) Withdrawl");
			System.out.println(" 3) Deposit");
			System.out.println(" 4) Transfer");
			System.out.println(" 5) Quit");
			System.out.print("Enter the choice: ");
			choice = in.nextInt();
			
			if(choice < 1 || choice > 5) {
				System.out.println("Invalid choice. Pls choose 1-5");
			}
			
		}while(choice < 1 || choice > 5);
		
		//Process the choice
		switch(choice) {
		case 1:
			ATM.showTransHistory(theUser,in);
			break;
		case 2:
			ATM.withdrawlFunds(theUser,in);
			break;
		case 3:
			ATM.depositFunds(theUser,in);
			break;
		case 4: 
			ATM.transferFunds(theUser,in);
			break;
		case 5:
			//gobble up rest of previous input 
			in.nextLine();
			break;
			
		}
		
		//redisplay this menu unless the user wants to quit
		if(choice != 5) {
			ATM.printUserMenu(theUser, in);
		}
	}

	public static void showTransHistory(User theUser, Scanner in) {


		int theAcct;
		//get acc whose transaction history to look at
		do {
			System.out.printf("Enter the number(1-%d) of the account"
		+ "whose transactions you want to see", theUser.numAccounts());
			theAcct = in.nextInt() - 1;
			if(theAcct < 0 || theAcct >= theUser.numAccounts()) {
				System.out.println("Inavlid account. Please try again. ");
			}
		}while(theAcct < 0 || theAcct >= theUser.numAccounts());
		//print the transaction history
		theUser.printAcctTransHistory(theAcct);
	}
	/**
	 * Process transferring funds from one account to another
	 * @param theUser	the logged-in User object
	 * @param in		the Scanner objected used for user input
	 */
	public static void transferFunds(User theUser, Scanner in) {



		//init
		int fromAcct;
		int toAcct;
		double amount;
		double acctBal;
		//get the account to trnsfer from
		do {
			System.out.printf("Enter the number (1 - %d) of the acccount\n"
					+ "to transfer from.",theUser.numAccounts());
			fromAcct = in.nextInt() - 1;
				if(fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
					System.out.println("Inavlid account. Please try again. ");
				}
		}while(fromAcct < 0 || fromAcct >= theUser.numAccounts());
		
		acctBal = theUser.getAcctBalance(fromAcct);
		
		//get to account to transfer to
		
		do {
			System.out.printf("Enter the number (1 - %d) of the acccount\n"
					+ "to transfer from: ", theUser.numAccounts());
			toAcct = in.nextInt() - 1;
				if(toAcct < 0 || toAcct >= theUser.numAccounts()) {
					System.out.println("Inavlid account. Please try again. ");
				}
		}while(toAcct < 0 || toAcct >= theUser.numAccounts());
		
		//get the amount to transfer
		
		do {
			System.out.printf("Enter the amount to transfer (max $%.02f): $", acctBal);
			amount = in.nextDouble();
			if(amount < 0) {
				System.out.println("Amount must be greater than zero");
			}else if(amount > acctBal) {
				System.out.printf("Amount must not be greater than\n"
						+ "balance of $%.02f.\n", acctBal);
			}
		}while(amount < 0 || amount > acctBal);
		
		// Finally do the transfer
		theUser.addAcctTransaction(fromAcct, -1*amount, String.format(
				"Transfer to account %s", theUser.getAcctUUID(toAcct)));
		theUser.addAcctTransaction(toAcct, amount, String.format(
				"Transfer to account %s", theUser.getAcctUUID(toAcct)));
	}
	/**
	 * Process a fund withdraw from an account
	 * @param theUser	The logged user
	 * @param in		The scanner object 
	 */
	public static void withdrawlFunds(User theUser, Scanner in) {



		//init
				int fromAcct;
				double amount;
				double acctBal;
				String memo;
				//get the account to trnsfer from
				do {
					System.out.printf("Enter the number (1 - %d) of the acccount\n"
							+ "to withdraw from: ",theUser.numAccounts());
					fromAcct = in.nextInt() - 1;
						if(fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
							System.out.println("Inavlid account. Please try again. ");
						}
				}while(fromAcct < 0 || fromAcct >= theUser.numAccounts());
				acctBal = theUser.getAcctBalance(fromAcct);
				
				do {
					System.out.printf("Enter the amount to withdraw (max $%.02f): $", acctBal);
					amount = in.nextDouble();
					if(amount < 0) {
						System.out.println("Amount must be greater than zero");
					}else if(amount > acctBal) {
						System.out.printf("Amount must not be greater than\n"
								+ "balance of $%.02f.\n", acctBal);
					}
				}while(amount < 0 || amount > acctBal);
				
				//gobble up rest of previous input 
				in.nextLine();
				
				//get a memo
				
				System.out.println("Enter a memo");
				memo = in.nextLine();
				
				//do the withdraw
				theUser.addAcctTransaction(fromAcct,-1*amount,memo);
	}
	/**
	 * Process a fund deposit to an account
	 * @param theUser 	The logged user
	 * @param in		The scanner object 
	 */
	public static void depositFunds(User theUser, Scanner in) {
		//init
		int toAcct;
		double amount;
		double acctBal;
		String memo;
		//get the account to trnsfer from
		do {
			System.out.printf("Enter the number (1 - %d) of the acccount\n"
					+ "to deposit in: ", theUser.numAccounts());
			toAcct = in.nextInt() - 1;
				if(toAcct < 0 || toAcct >= theUser.numAccounts()) {
					System.out.println("Inavlid account. Please try again. ");
				}
		}while(toAcct < 0 || toAcct >= theUser.numAccounts());
		acctBal = theUser.getAcctBalance(toAcct);
		
		do {
			System.out.printf("Enter the amount to transfer (max $%.02f): $", acctBal);
			amount = in.nextDouble();
			if(amount < 0) {
				System.out.println("Amount must be greater than zero");
			}
		}while(amount < 0);
		
		//gobble up rest of previous input 
		in.nextLine();
		
		//get a memo
		
		System.out.println("Enter a memo");
		memo = in.nextLine();
		
		//do the withdraw
		theUser.addAcctTransaction(toAcct,amount,memo);
		
	}
}
