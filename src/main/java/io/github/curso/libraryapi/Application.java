package io.github.curso.libraryapi;

import io.github.curso.libraryapi.model.Autor;
import io.github.curso.libraryapi.model.Livro;
import io.github.curso.libraryapi.repository.AutorRepository;
import io.github.curso.libraryapi.service.AutorService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.orm.hibernate5.SpringSessionContext;

import java.time.LocalDate;

@SpringBootApplication
@EnableJpaAuditing
public class Application {

	public static void main(String[] args) {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(Application.class);
		builder.run(args);
	}



}
