package com.fenixinfotech.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App {

    Logger logger = LoggerFactory.getLogger(App.class);

    @Autowired
    CamelContext camelContext;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext applicationContext) {

        return args -> {

            camelContext.addRoutes(new RouteBuilder() {

                @Override
                public void configure() throws Exception {
                    logger.info("bootstrapping camel app");
                    from("file:src/test/routes/data?noop=true")
                            .log(LoggingLevel.INFO, "firing route")
                            .to("file:target/test/routes/output");
                }
            });
        };
    }
}
