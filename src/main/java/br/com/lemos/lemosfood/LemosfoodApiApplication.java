package br.com.lemos.lemosfood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import br.com.lemos.lemosfood.infrastructure.repository.CustomJpaRepositoryImpl;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class LemosfoodApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LemosfoodApiApplication.class, args);
	}

}
