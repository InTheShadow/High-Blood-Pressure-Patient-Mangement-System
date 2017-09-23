package experi.dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class BaseDao {

	protected JdbcTemplate jdbcTemplate;
	public void getJdbcTemplate(){
		if(jdbcTemplate==null){
		  ApplicationContext applicationContext = new ClassPathXmlApplicationContext("experi/dao/applicationContext.xml");
		  jdbcTemplate= (JdbcTemplate) applicationContext.getBean("jdbcTemplate");
		}
	}
}
