package selector.handler.answer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Created by mtumilowicz on 2019-07-26.
 */
public class SelectorKeysHandlerAnswer {
    private final Map<SocketChannel, Queue<ByteBuffer>> pendingData = new HashMap<>();
    private final AcceptHandlerAnswer acceptHandler = new AcceptHandlerAnswer(pendingData);
    private final SingleThreadedReadHandlerAnswer readHandler = new SingleThreadedReadHandlerAnswer(pendingData);
    private final WriteHandlerAnswer writeHandler = new WriteHandlerAnswer(new PendingMessages(pendingData));

    public final void handle(Selector selector) throws IOException {
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
        } catch (Exception ex) {
            // workshops
        }
    }
}
