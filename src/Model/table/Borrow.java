package Model.table;

/**
 * ½èÔÄ¹é»¹ĞÅÏ¢±í×Ö¶Î Ó³Éä
 * @author rsw
 *
 */
public class Borrow {
	private int borrow_id;
	private String r_number;
	private int borrow_b_id;
	private int borrowdate;
	private int duedate;
	private int returndate;
	private boolean isreturn;

	public int getBorrow_id() {
		return borrow_id;
	}

	public void setBorrow_id(int borrow_id) {
		this.borrow_id = borrow_id;
	}

	public String getR_number() {
		return r_number;
	}

	public void setR_number(String r_number) {
		this.r_number = r_number;
	}

	public int getBorrow_b_id() {
		return borrow_b_id;
	}

	public void setBorrow_b_id(int borrow_b_id) {
		this.borrow_b_id = borrow_b_id;
	}

	public int getBorrowdate() {
		return borrowdate;
	}

	public void setBorrowdate(int borrowdate) {
		this.borrowdate = borrowdate;
	}

	public int getDuedate() {
		return duedate;
	}

	public void setDuedate(int duedate) {
		this.duedate = duedate;
	}

	public int getReturndate() {
		return returndate;
	}

	public void setReturndate(int returndate) {
		this.returndate = returndate;
	}

	public boolean isIsreturn() {
		return isreturn;
	}

	public void setIsreturn(boolean isreturn) {
		this.isreturn = isreturn;
	}

}
