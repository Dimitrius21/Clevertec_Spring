package ru.clevertec.ecl.config;

import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import javax.sql.DataSource;
import java.util.Properties;


/**
 * Конфигурация приложения для Spring для тестирования
 */

@Configuration
@EnableWebMvc
@ComponentScan("ru.clevertec.ecl")
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
@Profile("test")
public class TestConfig { //implements WebMvcConfigurer {

    @Bean
    public DataSource dataSource() {
        try {
            EmbeddedDatabaseBuilder dbBuilder = new EmbeddedDatabaseBuilder();
            DataSource dataSource = dbBuilder.setType(EmbeddedDatabaseType.H2)
                    .addScripts("classpath:schema.sql", "classpath:data.sql")
                    .build();
            return dataSource;
        } catch (Exception е) {
            return null;
        }
    }

    private Properties hibernateProperties() {
        Properties hibernateProp = new Properties();
        hibernateProp.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        hibernateProp.put("hibernate.format sql", true);
        hibernateProp.put("hibernate.use sql comments", true);
        hibernateProp.put("hibernate.show_sql", true);
        hibernateProp.put("hibernate.max fetch_depth", 3);
        hibernateProp.put("hibernate. jdbc.batch size", 10);
        hibernateProp.put("hibernate.jdbc. fetch_size", 50);
        return hibernateProp;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() throws ClassNotFoundException {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(new String[]{"ru.clevertec.ecl"});
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

/*    @Bean
    public SessionFactory sessionFactory(EntityManagerFactory factory) {
        if (factory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }
        return entityManagerFactory.unwrap(SessionFactory.class);
    }*/

    @Bean
    public PlatformTransactionManager hibernateTransactionManager() throws ClassNotFoundException {
        HibernateTransactionManager transactionManager
                = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }
}
