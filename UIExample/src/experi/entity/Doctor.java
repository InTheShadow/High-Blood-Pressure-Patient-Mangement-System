package experi.entity;

public class Doctor {
	private int doc_id;
	private String doc_name = "";
	private String doc_password = "";
	private String doc_userName = "";
	private boolean doc_sex = true;
	private String doc_hospital = "";
	private int doc_age = 0;
	public Doctor(){super();}
	public Doctor(String doc_name,String doc_password,String doc_userName){
		super();
		this.doc_name = doc_name;
		this.doc_password = doc_password;
		this.doc_userName = doc_userName;
	}
	public void setId(int doc_id){this.doc_id = doc_id;}
	public int getId(){return doc_id;}
	
	public void setName(String doc_name){this.doc_name = doc_name;}
	public String getName(){return doc_name;}
	
	public void setPassword(String doc_password){this.doc_password = doc_password;}
	public String getPassword(){return doc_password;}
	
	public void setUserName(String doc_userName){this.doc_userName = doc_userName;}
	public String getUserName() { return doc_userName;}
	
	public void setSex(boolean doc_sex) {this.doc_sex = doc_sex;}
	public boolean getSex(){return doc_sex;}
	
	public void setHospital(String doc_hospital) {this.doc_hospital = doc_hospital;}
	public String getHospital(){return doc_hospital;}
	
	public void setAge(int doc_age){this.doc_age = doc_age;}
	public int getAge(){return doc_age;}
}
