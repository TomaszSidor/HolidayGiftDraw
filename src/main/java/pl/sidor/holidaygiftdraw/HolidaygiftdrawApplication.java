package pl.sidor.holidaygiftdraw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class HolidaygiftdrawApplication {

    public static void main(String[] args) {
        SpringApplication.run(HolidaygiftdrawApplication.class, args);
    }

}
