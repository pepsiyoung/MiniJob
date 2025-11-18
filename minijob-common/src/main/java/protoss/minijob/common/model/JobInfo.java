package protoss.minijob.common.model;

import lombok.Data;

@Data
public class JobInfo {
    private Long id;
    private boolean enabled;
    private String cron;
    // private JobType type;   // CRON / FIXED_RATE / FIXED_DELAY
    private long intervalMs;
    private long nextTriggerTime;
    private String params;
}
