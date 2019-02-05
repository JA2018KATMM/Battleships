package academy.konrad.group.battleships.server;

import java.util.concurrent.ThreadFactory;


class ServerThreadsFactory implements ThreadFactory {
    private int roomCounter = 0;
    private final String threadName;

    ServerThreadsFactory(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public Thread newThread(Runnable r) {
        roomCounter++;
        return new Thread(r, String.format("%s%d", threadName, roomCounter));
    }
}
