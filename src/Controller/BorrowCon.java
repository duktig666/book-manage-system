package Controller;

import java.sql.SQLException;
import java.util.Vector;

import Model.access.BorrowAccess;
import Tool.TimeTool;

/**
 * 借阅归还表的控制器
 * @author rsw
 *
 */
public class BorrowCon {
	BorrowAccess borrowdao = new BorrowAccess();

	public boolean insertBorrow(String number, int b_name, int borrowdate, int duedate,int b_id) throws SQLException {
		return borrowdao.insertBorrow(number, b_name, borrowdate, duedate,b_id);
	}

	/**
	 * 查询借阅信息
	 */
	public Object[][] queryBorrowInfo(String number1, String number2, boolean isreturn) throws SQLException {
		Object[][] borrowData = new Object[borrowdao.queryBorrowInfo(number1, number2, isreturn).size()][8];
		for (int i = 0; i < borrowData.length; i++) {
			borrowData[i][0] = borrowdao.queryBorrowInfo(number1, number2, isreturn).elementAt(i).elementAt(0);
			borrowData[i][1] = borrowdao.queryBorrowInfo(number1, number2, isreturn).elementAt(i).elementAt(1);
			borrowData[i][2] = borrowdao.queryBorrowInfo(number1, number2, isreturn).elementAt(i).elementAt(2);
			borrowData[i][3] = borrowdao.queryBorrowInfo(number1, number2, isreturn).elementAt(i).elementAt(3);
			borrowData[i][4] = borrowdao.queryBorrowInfo(number1, number2, isreturn).elementAt(i).elementAt(4);
			borrowData[i][5] = TimeTool.stampToDate(
					borrowdao.queryBorrowInfo(number1, number2, isreturn).elementAt(i).elementAt(5).toString());
			borrowData[i][6] = TimeTool.stampToDate(
					borrowdao.queryBorrowInfo(number1, number2, isreturn).elementAt(i).elementAt(6).toString());

		}
		return borrowData;
	}
	/**
	 * 获取实际归还时间
	 */
	public Object[][] queryBorrowReturnDate(String number1, String number2, boolean isreturn) throws SQLException {
		Object[][] borrowData = new Object[borrowdao.queryBorrowInfo(number1, number2, isreturn).size()][8];
		for (int i = 0; i < borrowData.length; i++) {
			borrowData[i][0] = borrowdao.queryBorrowInfo(number1, number2, isreturn).elementAt(i).elementAt(0);
			borrowData[i][1] = borrowdao.queryBorrowInfo(number1, number2, isreturn).elementAt(i).elementAt(1);
			borrowData[i][2] = borrowdao.queryBorrowInfo(number1, number2, isreturn).elementAt(i).elementAt(2);
			borrowData[i][3] = borrowdao.queryBorrowInfo(number1, number2, isreturn).elementAt(i).elementAt(3);
			borrowData[i][4] = borrowdao.queryBorrowInfo(number1, number2, isreturn).elementAt(i).elementAt(4);
			borrowData[i][5] = TimeTool.stampToDate(
					borrowdao.queryBorrowInfo(number1, number2, isreturn).elementAt(i).elementAt(5).toString());
			borrowData[i][6] = TimeTool.stampToDate(
					borrowdao.queryBorrowInfo(number1, number2, isreturn).elementAt(i).elementAt(6).toString());
			borrowData[i][7] = TimeTool.stampToDate(
					borrowdao.queryBorrowInfo(number1, number2, isreturn).elementAt(i).elementAt(7).toString());
		}
		return borrowData;
	}
	/**
	 * 图书归还
	 */
	public boolean returnBorrow(int returndate,int borrow_id,int b_id) throws SQLException {
		return borrowdao.returnBorrow(returndate,borrow_id,b_id);
	}
	/**
	 * 查询此书是否被借阅
	 * @throws SQLException 
	 */
	public boolean queryExistBook(int borrow_b_id) throws SQLException {
		return borrowdao.queryExistBook(borrow_b_id);
	}
	/**
	 * 查询此书是否被借阅
	 */
	public boolean queryIsBorrowBook(int borrow_b_id,String count)throws SQLException {
		return borrowdao.queryIsBorrowBook(borrow_b_id, count);
	}
}
