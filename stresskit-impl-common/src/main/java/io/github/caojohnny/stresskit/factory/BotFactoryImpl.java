package io.github.caojohnny.stresskit.factory;

import io.github.caojohnny.stresskit.bot.Bot;
import io.github.caojohnny.stresskit.exec.BotRunner;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;

public abstract class BotFactoryImpl implements BotFactory {
    private final Map<String, Bot> created = new HashMap<>();

    private final BotRunner runner;

    public BotFactoryImpl(BotRunner runner) {
        this.runner = runner;
    }

    @Override
    public void init() {
        this.runner.init(this);
    }

    @Override
    public @NonNull BotRunner getRunner() {
        return this.runner;
    }

    @Override
    public @NonNull Map<String, Bot> getCreated() {
        return this.created;
    }

    @Override
    public void close() {
        this.runner.close(this);

        for (Bot bot : this.created.values()) {
            bot.despawn();
        }
        this.created.clear();
    }
}
