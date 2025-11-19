package core;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import model.JobDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import protoss.minijob.common.request.DispatchRequest;

import java.lang.reflect.Method;

@Slf4j
@Component
public class LocalJobExecutor {

    @Autowired
    private JobDefinitionContainer jobDefinitionContainer;

    public void execute(DispatchRequest req) {
        try {
            JobDefinition def = jobDefinitionContainer.getJob(req.getJobName());
            if (def == null) {
                System.err.println("No job definition for jobName=" + req.getJobName());
                return;
            }

            Object bean = def.getBean();
            Method method = def.getMethod();

            Object result;

            // 方法有参数
            if (method.getParameterCount() == 1) {
                Class<?> paramType = method.getParameterTypes()[0];
                Object arg = convertArg(paramType, req.getParams());
                result = method.invoke(bean, arg);
            } else {
                // 无参方法
                result = method.invoke(bean);
            }

            System.out.println("Job executed, result=" + result);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private Object convertArg(Class<?> paramType, String raw) throws Exception {
        if (paramType == String.class) {
            return raw;
        }
        // 如果是对象类型，则 JSON 反序列化
        return new ObjectMapper().readValue(raw, paramType);
    }
}
