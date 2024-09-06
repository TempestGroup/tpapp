package kz.tempest.tpapp;

import kz.tempest.tpapp.commons.services.SettingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class TpappApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(TpappApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        SettingService.initDB();
    }
}
