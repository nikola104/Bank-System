import java.util.Date;

public class Transaction {
	private double amount;
	/**
	 * time of transaction.
	 */
	private Date timestamp;
	/**
	 * a memo of this transaction.
	 */
	private String memo;
	/**
	 * The account in which the transaction was .
	 */
	private Account inAccount;
	/**
	 * Create a new Transaction
	 * @param amount the amount transacted
	 * @param inAccount the acc transaction belongs to.
	 */
	public Transaction(double amount, Account inAccount) {
		this.amount = amount;
		this.inAccount = inAccount;
		this.timestamp = new Date();
		this.memo = "";
	}
	/**
	 * Create a new transaction
	 * @param amount  the amount transacted
	 * @param memo    the memo for transaction
	 * @param inAccount the acc transaction belongs to.
	 */
	public Transaction(double amount, String memo, Account inAccount) {
		this(amount,inAccount);
		// set the memo
		this.memo = memo;
	}
	public double getAmount() {
		return this.amount;
	}
	/**
	 * get a string summarizing the transaction
	 * @return the summary string
	 */
	public String getSummaryLine() {

		if(this.amount >= 0) {
			return String.format("%s : $%.02f : %s", this.timestamp.toString(),
					this.amount, this.memo);
		}
			else {
				return String.format("%s : $(%.02f) : %s", this.timestamp.toString(),
					-this.amount, this.memo);
			}
		
	}

}
