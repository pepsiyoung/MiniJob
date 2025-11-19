package protoss.minijob.worker.samples;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SamplesApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SamplesApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(111);
    }
}
