package io.github.caojohnny.stresskit.ctl.invoke;

import io.github.caojohnny.stresskit.event.BotEvent;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;

public class MHandleListenerInvoker implements ListenerInvoker {
    private final Object listener;
    private final MethodHandle handle;

    public MHandleListenerInvoker(Object listener, Method method) throws IllegalAccessException {
        this.listener = listener;

        MethodHandle mh = MethodHandles.lookup().unreflect(method);
        this.handle = mh.asType(mh.type()
                .changeParameterType(0, Object.class)
                .changeParameterType(1, BotEvent.class));
    }

    @Override
    public Object getListener() {
        return this.listener;
    }

    @Override
    public void invoke(BotEvent event) throws Throwable {
        this.handle.invokeExact(this.listener, event);
    }
}
