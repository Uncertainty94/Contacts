package ru.reksoft.lab.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.reksoft.lab.service.ContactManager;

/**
 * Created by mishanin on 24.10.2016.
 */

@Configuration
@Import(DaoConfig.class)
public class AppConfig {

    @Autowired
    private DaoConfig daoConfig;

    @Bean
    public ContactManager contactManager(){
        ContactManager contactManager = new ContactManager();
        contactManager.setProvider(daoConfig.hibernateDAO());
        return contactManager;
    }

}
