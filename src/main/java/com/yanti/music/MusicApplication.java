package com.yanti.music;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages= {"com.yanti.music.ui", "com.yanti.music.config","com.yanti.music.impl","com.yanti.music.service"})
public class MusicApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicApplication.class, args);
	}

}
