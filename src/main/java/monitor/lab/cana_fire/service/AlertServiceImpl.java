package monitor.lab.cana_fire.service;

import monitor.lab.cana_fire.domain.Alert;
import monitor.lab.cana_fire.domain.Hotspot;
import monitor.lab.cana_fire.dto.AlertResponseDto;
import monitor.lab.cana_fire.ingestion.AlertParserToXls;
import monitor.lab.cana_fire.mapper.AlertMapper;
import monitor.lab.cana_fire.repository.AlertRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Service
public class AlertServiceImpl implements AlertService{

    private final AlertRepository repo;
    private final EmailServiceImpl emailService;
    private final AlertMapper mapper;
    private final AlertParserToXls alertParserToXls;

    private static final Logger logger = LoggerFactory.getLogger(AlertServiceImpl.class);

    public AlertServiceImpl(AlertRepository repo,
                            EmailServiceImpl emailService,
                            AlertMapper mapper,
                            AlertParserToXls alertParserToXls) {
        this.repo = repo;
        this.emailService = emailService;
        this.mapper = mapper;
        this.alertParserToXls = alertParserToXls;
    }


    @Override
    public AlertResponseDto createAlert(Hotspot hotspot) {
        Alert alert = mapper.createAlertFromHotspot(hotspot);
        repo.save(alert);
        return mapper.toResponse(alert);
    }


    @Override
    public void sendAlertEmail(AlertResponseDto alert) {
        emailService.notify(alert);
    }


    @Override
    public AlertResponseDto updateAlert(UUID uuid, Alert alert) {
        Alert entity = repo.findById(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Alerts não existe"));

        entity.setDate(alert.getDate());
        entity.setLon(alert.getLon());
        entity.setLat(alert.getLat());

        Alert entitySaved = repo.save(entity);

        return mapper.toResponse(entitySaved);
    }


    @Override
    public void deleteAlert(UUID id) {
        Alert entity = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Alerts não existe"));
        repo.delete(entity);
    }

    @Override
    public ByteArrayInputStream exportAlerts() throws IOException {
        List<AlertResponseDto> alertResponseDtos = StreamSupport
                .stream(repo.findAll().spliterator(), false)
                .map(mapper::toResponse)
                .toList();

        logger.info("Caiu aqui");
        return alertParserToXls.exportToXls(alertResponseDtos);
    }
}
