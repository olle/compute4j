package compute4j.lib;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

import org.springframework.stereotype.Component;

import java.util.function.Function;


public interface Compute4jClient {

    void registerCompute(String routingKey, Function<In, Out> fun);
}

@Component
class DefaultClient implements Compute4jClient {

    private final RabbitTemplate template;

    public DefaultClient(RabbitTemplate template) {

        this.template = template;
    }

    @Override
    public void registerCompute(String routingKey, Function<In, Out> fun) {

        template.convertAndSend("compute4j", routingKey, fun);
    }
}
