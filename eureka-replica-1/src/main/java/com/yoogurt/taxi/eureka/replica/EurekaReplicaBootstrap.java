package com.yoogurt.taxi.eureka.replica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurekaReplicaBootstrap {

	public static void main(String[] args) {
		SpringApplication.run(EurekaReplicaBootstrap.class, args);
	}
}
