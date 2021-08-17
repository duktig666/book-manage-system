package Model.table;

/**
 * 读者类型表字段 映射
 * @author rsw
 */
public class ReaderType {
	private int rt_id;
	private String rt_name;
	private int maxcount;
	private int maxday;

	public void setRt_id(int rt_id) {
		this.rt_id = rt_id;
	}

	public void setRt_name(String rt_name) {
		this.rt_name = rt_name;
	}

	public void setMaxcount(int maxcount) {
		this.maxcount = maxcount;
	}

	public void setMaxday(int maxday) {
		this.maxday = maxday;
	}

	public int getRt_id() {
		return rt_id;
	}

	public String getRt_name() {
		return rt_name;
	}

	public int getMaxcount() {
		return maxcount;
	}

	public int getMaxday() {
		return maxday;
	}
}
