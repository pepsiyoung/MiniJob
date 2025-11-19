package controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ZhuChenyang
 * @date: 2025/11/19
 * @description:
 */
@RestController("/job")
public class JobController {

    @GetMapping("/run/{appId}")
    public String run(@PathVariable String appId) {
        System.out.println("执行");
        return appId;
    }
}
