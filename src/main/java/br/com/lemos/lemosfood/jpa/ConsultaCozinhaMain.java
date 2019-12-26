package br.com.lemos.lemosfood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import br.com.lemos.lemosfood.LemosfoodApiApplication;
import br.com.lemos.lemosfood.domain.model.Cozinha;
import br.com.lemos.lemosfood.domain.repository.CozinhaRepository;

public class ConsultaCozinhaMain {

	public static void main(String[] args) {
		
		ApplicationContext applicationContext =  new SpringApplicationBuilder(LemosfoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);
		
		CozinhaRepository cozinhaRepo = applicationContext.getBean(CozinhaRepository.class);
		
		List<Cozinha> cozinhas = cozinhaRepo.findAll();
		
		for(Cozinha c : cozinhas)
			System.out.println(c.getNome());
	}
}
