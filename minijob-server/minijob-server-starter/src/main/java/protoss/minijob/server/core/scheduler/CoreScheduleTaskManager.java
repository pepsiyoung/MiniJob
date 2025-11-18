package protoss.minijob.server.core.scheduler;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import protoss.minijob.common.enums.TimeExpressionType;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CoreScheduleTaskManager implements InitializingBean, DisposableBean {

    private final PowerScheduleService powerScheduleService;

//    private final InstanceStatusCheckService instanceStatusCheckService;

    private final List<Thread> coreThreadContainer = new ArrayList<>();

    @SuppressWarnings("AlibabaAvoidManuallyCreateThread")
    @Override
    public void afterPropertiesSet() {
        // 定时调度
        coreThreadContainer.add(new Thread(new LoopRunnable("ScheduleCronJob", PowerScheduleService.SCHEDULE_RATE, () -> powerScheduleService.scheduleNormalJob(TimeExpressionType.CRON)), "Thread-ScheduleCronJob"));
        // coreThreadContainer.add(new Thread(new LoopRunnable("ScheduleFrequentJob", PowerScheduleService.SCHEDULE_RATE, powerScheduleService::scheduleFrequentJob), "Thread-ScheduleFrequentJob"));
        coreThreadContainer.forEach(Thread::start);
    }

    @Override
    public void destroy() {
        log.info("minijob-server destroy");
    }

    @RequiredArgsConstructor
    private static class LoopRunnable implements Runnable {

        private final String taskName;

        private final Long runningInterval;

        private final Runnable innerRunnable;

        @SuppressWarnings("BusyWait")
        @Override
        public void run() {
            log.info("start task : {}.", taskName);
            while (true) {
                try {

                    // 倒置顺序为 先 sleep 再执行，解决异常情况 while true 打日志的问题 https://github.com/PowerJob/PowerJob/issues/769
                    Thread.sleep(runningInterval);

                    innerRunnable.run();
                } catch (InterruptedException e) {
                    log.warn("[{}] task has been interrupted!", taskName, e);
                    break;
                } catch (Exception e) {
                    log.error("[{}] task failed!", taskName, e);
                }
            }
        }
    }
}
