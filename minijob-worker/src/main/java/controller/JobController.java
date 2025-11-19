package controller;

import core.LocalJobExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import protoss.minijob.common.request.DispatchRequest;

/**
 * @author: ZhuChenyang
 * @date: 2025/11/19
 * @description:
 */
@RestController
@RequiredArgsConstructor
public class JobController {

    private final LocalJobExecutor localJobExecutor;

    @PostMapping("/job/run")
    public String run(@RequestBody DispatchRequest dispatchRequest) {
        localJobExecutor.execute(dispatchRequest);
        return "OK";
    }
}
