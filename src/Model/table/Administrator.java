package Model.table;

/**
 * 管理员表的字段 映射
 * @author rsw
 */
public class Administrator {
	private int adm_id;
	private String adm_count;
	private String adm_password;
	private String adm_name;
	private String adm_id_number;
	private String adm_tele;
	private String adm_email;
	private String adm_keeppass;
	private boolean issuper;

	public int getAdm_id() {
		return adm_id;
	}

	public void setAdm_id(int adm_id) {
		this.adm_id = adm_id;
	}

	public String getAdm_count() {
		return adm_count;
	}

	public void setAdm_count(String adm_count) {
		this.adm_count = adm_count;
	}

	public String getAdm_password() {
		return adm_password;
	}

	public void setAdm_password(String adm_password) {
		this.adm_password = adm_password;
	}

	public String getAdm_name() {
		return adm_name;
	}

	public void setAdm_name(String adm_name) {
		this.adm_name = adm_name;
	}

	public String getAdm_id_number() {
		return adm_id_number;
	}

	public void setAdm_id_number(String adm_id_number) {
		this.adm_id_number = adm_id_number;
	}

	public String getAdm_tele() {
		return adm_tele;
	}

	public void setAdm_tele(String adm_tele) {
		this.adm_tele = adm_tele;
	}

	public String getAdm_email() {
		return adm_email;
	}

	public void setAdm_email(String adm_email) {
		this.adm_email = adm_email;
	}

	public String getAdm_keeppass() {
		return adm_keeppass;
	}

	public void setAdm_keeppass(String adm_keeppass) {
		this.adm_keeppass = adm_keeppass;
	}

	public boolean isIssuper() {
		return issuper;
	}

	public void setIssuper(boolean issuper) {
		this.issuper = issuper;
	}

}
