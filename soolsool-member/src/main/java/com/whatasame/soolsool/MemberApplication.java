package com.whatasame.soolsool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class MemberApplication {

    public static void main(final String[] args) {
        SpringApplication.run(MemberApplication.class, args);
    }
}
