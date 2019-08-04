package server.answer;

import handler.answer.ThreadPooledEventLoopAnswer;

import java.io.IOException;
import java.nio.channels.Selector;

public class ThreadPoolServerAnswer extends ServerAnswer {

    public ThreadPoolServerAnswer(int port) {
        super(port);
    }

    @Override
    protected void handleConnections(Selector selector) throws IOException {
        new ThreadPooledEventLoopAnswer().runOver(selector);
    }

    public static void main(String[] args) throws IOException {
        new ThreadPoolServerAnswer(81).start();
    }
}