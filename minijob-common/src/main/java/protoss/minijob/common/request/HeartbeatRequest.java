package protoss.minijob.common.request;

import lombok.Data;

@Data
public class HeartbeatRequest {

    private String appName;
    private String address; // ip:port
    private String tag;     // worker 标签，可选

}
