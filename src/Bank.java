import java.util.ArrayList;
import java.util.Random;

public class Bank {
	
	private String name;
	
	private ArrayList<User> users;
	
	private ArrayList<Account> accounts;
	/**
	 * generate new id for User
	 * @return uuid
	 */
	public String getNewUserUUID() {
		int len = 6;
		String uuid;
		boolean nonUnique;
		Random rng = new Random();
		
		do {
			nonUnique = false;
			uuid = "";
			for(int i = 0; i < len; i++) {
				uuid += ((Integer)rng.nextInt(10)).toString();
			}
			
			for(User a : this.users) {
				if(uuid.compareTo(a.getUUID()) == 0) {
					nonUnique = true;
					break;
				}
			}
				
		}while(nonUnique);
		
		return uuid;
	}
	/**
	 * generate new ID for Account
	 * @return uuid
	 */
	public String getNewAccountUUID() {
		int len = 10;
		String uuid;
		boolean nonUnique;
		Random r = new Random();
		do {
			nonUnique = false;
			uuid = "";
			for(int i = 0; i < len; i++) {
				uuid += ((Integer)r.nextInt(10)).toString();
			}
			
			for (Account ac : this.accounts) {
				if(uuid.compareTo(ac.getUUID()) == 0) {
					nonUnique = true;
					break;
				}
			}
			
		}while(nonUnique);
		
	return uuid;	
	}
	/**
	 * Add an account
	 * @param anAcct  the account to add.
	 */
	public void addAccounts(Account anAcct) {
		this.accounts.add(anAcct);
	}
	/**
	 * Create new bank object with empty lists of users and accounts
	 * @param name  the name of the bank
	 */
	public Bank(String name) {
		this.name = name;
		this.users = new ArrayList<User>();
		this.accounts = new ArrayList<Account>();
	}
	
	public User addUser(String firstName, String lastName, String pin) {
		//Create a new User object and ad it to our list.
		User newUser = new User(firstName, lastName, pin,this);
		this.users.add(newUser);
		
		//Saving acc
		Account newAccount = new Account("Savings", newUser, this);
		newUser.addAccount(newAccount);
		this.addAccounts(newAccount);
		
		return newUser;
	}
	
	public User userLogin(String userID, String pin) {
		// search in list of users
		for(User a : this.users) {
			if(a.getUUID().compareTo(userID) == 0 && a.validatePin(pin)) {
				return a;
			}
		}
		//not find the person or incorrect pin
		return null;
	}
	
	public String getName() {
		return name;
	}

}
