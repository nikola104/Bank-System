import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
public class User {
	private String firstName;
	
	private String lastName;
	/**
	 * The ID number of the user.
	 */
	private String uuid;
	/**
	 * The ID number of the user`s pin number.
	 */
	private byte pinHash[];
	/**
	 * The list of accounts for this user.
	 */
	private ArrayList<Account> accounts;
	
	public User(String firstName,String lastName,String pin, Bank theBank) {
		//set user`s name
		this.firstName = firstName;
		this.lastName = lastName;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			this.pinHash = md.digest(pin.getBytes());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.out.println("error, cought NoSuchAlgorithmException");
			e.printStackTrace();
			System.exit(1);
		}
		
		//get anew unique universal ID for the user.
		this.uuid = theBank.getNewUserUUID();
		
		//create empty list of account.
		this.accounts = new ArrayList<Account>();
		// print log message.
	System.out.printf("New user %s,%s with ID %s created.\n", firstName, lastName, 
			this.uuid);
	}
	/**
	 * Add an acc 
	 * @param anAcct add acc for the user
	 */
	public void addAccount(Account anAcct) {
		this.accounts.add(anAcct);
	}
	/**
	 *  get uuid.
	 * @return uuid
	 */
	public String getUUID() {
		return uuid;
	}
	
	public boolean validatePin(String aPin) {


		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return MessageDigest.isEqual(md.digest(aPin.getBytes()), this.pinHash);
		} catch (NoSuchAlgorithmException e) {
			System.out.println("error, cought NoSuchAlgorithmException");
			e.printStackTrace();
			System.exit(1);
		}
		
		return false;
	}
	
	public String getName() {
		return firstName;
	}
	/**
	 * Print summaries for the accounts of this user
	 */
	public void printAccountsSummary() {

		System.out.printf("\n\n%s`s accounts summary\n", this.firstName);
		for (int i = 0; i < this.accounts.size(); i++) {
			System.out.printf("  %d) %s\n", i+1, this.accounts.get(i).getSummaryLine());
		}
		System.out.println();
	}
	public void printAcctTransHistory(int acctIdx) {
		this.accounts.get(acctIdx).printTransHistory();
	}
	/**
	 * Get the number of accounts of the user
	 * @return the number of accounts
	 */
	public int numAccounts() {
		return this.accounts.size();
	}
	/**
	 * Get balance of the particular account 
	 * @param acctIdx the index of account to use
	 * @return the balance of the account
	 */
	public double getAcctBalance(int acctIdx) {
		return this.accounts.get(acctIdx).getBalance();
	}
	/**
	 * Get UUID of a particular account
	 * @param acctIdx	The index of the account to use
	 * @return			The UUID of account
	 */
	public String getAcctUUID(int acctIdx) {
		return this.accounts.get(acctIdx).getUUID();
	}

	public void addAcctTransaction(int acctIdx, double amount, String memo) {
		this.accounts.get(acctIdx).addTransaction(amount, memo);
	}
}
