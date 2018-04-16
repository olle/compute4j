package compute4j.lib;

import java.io.Serializable;


public class Compute implements Serializable {

    private String routingKey;

    public String getRoutingKey() {

        return routingKey;
    }


    public void setRoutingKey(String routingKey) {

        this.routingKey = routingKey;
    }
}
