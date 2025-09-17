package monitor.lab.cana_fire.service;

import lombok.RequiredArgsConstructor;
import monitor.lab.cana_fire.domain.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Locale;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    public void notify(Alert a) {
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo("celiojuniorata@gmail.com");
        mensagem.setSubject("🔥 Alerta de Incêndio Detectado!");
        mensagem.setText(buildMessage(a));
        mensagem.setFrom("celiojuniorata@gmail.com");
        mensagem.setFrom("aanacarla.vieira@gmail.com");
        mensagem.setFrom("juniorvieira04055@gmail.com");

        mailSender.send(mensagem);
        log.info("E-mail enviado com sucesso! ",
                a.getLat(), a.getLon());
    }

    private String buildMessage(Alert a) {
        return String.format(Locale.US,
                """
                🚨 Atenção!
        
                Um foco de calor foi detectado nas coordenadas:
                📍 Latitude: %.4f
                📍 Longitude: %.4f
                📅 Data: %s
        
                🔗 Visualize no mapa:
                https://maps.google.com/?q=%.6f,%.6f
        
                FIQUE ATENTO E TOME AS MEDIDAS NECESSÁRIAS!
                """,
                a.getLat(), a.getLon(), a.getDate(), a.getLat(), a.getLon());
    }

}
