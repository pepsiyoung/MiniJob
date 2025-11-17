package protoss.minijob.worker.autoconfigure;

import annotation.MiniJobHandler;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.Arrays;

@Configuration
@EnableConfigurationProperties(MiniJobProperties.class)
@ConditionalOnProperty(prefix = "minijob.worker", name = "enabled", havingValue = "true", matchIfMissing = false)
public class MiniJobAutoConfiguration {

//    @Bean
//    @ConditionalOnMissingBean
//    public Integer initMiniJob2(MiniJobProperties properties) {
//        System.out.println("Initializing MiniJob");
//        return 1;
//    }

    private final ApplicationContext applicationContext;

    public MiniJobAutoConfiguration(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * 执行初始化程序
     */
    @Bean
    public CommandLineRunner initMiniJob(MiniJobProperties properties) {
        return args -> {
//            MiniJobProperties.Worker worker = properties.getWorker();
//            System.out.println(worker.getAppName());
//            System.out.println("【MyStarter】自定义starter初始化完成！");

            // 获取所有 Bean 的名称
            String[] beanNames = applicationContext.getBeanDefinitionNames();

            for (String beanName : beanNames) {
                Object bean = applicationContext.getBean(beanName);
                Class<?> clazz = bean.getClass();

                // 获取真实目标类（可能是 CGLIB 代理）
                Class<?> targetClass = AopProxyUtils.ultimateTargetClass(bean);

                for (Method method : targetClass.getDeclaredMethods()) {

                    // 找出方法上的指定注解
                    MiniJobHandler ann = method.getAnnotation(MiniJobHandler.class);
                    if (ann != null) {
                        System.out.println("扫描到 Job = " + ann.name());
                        System.out.println("类名 = " + clazz.getName());
                        System.out.println("方法 = " + method.getName());
                        System.out.println("返回类型 = " + method.getReturnType());
                        System.out.println("参数类型 = " + Arrays.toString(method.getParameterTypes()));
                    }
                }
            }
        };
    }
}
