package experi.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;

import experi.entity.*;

public class DoctorDao extends BaseDao {
	/**
	 将医生插入数据库
	 */
	public void insertDoctor(Doctor doctor){
		getJdbcTemplate();
		try{
			String sql = "insert into dbo.doctor values(?,?,?,?,?,?)";
			jdbcTemplate.update(sql,new Object[] { 
				doctor.getName(),doctor.getPassword(),
				doctor.getUserName(),doctor.getSex(),doctor.getHospital(),
				doctor.getAge()}
			);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 删除
	 */	
	public void deleteDoctor(Doctor doctor){
		getJdbcTemplate();
		
		try{
			String sql = "delete from dbo.doctor where doc_userName=?";
			jdbcTemplate.update(sql,new Object[] {doctor.getUserName()});			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/*
	 获得负责患者用户名（通过ID)
	 */
	public Patient[] findPatients(int doc_id){
		getJdbcTemplate();
		try{

			String sql = "select pat_userName,pat_name from dbo.Patient where doc_id=?";
			List query = jdbcTemplate.query(sql, new Object[]{doc_id}, new RowMapper() {
				@Override
				public Object mapRow(ResultSet set, int rownum)
						throws SQLException {
					Patient patient = new Patient();
					patient.setName(set.getString("pat_name"));
					patient.setUserName(set.getString("pat_userName"));
					return patient;
				}
			});
			if(query == null) return null;
			Patient result[] = new Patient[query.size()];
			for(int i = 0;i<query.size();i++) result[i] = (Patient)query.get(i); 
			return result;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 查找固定医生（通过编号）
	 */	
	public Doctor findById(int doc_id){
		getJdbcTemplate();
		try{
			String sql = "select * from dbo.doctor where doc_id=?";
			List query = jdbcTemplate.query(sql, new Object[]{doc_id}, new RowMapper() {
				@Override
				public Object mapRow(ResultSet set, int rownum)
						throws SQLException {
					Doctor doctor=new Doctor();
					doctor.setId(set.getInt("doc_id"));
					doctor.setName(set.getString("doc_name"));
					doctor.setPassword(set.getString("doc_password"));
					doctor.setUserName(set.getString("doc_userName"));
					doctor.setSex(set.getBoolean("doc_sex"));
					doctor.setHospital(set.getString("doc_hospital"));
					doctor.setAge(set.getInt("doc_age"));
					return doctor;
				}
			});
			if(query == null||query.size() == 0) return null;
			return (Doctor) query.get(0);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	/**
	 查找固定医生（通过用户名）
	 */	
	public Doctor findByUserName(String doc_userName){
		getJdbcTemplate();
		try{
			String sql = "select * from dbo.doctor where doc_userName=?";
			List query = jdbcTemplate.query(sql, new Object[]{doc_userName}, new RowMapper() {
				@Override
				public Object mapRow(ResultSet set, int rownum)
						throws SQLException {
					Doctor doctor=new Doctor();
					doctor.setId(set.getInt("doc_id"));
					doctor.setName(set.getString("doc_name"));
					doctor.setPassword(set.getString("doc_password"));
					doctor.setUserName(set.getString("doc_userName"));
					doctor.setSex(set.getBoolean("doc_sex"));
					doctor.setHospital(set.getString("doc_hospital"));
					doctor.setAge(set.getInt("doc_age"));
					return doctor;
				}
			});
			if(query == null||query.size() == 0) return null;
			return (Doctor) query.get(0);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	/**
	 更新医生个人信息
	 */
	public void  updateDoctor(Doctor doctor){
		getJdbcTemplate();
		if(findByUserName(doctor.getUserName()) != null){
			try{
				
				String sql = "update dbo.doctor set doc_name=?,doc_password=?,doc_sex=?,"
						+ "doc_hospital=?,doc_age=? where doc_userName=? ";
				jdbcTemplate.update(sql,new Object[] {doctor.getName(),doctor.getPassword(),
						doctor.getSex(),doctor.getHospital(),doctor.getAge(),doctor.getUserName()});			
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
