package experi.entity;

import java.sql.Timestamp;

public class Body {
	private int pat_id;
	private int HR;
	private int HPre;
	private int LPre;
	private float Sug;
	private Timestamp record_time;
	public Body() {super();}
	
	public int getPat_id() {
		return pat_id;
	}

	public void setPat_id(int pat_id) {
		this.pat_id = pat_id;
	}
	public int getHR() {
		return HR;
	}
	public void setHR(int hR) {
		HR = hR;
	}
	public int getHPre() {
		return HPre;
	}
	public void setHPre(int hPre) {
		HPre = hPre;
	}
	public int getLPre() {
		return LPre;
	}
	public void setLPre(int lPre) {
		LPre = lPre;
	}
	public float getSug() {
		return Sug;
	}
	public void setSug(float sug) {
		Sug = sug;
	}
	public Timestamp getRecord_time() {
		return record_time;
	}
	public void setRecord_time(Timestamp record_time) {
		this.record_time = record_time;
	}
}
