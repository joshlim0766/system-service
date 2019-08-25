package josh0766.monitoring.controller;

import josh0766.monitoring.dto.SystemInformationDTO;
import josh0766.monitoring.service.MonitoringService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/v1/monitoring")
public class MonitoringController {

    @Autowired
    private MonitoringService monitoringService;

    @GetMapping(
            value = "/systeminfo",
            produces = { MediaType.APPLICATION_JSON_UTF8_VALUE }
    )
    public SystemInformationDTO getSystemInformation () throws IOException, InterruptedException {
        return monitoringService.getSystemInformation();
    }

    @GetMapping(
            value = "/systeminfo/stream",
            produces = { MediaType.TEXT_EVENT_STREAM_VALUE }
    )
    public SseEmitter getSystemInformationStream () {
        return monitoringService.getSystemInformationStream();
    }
}
