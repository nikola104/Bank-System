import java.util.ArrayList;

public class Account {
	private String name;
	
	private String uuid;
	/**
	 * The user object that owns this account.
	 */
	private User holder;
	/**
	 * the list of transaction  for this account.
	 */
	private ArrayList<Transaction> transactions;
	
	public Account(String name, User holder, Bank theBank) {
		this.name = name;
		this.holder = holder;
		
		this.uuid = theBank.getNewAccountUUID();
		
		this.transactions = new ArrayList<Transaction>();
		
		//add to holder and bank lists.
		
	}
	/**
	 * get uuid.
	 * @return uuid
	 */
	public String getUUID() {
		return uuid;
	}
	/**
	 * Get summary line for the acc
	 * @return the string summary
	 */
	public String getSummaryLine() {
		//get the account`s balance
		double balance = this.getBalance();
		
		//format the summary line depending on the weather the balance is 
		//negative
		
		if(balance >= 0) {
			return String.format("%s : $%.02f : %s", this.uuid,balance, 
					this.name);
		}else {
			return String.format("%s : $(%.02f) : %s", this.uuid,balance,
					this.name);
		}
	}
	/**
	 * 
	 * @return balance of the accounts
	 */
	public double getBalance() {
		double balance = 0;
		for(Transaction t : this.transactions) {
			balance += t.getAmount();
		}
		return balance;
	}
	/**
	 * Printing the transaction history of the account
	 */
	public void printTransHistory() {
		System.out.printf("\nTransaction history for account %s\n", this.uuid);
		for(int i = this.transactions.size()-1; i >= 0; i--) {
			System.out.println(this.transactions.get(i).getSummaryLine());
		}
		System.out.println();
	}
	/**
	 * Add a new transaction in this account
	 * @param amount	The amount transacted
	 * @param memo		The transaction memo
	 */
	public void addTransaction(double amount, String memo) {
		// create a new transaction object and add to our list
		Transaction newTrans = new Transaction(amount, memo,this);
		this.transactions.add(newTrans);
	}

}
