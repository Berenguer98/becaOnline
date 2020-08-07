package es.eoi.mundobancario;

import java.util.Scanner;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MundoBancarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(MundoBancarioApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}
}
