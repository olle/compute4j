package compute4j;

import compute4j.logging.Loggable;

import org.apache.commons.codec.binary.Hex;

import org.apache.logging.log4j.util.ProcessIdUtil;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

import java.util.Locale;


@SpringBootApplication
public class Compute4j {

    public static void main(String[] args) throws Exception {

        new SpringApplicationBuilder(Compute4j.class).web(WebApplicationType.NONE).run(args);
    }
}

@EnableRabbit
@Configuration
class RabbitMqConfig implements Loggable {

    private static final String COMPUTE4J = "compute4j";

    @Bean
    Exchange createCompute4jExchange() {

        return log_created(ExchangeBuilder.fanoutExchange(COMPUTE4J).autoDelete().build());
    }


    @Bean(name = "compute4jQueue")
    public Queue createCompute4jQueue() {

        return log_created(QueueBuilder.nonDurable(queueName()).autoDelete().build());
    }


    private String queueName() {

        return String.format(Locale.ENGLISH, "%s.%s.%s", COMPUTE4J, clientAddress(), processId());
    }


    private String clientAddress() {

        String result = "unknown";

        try {
            InetAddress address = InetAddress.getLocalHost();
            result = address.toString();

            NetworkInterface network = NetworkInterface.getByInetAddress(address);
            byte[] mac = network.getHardwareAddress();

            return Hex.encodeHexString(mac);
        } catch (UnknownHostException | SocketException e) {
            return result;
        }
    }


    private String processId() {

        return ProcessIdUtil.getProcessId();
    }


    @Bean
    Binding createCompute4jBinding() {

        return log_created(BindingBuilder.bind(createCompute4jQueue()).to((FanoutExchange) createCompute4jExchange()));
    }


    @RabbitListener(queues = "#{@compute4jQueue}")
    public void subscribe(Message message) {

        logger().info("||--> Received {}", message);
    }
}
