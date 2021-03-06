package my.rest.test.config;


import java.util.Properties;
import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
//import com.google.common.base.Preconditions;
 
@Configuration
@EnableTransactionManagement
//@PropertySource({ "classpath:persistence-mysql.properties" })
@ComponentScan({ "my.rest.test.persistence" })
public class PersistenceConfig {
 
   @Autowired
   private Environment env;
 
   @Bean
   public LocalSessionFactoryBean sessionFactory() {
      LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
      sessionFactory.setDataSource(restDataSource());
      sessionFactory.setPackagesToScan(new String[] { "my.rest.test.persistence" });
      sessionFactory.setHibernateProperties(hibernateProperties());
 
      return sessionFactory;
   }
 
   @Bean
   public DataSource restDataSource() {
      BasicDataSource dataSource = new BasicDataSource();
      
//      dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
//      dataSource.setUrl(env.getProperty("jdbc.url"));
//      dataSource.setUsername(env.getProperty("jdbc.user"));
//      dataSource.setPassword(env.getProperty("jdbc.pass"));
      
      dataSource.setDriverClassName("org.postgresql.Driver");
      dataSource.setUrl("jdbc:postgresql://localhost:5432/spring_hibernate_dev");
      dataSource.setUsername("coploftbas");
      dataSource.setPassword("a123456789");
 
      return dataSource;
   }
 
   @Bean
   @Autowired
   public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
      HibernateTransactionManager txManager = new HibernateTransactionManager();
      txManager.setSessionFactory(sessionFactory);
 
      return txManager;
   }
 
   @Bean
   public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
      return new PersistenceExceptionTranslationPostProcessor();
   }
 
   Properties hibernateProperties() {
      return new Properties() {
         {
            setProperty("hibernate.hbm2ddl.auto", "create-drop");
            setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
            setProperty("hibernate.globally_quoted_identifiers", "true");
         }
      };
   }
}