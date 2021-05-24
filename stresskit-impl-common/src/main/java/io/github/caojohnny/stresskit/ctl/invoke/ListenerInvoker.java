package io.github.caojohnny.stresskit.ctl.invoke;

import io.github.caojohnny.stresskit.event.BotEvent;

public interface ListenerInvoker {
    Object getListener();

    void invoke(BotEvent event) throws Throwable;
}
