package experi.entity;

import java.sql.Timestamp;

public class Advice {
	private int advice_id;
	private int doc_id;
	private String doc_name;
	private int pat_id;
	private String advice_value;
	private Timestamp advice_recordTime;
	public Advice(){super();}
	public int getAdvice_id() {
		return advice_id;
	}
	public void setAdvice_id(int advice_id) {
		this.advice_id = advice_id;
	}
	public int getDoc_id() {
		return doc_id;
	}
	public void setDoc_id(int doc_id) {
		this.doc_id = doc_id;
	}
	public int getPat_id() {
		return pat_id;
	}
	public void setPat_id(int pat_id) {
		this.pat_id = pat_id;
	}
	public String getAdvice_value() {
		return advice_value;
	}
	public void setAdvice_value(String advice_value) {
		this.advice_value = advice_value;
	}
	public Timestamp getAdvice_recordTime() {
		return advice_recordTime;
	}
	public void setAdvice_recordTime(Timestamp advice_recordTime) {
		this.advice_recordTime = advice_recordTime;
	}
	public String getDoc_name() {
		return doc_name;
	}
	public void setDoc_name(String doc_name) {
		this.doc_name = doc_name;
	}
	

}
