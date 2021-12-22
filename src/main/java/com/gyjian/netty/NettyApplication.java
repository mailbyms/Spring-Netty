package com.gyjian.netty;

import com.gyjian.netty.server.NettyServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class NettyApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(NettyApplication.class, args);
		//new SpringApplicationBuilder().sources(NettyApplication.class).web(WebApplicationType.NONE).run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		NettyServer nettyServer = new NettyServer(7000);
		nettyServer.start();
	}

}
