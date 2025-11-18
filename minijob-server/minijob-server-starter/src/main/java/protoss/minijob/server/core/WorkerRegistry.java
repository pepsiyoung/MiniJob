package protoss.minijob.server.core;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: ZhuChenyang
 * @date: 2025/11/18
 * @description:
 */
@Component
public class WorkerRegistry {
    // appName -> worker list
    // private static final ConcurrentHashMap<String, CopyOnWriteArrayList<WorkerInfo>> workerMap = new ConcurrentHashMap<>();

    /**
     * 项目启动时初始化
     */
    public void handleInit() {
        String key = "test";

    }

    // 心跳存储/刷新
//    public void handleHeartbeat(HeartbeatRequest req) {
//        long now = Instant.now().toEpochMilli();
//        CopyOnWriteArrayList<WorkerInfo> list =
//                workerMap.computeIfAbsent(req.getAppName(), k -> new CopyOnWriteArrayList<>());
//        // 先尝试找到已有的
//        WorkerInfo existing = null;
//        for (WorkerInfo wi : list) {
//            if (wi.getAddress().equals(req.getAddress())) {
//                existing = wi;
//                break;
//            }
//        }
//        if (existing != null) {
//            existing.setLastActiveTime(now);
//        } else {
//            list.add(new WorkerInfo(req.getAppName(), req.getAddress(), req.getTag(), now));
//        }
//    }

    // 查询某个 app 的所有在线 worker
//    public List<WorkerInfo> listWorkers(String appName) {
//        CopyOnWriteArrayList<WorkerInfo> list = workerMap.get(appName);
//        if (list == null) {
//            return new ArrayList<>();
//        }
//        return new ArrayList<>(list);
//    }
    // 定时清理过期 worker，每 10 秒执行一次
    // 超过 20 秒没有心跳，认为下线
//    @Scheduled(fixedDelay = 10_000)
//    public void cleanExpiredWorkers() {
//        long now = Instant.now().toEpochMilli();
//        long expireMillis = 20_000;
//        workerMap.forEach((appName, list) -> {
//            list.removeIf(wi -> now - wi.getLastActiveTime() > expireMillis);
//            if (list.isEmpty()) {
//                workerMap.remove(appName);
//            }
//        });
//    }
}
