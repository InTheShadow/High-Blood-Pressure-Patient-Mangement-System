package experi.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import experi.entity.*;

public class BodyDao extends BaseDao {
	//插入心率
	public void insertHR(Body body){
		getJdbcTemplate();
		try{
			String sql = "insert into dbo.HR values(?,?,?)";
			jdbcTemplate.update(sql,new Object[] { 
					body.getPat_id(),body.getHR(),body.getRecord_time()
			}
			);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//插入血压
	public void insertPre(Body body){
		getJdbcTemplate();
		try{
			String sql = "insert into dbo.Pressure values(?,?,?,?)";
			jdbcTemplate.update(sql,new Object[] { 
					body.getPat_id(),body.getLPre(),body.getHPre(),body.getRecord_time()
			}
			);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//插入血糖
	public void insertSug(Body body){
		getJdbcTemplate();
		try{
			String sql = "insert into dbo.Sugar values(?,?,?)";
			jdbcTemplate.update(sql,new Object[] { 
					body.getPat_id(),body.getSug(),body.getRecord_time()
			}
			);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//找到患者所有身体数据
	public Body[] findBodies(int pat_id){
		getJdbcTemplate();
		try{

			String sql = 	"select * from"
					+" (select * from dbo.HR where pat_id=?) AS hr full join "
					+" (select * from dbo.Pressure where pat_id=?) AS pre"
					+" on hr.hr_recordTime = pre.pressure_recordTime full join"
					+" (select * from dbo.Sugar where pat_id=?) AS sug"
					+" on hr.hr_recordTime = sug.sug_recordTime";
			List query = jdbcTemplate.query(sql, new Object[]{pat_id,pat_id,pat_id}, new RowMapper() {
				@Override
				public Object mapRow(ResultSet set, int rownum)
						throws SQLException {
					Body body = new Body();
					body.setPat_id(pat_id);
					body.setHR(set.getInt("hr_value"));
					body.setLPre(set.getInt("pressure_low"));
					body.setHPre(set.getInt("pressure_high"));
					body.setSug(set.getFloat("sug_value"));
					if(set.getTimestamp("hr_recordTime") != null ) 
						body.setRecord_time(set.getTimestamp("hr_recordTime"));
					else if(set.getTimestamp("pressure_recordTime") != null)
						body.setRecord_time(set.getTimestamp("pressure_recordTime"));
					else body.setRecord_time(set.getTimestamp("sug_recordTime"));
					return body;
				}
			});
			if(query == null|| query.size() == 0) return null;
			Body result[] = new Body[query.size()];
			for(int i = 0;i<query.size();i++) result[i] = (Body)query.get(i); 
			return result;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	//找到患者最新的身体数据
	public Body findFinalBody(int pat_id){
		getJdbcTemplate();
		try{

			String sql =	"select * from"
					+" (select top 1 hr_value from dbo.HR where pat_id=? order by hr_recordTime DESC) AS HR,"
					+" (select top 1 pressure_low,pressure_high from dbo.Pressure where pat_id=? order by pressure_recordTime DESC) AS Pre,"
					+" (select top 1 sug_value from dbo.Sugar where pat_id=? order by sug_recordTime DESC) AS sug"; 
			List query = jdbcTemplate.query(sql, new Object[]{pat_id,pat_id,pat_id}, new RowMapper() {
				@Override
				public Object mapRow(ResultSet set, int rownum)
						throws SQLException {
					Body body = new Body();
					body.setPat_id(pat_id);
					body.setHR(set.getInt("hr_value"));
					body.setLPre(set.getInt("pressure_low"));
					body.setHPre(set.getInt("pressure_high"));
					body.setSug(set.getFloat("sug_value"));
					return body;
				}
			});
			if(query == null || query.size() == 0) return null;
			return (Body)query.get(0);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
