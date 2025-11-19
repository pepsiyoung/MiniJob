package protoss.minijob.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import protoss.minijob.server.common.timewheel.SimpleTimeWheel;

@SpringBootApplication
public class MiniJobServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiniJobServerApplication.class, args);
    }
}
