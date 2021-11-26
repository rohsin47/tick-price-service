package org.soloactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        ThymeleafAutoConfiguration.class
})
public class TickPriceServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(TickPriceServiceApp.class, args);
    }


}
