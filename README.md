# `stresskit`

One of the really hard things to do in performance testing
is getting enough players to participate in it, or even
getting players to participate at all. Collecting data and
stressing the performance capabilities of Bukkit servers to
their limits and playability don't exactly work well with
each other. The way that it typically works in the industry
is throw it online, beta test and then pray that nothing
falls apart when you start being hit with hundreds of
players.

While you can very easily simulate entities since their AI
is already baked into the server, it's hard to get player
accounts for things such as testing if your data back-end
is even capable of handling the number of queries needed to
load player data, player movement, chunk loading, redstone
persistence, etc. After all, the point of a server is to
optimize the experience for *players* not the entities!

`stresskit` is an attempt to write an SDK for developers to
quickly simulate real players connecting to a server and
performing different actions. This allows developers to
automate the process rather than having to join with
different offline clients or force real players to
participate in potentially mundane or unproductive
activities for testing purposes. It uses real client
connections to actually simulate the presence of a player
that connects to your server. It is capable of fully
interacting with the Minecraft protocol and offers
low-level access to primitives which allow both client
and server-sided profiling.

The purpose of `stresskit` is to abstract away logic. While
out-of-the-box support for players comes first and
foremost, it is also possible to adapt the `Bot`s to be
used for any entity.

# Building

``` shell
git clone https://github.com/caojohnny/stresskit.git
cd stresskit
mvn clean install
```

# Usage

`stresskit` can be used both by standalone programs and
Bukkit plugins.

Code samples for 
[tasks](https://github.com/caojohnny/stresskit/blob/master/stresskit-example-plugin/src/main/java/io/github/caojohnny/stresskitexample/task/WalkSouthTask.java),
[listeners](https://github.com/caojohnny/stresskit/blob/master/stresskit-example-plugin/src/main/java/io/github/caojohnny/stresskitexample/listener/BotPlayerListener.java) 
and even a complete
[working program](https://github.com/caojohnny/stresskit/blob/master/stresskit-bukkit-plugin/src/test/java/io/github/caojohnny/stresskit/ApiTest.java)
and 
[plugin](https://github.com/caojohnny/stresskit/tree/master/stresskit-example-plugin)
utilizing `stresskit` can be found in the different
modules.

## Standalone Programs

Standalone programs should depend on 
`io.github.caojohnny:stresskit-impl-v[VERSION]` for
whichever version suits the needs of the developer.

Setup:
``` java
BotRunner runner = new BotRunner() {
    @Override
    public void init(BotFactory factory) {
        ...
    }

    @Override
    public void close(BotFactory factory) {
        ...
    }
};
BotPlayerFactory factory = new BotPlayerFactory(runner) {
    @Override
    public String getServerAddress() {
        ...
    }

    @Override
    public int getServerPort() {
        ...
    }
};
factory.init();
```

From here, `factory` can be used to `createBot()`, which
are used to call `Bot#spawn()` in order to connect the bot
to the server.

`Bot`s can be customized by adding listeners to the
`BotEventController` and their actions modified by setting
its `Procedure` and the remaining tasks in the procedure.

## Bukkit Plugins

Bukkit plugins are recommended to depend on
`io.github.caojohnny:stresskit-bukkit-plugin` and to add
`StressKit` to the `plugin.yml` `depend` field.

Setup:
``` java
// Initialized with the plugin instance
BotRunner runner = new BukkitBotRunner(this);

BotPlayerFactory factory = new BukkitBotPlayerFactory(runner);
factory.init();
```

The same usage as for standalone programs can be used to
create bots in a Bukkit plugin.

# Notes

- This is for the most part pretty incomplete and
underdocumented.
- A lot of logic has been left to developers to figure out
on their own. Only basic movement and connection to the
server is handled out-of-the-box.
- Currently only 1.14.4 is available, but there are only a
few version-dependent components that need to be ported to
other versions should that be required.
- It is highly likely that none of this is even
thread-safe, I'm not really sure how MCProtocolLib handles
threading.
- The task and event control is low-level and very
primitive, e.g. there is no order control or anything.

# Credits

Built with [IntelliJ IDEA](https://www.jetbrains.com/idea/)

Uses [Checker Framework](https://checkerframework.org/),
[errorprone](https://errorprone.info/), and 
[MCProtocolLib](https://github.com/Steveice10/MCProtocolLib)
