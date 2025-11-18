package protoss.minijob.common.model;

import lombok.Data;

@Data
public class JobInstance {
    private Long id = System.nanoTime();
    private Long jobId;
    private long triggerTime;
    private String params;
}
