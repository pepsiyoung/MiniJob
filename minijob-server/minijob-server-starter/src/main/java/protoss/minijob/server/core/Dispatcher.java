package protoss.minijob.server.core;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import protoss.minijob.common.model.JobInstance;
import protoss.minijob.common.model.WorkerNode;
import protoss.minijob.common.request.DispatchRequest;

import java.util.concurrent.CompletableFuture;

@Component
public class Dispatcher {
    private final RestTemplate restTemplate = new RestTemplate();

    public boolean runJob(WorkerNode worker, JobInstance instance) {
        try {
            String url = "http://localhost:8080/job/run";
            DispatchRequest body = new DispatchRequest();
            body.setInstanceId(instance.getId());
            body.setJobName("one");
            body.setParams(instance.getParams());

            restTemplate.postForObject(url, body, String.class);
            return true;
        } catch (Exception e) {
            // 这里可以打日志
            return false;
        }
    }

    public void runJobAsync(WorkerNode worker, JobInstance instance) {
        CompletableFuture.runAsync(() -> {
            try {
                String url = "http://localhost:8080/job/run";
                DispatchRequest body = new DispatchRequest();
                body.setInstanceId(instance.getId());
                body.setJobName("one");
                body.setParams(instance.getParams());
                restTemplate.postForObject(url, body, Void.class);
            } catch (Exception e) {
                // 这里可以打日志
            }
        });
    }
}
