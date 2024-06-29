package net.lahlalia.previsions;

import net.lahlalia.previsions.mappers.PrevisionMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class PrevisionsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrevisionsApplication.class, args);
    }

}
