package net.lahlalia.previsions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PrevisionsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrevisionsApplication.class, args);
    }

}
