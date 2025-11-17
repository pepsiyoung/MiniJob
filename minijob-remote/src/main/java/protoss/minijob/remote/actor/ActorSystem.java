package protoss.minijob.remote.actor;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ActorSystem {

    private final Map<String, Object> actorContainer = new ConcurrentHashMap<>();

    // 简单线程池，用于异步执行
    private final ExecutorService executor = Executors.newCachedThreadPool();

    /**
     * 注册一个 Actor 实例
     */
    public void registerActor(String name, Object actorInstance) {
        if (actorContainer.containsKey(name)) {
            throw new IllegalStateException("Duplicate actor name: " + name);
        }
        actorContainer.put(name, actorInstance);
    }

    /**
     * 仅发送消息，不关心结果
     */
    public void tell(String actorName, String methodName, Object arg) {
        Object actor = getActor(actorName);
        executor.submit(() -> invokeMethod(actor, methodName, arg));
    }

    /**
     * 发送消息并等待结果
     */
    @SuppressWarnings("unchecked")
    public <T> CompletableFuture<T> ask(String actorName, String methodName, Object arg, Class<T> returnType) {
        Object actor = getActor(actorName);
        CompletableFuture<T> future = new CompletableFuture<>();
        executor.submit(() -> {
            try {
                Object result = invokeMethod(actor, methodName, arg);
                if (result == null) {
                    future.complete(null);
                } else if (!returnType.isAssignableFrom(result.getClass())) {
                    future.completeExceptionally(new ClassCastException(
                            "Return type mismatch, expect " + returnType + " but got " + result.getClass()));
                } else {
                    future.complete((T) result);
                }
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        });
        return future;
    }

    // ---------------- internal ----------------

    private Object getActor(String actorName) {
        Object actor = actorContainer.get(actorName);
        if (actor == null) {
            throw new IllegalArgumentException("Actor not found: " + actorName);
        }
        return actor;
    }

    /**
     * 使用反射调用对应方法，这里做一个简化版：
     * 根据方法名 + 单参数类型匹配
     */
    private Object invokeMethod(Object actor, String methodName, Object arg) throws Exception {
        Class<?> clazz = actor.getClass();
        Method target = null;
        for (Method method : clazz.getMethods()) {
            if (!method.getName().equals(methodName)) {
                continue;
            }
            Class<?>[] pts = method.getParameterTypes();
            if (pts.length == 0 && arg == null) {
                target = method;
                break;
            }
            if (pts.length == 1 && (arg == null || pts[0].isAssignableFrom(arg.getClass()))) {
                target = method;
                break;
            }
        }
        if (target == null) {
            throw new NoSuchMethodException("No suitable method " + methodName + " in actor " + clazz.getName());
        }
        return target.invoke(actor, arg);
    }
}
