package protoss.minijob.server.common.timewheel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import protoss.minijob.common.enums.TimeExpressionType;
import protoss.minijob.common.model.JobInfo;
import protoss.minijob.server.core.scheduler.PowerScheduleService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class SimpleTimeWheel {
    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    @Autowired
    private PowerScheduleService powerScheduleService;
    public void addTask(JobInfo job, long triggerTime) {
        long delay = triggerTime - System.currentTimeMillis();
        delay = Math.max(0, delay);

        System.out.println(powerScheduleService);

        executor.schedule(() -> {
            powerScheduleService.scheduleNormalJob(TimeExpressionType.CRON);
        }, delay, TimeUnit.MILLISECONDS);
    }
}
