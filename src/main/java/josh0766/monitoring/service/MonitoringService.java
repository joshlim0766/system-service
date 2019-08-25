package josh0766.monitoring.service;

import josh0766.monitoring.dto.SystemInformationDTO;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

public interface MonitoringService {
    SystemInformationDTO getSystemInformation () throws IOException, InterruptedException;

    SseEmitter getSystemInformationStream ();
}
