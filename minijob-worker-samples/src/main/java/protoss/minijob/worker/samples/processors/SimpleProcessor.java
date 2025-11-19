package protoss.minijob.worker.samples.processors;

import annotation.MiniJobHandler;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class SimpleProcessor {

    @MiniJobHandler(name = "one")
    public void one() {
        System.out.println("one" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
}
