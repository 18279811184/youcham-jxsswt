package io.youcham.common.config;


import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 定时任务开关
 */
public class SchedulerCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        boolean enable = Boolean.valueOf(context.getEnvironment().getProperty("youcham.scheduling.enabled"));
        return enable;
    }
}
