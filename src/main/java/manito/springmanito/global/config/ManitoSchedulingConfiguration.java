package manito.springmanito.global.config;

import lombok.RequiredArgsConstructor;
import manito.springmanito.manito.service.ManitoService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class ManitoSchedulingConfiguration implements SchedulingConfigurer {

    private final ManitoService manitoService;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(
                () -> manitoService.assignManito(),
                triggerContext -> {
                    CronTrigger cronTrigger = new CronTrigger("0 0 12 * * *"); // 매일 새벽 4시에 실행
                    return cronTrigger.nextExecutionTime(triggerContext).toInstant();
                }
        );
    }
}
