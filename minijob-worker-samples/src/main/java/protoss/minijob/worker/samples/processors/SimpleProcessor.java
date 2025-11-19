package protoss.minijob.worker.samples.processors;

import annotation.MiniJobHandler;
import org.springframework.stereotype.Component;

@Component
public class SimpleProcessor {

    @MiniJobHandler(name = "one")
    public void one() {
        System.out.println("one");
    }
}
