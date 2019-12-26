package br.com.lemos.lemosfood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import br.com.lemos.lemosfood.LemosfoodApiApplication;
import br.com.lemos.lemosfood.domain.model.Cozinha;
import br.com.lemos.lemosfood.domain.repository.CozinhaRepository;

public class AlteracaoCozinhaMain {

	public static void main(String[] args) {
		
		ApplicationContext applicationContext =  new SpringApplicationBuilder(LemosfoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);
		
		CozinhaRepository cozinhaRepo = applicationContext.getBean(CozinhaRepository.class);
		
		Cozinha cozinha = new Cozinha();
		cozinha.setId(1L);
		cozinha.setNome("Brasileira");
		
		cozinha = cozinhaRepo.save(cozinha);
		System.out.printf("%d - %s\n", cozinha.getId(), cozinha.getNome());
	}
}
