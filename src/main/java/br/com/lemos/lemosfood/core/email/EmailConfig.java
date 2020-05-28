package br.com.lemos.lemosfood.core.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.lemos.lemosfood.domain.service.EnvioEmailService;
import br.com.lemos.lemosfood.infrastructure.service.email.FakeEnvioEmailService;
import br.com.lemos.lemosfood.infrastructure.service.email.SandboxEnvioEmailService;
import br.com.lemos.lemosfood.infrastructure.service.email.SmtpEnvioEmailService;

@Configuration
public class EmailConfig {

    @Autowired
    private EmailProperties emailProperties;

    @Bean
    public EnvioEmailService envioEmailService() {
    	
        switch (emailProperties.getImpl()) {
            case FAKE:
                return new FakeEnvioEmailService();
            case SMTP:
                return new SmtpEnvioEmailService();
            case SANDBOX:
                return new SandboxEnvioEmailService();
            default:
                return null;
        }
    }
}       
