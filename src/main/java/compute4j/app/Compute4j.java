package compute4j.app;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


@SpringBootApplication
public class Compute4j {

    public static void main(String[] args) throws Exception {

        new SpringApplicationBuilder(Compute4j.class).web(WebApplicationType.NONE).run(args);
    }
}
