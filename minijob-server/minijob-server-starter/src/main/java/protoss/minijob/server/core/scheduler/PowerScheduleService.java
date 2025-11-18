package protoss.minijob.server.core.scheduler;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import protoss.minijob.common.enums.TimeExpressionType;
import protoss.minijob.common.model.JobInfo;
import protoss.minijob.common.model.JobInstance;
import protoss.minijob.common.model.WorkerNode;
import protoss.minijob.server.common.timewheel.SimpleTimeWheel;
import protoss.minijob.server.core.WorkerManager;
import protoss.minijob.server.core.scheduler.auxiliary.impl.CronTimingStrategyHandler;

import java.util.List;
import java.util.Optional;

import static protoss.minijob.common.enums.TimeExpressionType.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PowerScheduleService {
    private static final int MAX_APP_NUM = 10;
    public static final long SCHEDULE_RATE = 15000;

    @Autowired
    private WorkerManager workerManager;
    @Autowired
    private SimpleTimeWheel timeWheel;
    @Autowired
    private CronTimingStrategyHandler cronTimingStrategyHandler;


    public void scheduleNormalJob(TimeExpressionType timeExpressionType) {
        long start = System.currentTimeMillis();
        // 调度 CRON 表达式 JOB
        try {
//            final List<Long> allAppIds = appInfoRepository.listAppIdByCurrentServer(transportService.defaultProtocol().getAddress());
//            if (CollectionUtils.isEmpty(allAppIds)) {
//                log.info("[NormalScheduler] current server has no app's job to schedule.");
//                return;
//            }
//            scheduleNormalJob0(timeExpressionType, allAppIds);
            scheduleNormalJob0(timeExpressionType, List.of(1L));
        } catch (Exception e) {
            log.error("[NormalScheduler] schedule cron job failed.", e);
        }
        long cost = System.currentTimeMillis() - start;
        log.info("[NormalScheduler] {} job schedule use {} ms.", timeExpressionType, cost);
        if (cost > SCHEDULE_RATE) {
            log.warn("[NormalScheduler] The database query is using too much time({}ms), please check if the database load is too high!", cost);
        }
    }

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

    private void scheduleNormalJob0(TimeExpressionType timeExpressionType, List<Long> appIds) {

//        Lists.partition(appIds, MAX_APP_NUM).forEach(partAppIds -> {
//        });

        JobInfo job = new JobInfo();
        // ① 基础校验
        if (!job.isEnabled()) {
            System.out.println("Job disabled, skip");
            return;
        }

        long now = System.currentTimeMillis();

        // 未到达下一次执行时间
        if (job.getNextTriggerTime() > now) {
            return;
        }

        // ② 创建 JobInstance（一次调度实例）
        JobInstance instance = new JobInstance();
        instance.setJobId(job.getId());
        instance.setTriggerTime(now);
        instance.setParams(job.getParams());
        // instanceRepo.save(instance);

        // ③ 选择 Worker 节点
        WorkerNode selected = workerManager.selectWorker();
        if (selected == null) {
            System.err.println("No worker available, retry later");
            // 重试：几秒后重放调度
            timeWheel.addTask(job, now + 3000);
            return;
        }

        // ④ 派发实例
        boolean success = dispatchToWorker(selected, instance);
        if (!success) {
            // 派发失败则重试
            long retryTime = now + 3000;
            System.err.println("Dispatch failed, retry @ " + retryTime);
            timeWheel.addTask(job, retryTime);
            return;
        }

        System.out.println("Dispatch OK instanceId=" + instance.getId());

        // ⑤ 计算下一次执行时间
        long nextTime = calcNextTriggerTime(job, now);
        job.setNextTriggerTime(nextTime);

        // ⑥ 放入时间轮
        timeWheel.addTask(job, nextTime);
    }

    private boolean dispatchToWorker(WorkerNode worker, JobInstance instance) {
        try {
            // 模拟 HTTP / RPC 调用
            System.out.println("Send instance " + instance.getId()
                    + " to worker " + worker.getAddress());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private long calcNextTriggerTime(JobInfo job, long now) {
        return cronTimingStrategyHandler.calculateNextTriggerTime(now, job.getCron(), null,null);
    }
}
