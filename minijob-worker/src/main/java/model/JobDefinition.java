package model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Method;

@Data
@AllArgsConstructor
public class JobDefinition {
    private Object bean;
    private Method method;
}
