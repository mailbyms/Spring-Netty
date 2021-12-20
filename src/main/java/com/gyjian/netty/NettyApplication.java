package com.gyjian.netty;

import com.gyjian.netty.server.NettyServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class NettyApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(NettyApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		NettyServer nettyServer = new NettyServer(7000);
		nettyServer.start();
	}

}