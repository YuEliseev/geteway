package org.tardi.gateway;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class GatewayApplication {
    public static void main(String... args) {
        Quarkus.run(args);
    }
}
