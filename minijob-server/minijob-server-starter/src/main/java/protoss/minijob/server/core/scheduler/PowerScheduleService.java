package protoss.minijob.server.core.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PowerScheduleService {
    public static final long SCHEDULE_RATE = 15000;

    public void scheduleFrequentJob() {
        long start = System.currentTimeMillis();
        // 调度 FIX_RATE/FIX_DELAY 表达式 JOB
        try {
//            final List<Long> allAppIds = appInfoRepository.listAppIdByCurrentServer(transportService.defaultProtocol().getAddress());
//            if (CollectionUtils.isEmpty(allAppIds)) {
//                log.info("[FrequentJobSchedule] current server has no app's job to schedule.");
//                return;
//            }
//            scheduleFrequentJobCore(allAppIds);
            scheduleFrequentJobCore(List.of(1L));
        } catch (Exception e) {
            log.error("[FrequentJobSchedule] schedule frequent job failed.", e);
        }
        long cost = System.currentTimeMillis() - start;
        log.info("[FrequentJobSchedule] frequent job schedule use {} ms.", cost);
        if (cost > SCHEDULE_RATE) {
            log.warn("[FrequentJobSchedule] The database query is using too much time({}ms), please check if the database load is too high!", cost);
        }
    }

    private void scheduleFrequentJobCore(List<Long> appIds) {

//        RunJobRequest runJobRequest = new RunJobRequest()
//                .setAppId(jobInfoDO.getAppId()).setJobId(jobId).setDelay(Optional.ofNullable(lifeCycle.getStart()).orElse(0L) - System.currentTimeMillis());
//        jobService.runJob(runJobRequest.getAppId(), runJobRequest);

        // 简化为发送 dispatch 调度 worker 节点
        // transportService.tell(taskTracker.getProtocol(), workerUrl, req);
    }
}
