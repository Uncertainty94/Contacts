package ru.reksoft.lab.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import ru.reksoft.lab.dao.ContactDao;
import ru.reksoft.lab.dao.impl.HiberDaoImpl;

import org.springframework.core.env.Environment;
import ru.reksoft.lab.domain.Contact;

/**
 * Created by mishanin on 24.10.2016.
 */
@Configuration
@PropertySource(value="classpath:server.properties")
public class DaoConfig {

    @Autowired
    private Environment environment;

    @Bean
    public ContactDao hibernateDAO(){
        return new HiberDaoImpl(sessionFactory());
    }

    @Bean
    public SessionFactory sessionFactory() {
        LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource());
        sessionBuilder.addAnnotatedClasses(Contact.class);
        return sessionBuilder.buildSessionFactory();
    }

    @Bean
    public DriverManagerDataSource dataSource(){
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(environment.getProperty("JDBC_DRIVER"));
        driverManagerDataSource.setUrl(environment.getProperty("DB_URL"));
        driverManagerDataSource.setUsername(environment.getProperty("USER"));
        driverManagerDataSource.setPassword(environment.getProperty("PASS"));
        return driverManagerDataSource;
    }

}
