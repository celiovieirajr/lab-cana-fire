package monitor.lab.cana_fire.service;

import lombok.RequiredArgsConstructor;
import monitor.lab.cana_fire.domain.Alert;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mail;
    public void notify(Alert a) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("celiojuniorata@gmail.com");
        msg.setSubject("🔥 Sugar‑cane fire detected");
        msg.setText(String.format(
                "Hotspot at %.4f, %.4f on %s%nMap: https://maps.google.com/?q=%f,%f",
                a.getLat(), a.getLon(), a.getDate(), a.getLat(), a.getLon()));

        mail.send(msg);
        System.out.println("Email enviado com sucesso para " + msg.getTo()[0]);
    }
}
