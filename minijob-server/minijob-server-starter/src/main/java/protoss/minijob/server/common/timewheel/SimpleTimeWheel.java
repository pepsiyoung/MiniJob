package protoss.minijob.server.common.timewheel;

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

    public void addTask(JobInfo job, long triggerTime) {
        long delay = triggerTime - System.currentTimeMillis();
        delay = Math.max(0, delay);

        executor.schedule(() -> {
            new PowerScheduleService().scheduleNormalJob(TimeExpressionType.CRON);
        }, delay, TimeUnit.MILLISECONDS);
    }
}
