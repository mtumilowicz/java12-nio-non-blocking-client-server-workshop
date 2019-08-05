package server.answer

import client.TestClient
import spock.lang.Specification

/**
 * Created by mtumilowicz on 2019-07-23.
 */
class ThreadPoolServerAnswerTest extends Specification {

    def expectedClientOutput = ["send: xxx", "received: xxx"]

    def "ThreadPoolServerAnswer"() {
        given:
        def port = 2

        expect:
        expectedClientOutput == extractClientOutputFor(port, new ThreadPoolServerAnswer(port))
    }
    
    def extractClientOutputFor(port, server) {
        new Thread({ server.start() }).start()
        Thread.sleep(10)
        new TestClient(port).run()
    }
}