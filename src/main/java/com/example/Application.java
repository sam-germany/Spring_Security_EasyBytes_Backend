package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*
@ComponentScan("com.example.controller")   (video 4)
when this main class with @SpringBootApplication is in a totally different package and the other classes are in a totall
different package, means the root package is also not same  like here we have com.abc.example and other classe we
have in   doc.abc.example , so here we have 2 totally different package so in this case we need @CompoonenetSacn
annotation
 */
@SpringBootApplication
//@ComponentScan("com.example.controller")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
