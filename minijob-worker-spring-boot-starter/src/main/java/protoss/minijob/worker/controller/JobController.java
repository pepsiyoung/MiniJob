package protoss.minijob.worker.controller;

import org.springframework.web.bind.annotation.*;
import protoss.minijob.common.request.HeartbeatRequest;

/**
 * @author: ZhuChenyang
 * @date: 2025/11/19
 * @description:
 */
@RestController
public class JobController {

    @PostMapping("/job/run")
    public String run(@RequestBody HeartbeatRequest heartbeatRequest) {
        return "Hello from my-controller-starter!!!" + heartbeatRequest.getAddress();
    }
}
