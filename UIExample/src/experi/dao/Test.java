package experi.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class Test {
	//≤‚ ‘
	public static void main(String[] args){
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("experi/dao/applicationContext.xml");
		JdbcTemplate jdbcTemplate = (JdbcTemplate) applicationContext.getBean("jdbcTemplate");
		DataSource dataSource = jdbcTemplate.getDataSource();
		try{
			Connection connection = dataSource.getConnection();
			connection.close();			
		}catch (SQLException e){
			e.printStackTrace();
		}
	}
}
