package Model.table;

/**
 * 图书类型表字段 映射
 * 
 * @author rsw
 */
public class BookType {
	public int bt_id;
	public String bt_name;

	public void setBt_id(int bt_id) {
		this.bt_id = bt_id;
	}

	public void setBt_name(String bt_name) {
		this.bt_name = bt_name;
	}

	public int getBt_id() {
		return bt_id;
	}

	public String getBt_name() {
		return bt_name;
	}
	
}
