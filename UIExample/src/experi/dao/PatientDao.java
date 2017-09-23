package experi.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import experi.entity.Patient;

public class PatientDao extends BaseDao {
		/**
		 �����߲������ݿ�
		 */
		public void insertPatient(Patient patient){
			getJdbcTemplate();
			try{
				String sql = "insert into dbo.Patient(pat_name,pat_password,pat_userName,pat_sex,"
							+ "pat_address,pat_age,pat_history,"
							+ "pat_weight,pat_height)  values(?,?,?,?,?,?,?,?,?)";
				jdbcTemplate.update(sql,new Object[] { 
					patient.getName(),patient.getPassword(),
					patient.getUserName(),patient.getSex(),patient.getAddress(),
					patient.getAge(),patient.getHistory(),patient.getWeight(),
					patient.getHeight()}
				);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		/**
		 ɾ��
		 */	
		public void deletePatient(Patient patient){
			getJdbcTemplate();
			
			try{
				String sql = "delete from dbo.Patient where pat_userName=?";
				jdbcTemplate.update(sql,new Object[] {patient.getUserName()});			
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		/**
		 ���ҹ̶�����
		 */
		
		public Patient findByUserName(String pat_userName){
			getJdbcTemplate();
			try{
				String sql = "select * from dbo.Patient where pat_userName=?";
				List query = jdbcTemplate.query(sql, new Object[]{pat_userName}, new RowMapper() {
					@Override
					public Object mapRow(ResultSet set, int rownum)
							throws SQLException {
						Patient patient=new Patient();
						patient.setId(set.getInt("pat_id"));
						patient.setDocId(set.getInt("doc_id"));
						patient.setName(set.getString("pat_name"));
						patient.setPassword(set.getString("pat_password"));
						patient.setUserName(set.getString("pat_userName"));
						patient.setSex(set.getBoolean("pat_sex"));
						patient.setAddress(set.getString("pat_address"));
						patient.setAge(set.getInt("pat_age"));
						patient.setHistory(set.getString("pat_history"));
						patient.setWeight(set.getInt("pat_weight"));
						patient.setHeight(set.getInt("pat_height"));
						return patient;
					}
				});
				if(query == null||query.size() == 0) return null;
				return (Patient) query.get(0);
			}catch(Exception e){
				e.printStackTrace();
			}
			return null;
		}
		/**
		 ���»��߸���ҽ��
		 */
		public void updateDocId(int doc_id, String pat_userName){
			getJdbcTemplate();
			try{
				String sql = "update dbo.Patient set doc_id =? where pat_userName=?";
				jdbcTemplate.update(sql,new Object[]{doc_id,pat_userName});
			}catch(Exception e){
				e.printStackTrace();
			}

		}
		/**
		 ���߸���ҽ����Ϊ��
		 */
		
		public void removeDocId(String pat_userName){
			getJdbcTemplate();
			try{
				String sql = "update dbo.Patient set doc_id =null where pat_userName=?";
				jdbcTemplate.update(sql,new Object[]{pat_userName});
			}catch(Exception e){
				e.printStackTrace();
			}

		}
		/**
		 ���»��߸�����Ϣ
		 */
		public void  updatePatient(Patient patient){
			getJdbcTemplate();
				try{
					
					String sql = "update dbo.patient set pat_name=?,pat_password=?,pat_sex=?,"
							+ "pat_address=?,pat_age=?,pat_history=?,"
							+ "pat_weight=?,pat_height=? where pat_userName=? ";
					jdbcTemplate.update(sql,new Object[] {patient.getName(),patient.getPassword(),
							patient.getSex(),patient.getAddress(),patient.getAge(),
							patient.getHistory(),patient.getWeight(),patient.getHeight(),
							patient.getUserName()});			
				}catch(Exception e){
					e.printStackTrace();
				}
		}
		
		
}
