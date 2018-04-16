package compute4j.app.event;

import org.springframework.amqp.core.Message;


public class RegisterComputeEvent {

    private RegisterComputeEvent() {

        // OK
    }

    public static RegisterComputeEvent valueOf(Message message) {

        return new RegisterComputeEvent();
    }
}
