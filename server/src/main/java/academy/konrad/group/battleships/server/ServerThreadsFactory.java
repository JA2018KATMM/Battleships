package academy.konrad.group.battleships.server;

import org.pmw.tinylog.Logger;

import java.util.concurrent.ThreadFactory;


class ServerThreadsFactory implements ThreadFactory {
    private int counter = 0;
    private final String threadName;

    ServerThreadsFactory(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public Thread newThread(Runnable r) {
        counter++;
        Logger.info(String.format("Odpalam wątek %s%d", threadName, counter));
        return new Thread(r, String.format("%s%d", threadName, counter));
    }
}
