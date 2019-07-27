package selector.server;

import selector.handler.AcceptHandler;
import selector.handler.ReadHandler;
import selector.handler.WriteHandler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.*;

/**
 * Created by mtumilowicz on 2019-07-26.
 */
class SelectorKeysHandler {
    private final Map<SocketChannel, Queue<ByteBuffer>> pendingData = new HashMap<>();
    private final AcceptHandler acceptHandler = new AcceptHandler(pendingData);
    private final ReadHandler readHandler = new ReadHandler(pendingData);
    private final WriteHandler writeHandler = new WriteHandler(pendingData);

    final void handle(Selector selector) throws IOException {
        while (true) {
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            keys.forEach(this::handleKey);
            keys.clear();
        }
    }

    private void handleKey(SelectionKey key) {
        try {
            acceptHandler.handle(key);
            readHandler.handle(key);
            writeHandler.handle(key);
        } catch (
                Exception ex) {
            // workshops
        }
    }
}
