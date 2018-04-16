package compute4j.app.event;

import org.springframework.amqp.core.Message;


public class UnknownMessageEvent {

    private UnknownMessageEvent() {

        // OK
    }

    public static UnknownMessageEvent valueOf(Message message) {

        return new UnknownMessageEvent();
    }
}
