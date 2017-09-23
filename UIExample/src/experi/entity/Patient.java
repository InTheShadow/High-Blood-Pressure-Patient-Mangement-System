package experi.entity;

public class Patient {
	private int pat_id;
	private int doc_id;
	private String pat_name;
	private String pat_password;
	private String pat_userName;
	private boolean pat_sex;
	private String pat_address;
	private int pat_age;
	private String pat_history;
	private int pat_height;
	private int pat_weight;
	public Patient(){super();}
	public Patient(String pat_name,String pat_password,String pat_userName){
		super();
		this.pat_name = pat_name;
		this.pat_password = pat_password;
		this.pat_userName = pat_userName;
	}
	
	public void setId(int pat_id){this.pat_id = pat_id;}
	public int getId(){return pat_id;}
	
	public void setDocId(int doc_id){this.doc_id = doc_id;}
	public int getDocId(){return doc_id;}
	
	public void setName(String pat_name){this.pat_name = pat_name;}
	public String getName(){return pat_name;}
	
	public void setPassword(String pat_password){this.pat_password = pat_password;}
	public String getPassword(){return pat_password;}
	
	public void setUserName(String pat_userName){this.pat_userName = pat_userName;}
	public String getUserName(){return pat_userName;}
	
	public void setSex(boolean pat_sex){this.pat_sex = pat_sex;}
	public boolean getSex(){return pat_sex;}
	
	public void setAge(int pat_age){this.pat_age = pat_age;}
	public int getAge(){return pat_age;}
	
	public void setAddress(String pat_address){this.pat_address = pat_address;}
	public String getAddress(){return pat_address;}
	
	public void setHistory(String pat_history){this.pat_history = pat_history;}
	public String getHistory(){return pat_history;}
	
	public void setWeight(int pat_weight){this.pat_weight = pat_weight;}
	public int getWeight(){return pat_weight;}
	
	public void setHeight(int pat_height){this.pat_height = pat_height;}
	public int getHeight(){return pat_height;}
}
