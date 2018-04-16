package compute4j.app.event;

import org.springframework.amqp.core.Message;


public class PerformComputeEvent {

    private PerformComputeEvent() {

        // OK
    }

    public static PerformComputeEvent valueOf(Message message) {

        return new PerformComputeEvent();
    }
}
