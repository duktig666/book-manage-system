package Model.table;

/**
 * 读者信息表字段 映射
 * @author rsw
 *
 */
public class Reader {
	private String number;
	private String name;
	private String gender;
	private String dept;
	private String classes;
	private String tele;
	private String email;
	private java.sql.Timestamp logindate;
	private String password;
	private String keeppass;
	private int reader_type;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getClasses() {
		return classes;
	}

	public void setClasses(String classes) {
		this.classes = classes;
	}

	public String getTele() {
		return tele;
	}

	public void setTele(String tele) {
		this.tele = tele;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public java.sql.Timestamp getLogindate() {
		return logindate;
	}

	public void setLogindate(java.sql.Timestamp logindate) {
		this.logindate = logindate;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getKeeppass() {
		return keeppass;
	}

	public void setKeeppass(String keeppass) {
		this.keeppass = keeppass;
	}

	public int getReader_type() {
		return reader_type;
	}

	public void setReader_type(int reader_type) {
		this.reader_type = reader_type;
	}

}
