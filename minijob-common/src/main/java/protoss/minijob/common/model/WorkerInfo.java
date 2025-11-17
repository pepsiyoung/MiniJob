package protoss.minijob.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkerInfo {
    private String appName;
    private String address;
    private String tag;
    private long lastActiveTime;
}
