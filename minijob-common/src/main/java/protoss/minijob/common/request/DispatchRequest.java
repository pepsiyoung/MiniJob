package protoss.minijob.common.request;

import lombok.Data;

@Data
public class DispatchRequest {
    private Long instanceId;
    private String jobName;
    private String params;   // JSON 或普通文本
}
