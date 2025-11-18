package protoss.minijob.server.core;

import org.springframework.stereotype.Component;
import protoss.minijob.common.model.WorkerNode;

@Component
public class WorkerManager {
    public WorkerNode selectWorker() {
        // 真实 PowerJob 会从数据库 / cache 里读取存活 worker
        return new WorkerNode("127.0.0.1:23333");
    }
}
