package core;

import annotation.MiniJobHandler;
import model.JobDefinition;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JobDefinitionContainer implements ApplicationContextAware, SmartInitializingSingleton {

    private final Map<String, JobDefinition> registry = new ConcurrentHashMap<>();
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        this.applicationContext = ctx;
    }

    @Override
    public void afterSingletonsInstantiated() {

        String[] beanNames = applicationContext.getBeanDefinitionNames();

        for (String beanName : beanNames) {

            Object bean = applicationContext.getBean(beanName);
            Class<?> targetClass = AopUtils.getTargetClass(bean);

            // 扫描所有方法上的注解
            Map<Method, MiniJobHandler> methods =
                    MethodIntrospector.selectMethods(
                            targetClass,
                            (MethodIntrospector.MetadataLookup<MiniJobHandler>) method ->
                                    AnnotatedElementUtils.findMergedAnnotation(method, MiniJobHandler.class)
                    );

            methods.forEach((method, anno) -> {
                registry.put(anno.name(), new JobDefinition(bean, method));

                System.out.println("[MiniJob] Registered jobId=" +
                        anno.name() + ", bean=" + beanName + ", method=" + method.getName());
            });
        }
    }

    public JobDefinition getJob(String name) {
        return registry.get(name);
    }

}
