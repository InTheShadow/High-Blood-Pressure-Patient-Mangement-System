package experi.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import experi.entity.Advice;
import experi.entity.Patient;

public class AdviceDao extends BaseDao {

	public void insertAdvice(Advice advice){
		getJdbcTemplate();
		try{
			String sql = "insert into dbo.advice values(?,?,?,?)";
			jdbcTemplate.update(sql,new Object[] { 
					advice.getDoc_id(),advice.getPat_id(),
					advice.getAdvice_value(),advice.getAdvice_recordTime()
			}
			);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public Advice[] findAdvices(int pat_id){
		getJdbcTemplate();
		try{

			String sql = "select * from dbo.Advice where pat_id=?";
			List query = jdbcTemplate.query(sql, new Object[]{pat_id}, new RowMapper() {
				@Override
				public Object mapRow(ResultSet set, int rownum)
						throws SQLException {
					Advice advice = new Advice();
					advice.setDoc_id(set.getInt("doc_id"));
					advice.setAdvice_value(set.getString("advice_value"));
					advice.setAdvice_recordTime(set.getTimestamp("advice_recordTime"));
					return advice;
				}
			});
			if(query == null||query.size() == 0) return null;
			Advice result[] = new Advice[query.size()];
			for(int i = 0;i<query.size();i++) result[i] = (Advice)query.get(i); 
			return result;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
