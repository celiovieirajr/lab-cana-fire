package monitor.lab.cana_fire.service;

import lombok.RequiredArgsConstructor;
import monitor.lab.cana_fire.domain.Alert;
import monitor.lab.cana_fire.dto.AlertResponseDto;
import monitor.lab.cana_fire.utils.AlertMessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

    public void notify(AlertResponseDto a) {
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo("lucasemanuelc.rodrigue@gmail.com", "celiojuniorata@gmail.com");
        mensagem.setSubject("🔥 Alerta de Incêndio Detectado!");
        mensagem.setText(AlertMessageBuilder.buildEmailMessage(a));
        mensagem.setFrom("monitorcanafire@gmail.com");

        mailSender.send(mensagem);
    }
}
