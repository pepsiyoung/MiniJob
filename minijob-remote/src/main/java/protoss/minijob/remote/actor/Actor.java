package protoss.minijob.remote.actor;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Actor {

    /**
     * Actor 名称，全局唯一，后续通过这个名字调用
     */
    String name();
}
