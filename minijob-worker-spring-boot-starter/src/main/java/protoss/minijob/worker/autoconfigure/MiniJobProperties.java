package protoss.minijob.worker.autoconfigure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "minijob")
public class MiniJobProperties {

    private final Worker worker = new Worker();

    @Setter
    @Getter
    public static class Worker {

        private boolean enabled = true;

        private String appName;
    }
}
