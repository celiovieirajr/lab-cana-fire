package monitor.lab.cana_fire.service;

import lombok.RequiredArgsConstructor;
import monitor.lab.cana_fire.domain.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void notify(Alert a) {
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo("celiojuniorata@gmail.com");
        mensagem.setSubject("Fire Alert!");
        mensagem.setText(String.format(
                "Hotspot at %.4f, %.4f on %s%nMap: https://maps.google.com/?q=%f,%f",
                a.getLat(), a.getLon(), a.getDate(), a.getLat(), a.getLon()));

        mensagem.setFrom("celiojuniorata@gmail.com");

        mailSender.send(mensagem);
    }
}
