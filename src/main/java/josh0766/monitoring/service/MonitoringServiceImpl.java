package josh0766.monitoring.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import josh0766.dto.ExternalCommandExecutionResultDTO;
import josh0766.exception.ExternalCommmandExecutionException;
import josh0766.monitoring.dto.SystemInformationDTO;
import josh0766.utility.ExternalCommandExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

@Slf4j
@Service
public class MonitoringServiceImpl implements MonitoringService {

    @Value("${system-service.monitoring.system-information-script}")
    private String systemInformationScript;

    @Autowired
    private ExternalCommandExecutor externalCommandExecutor;

    @Autowired
    private ExecutorService fixedThreadExecutor;

    public SystemInformationDTO getSystemInformation () throws IOException, InterruptedException {
        ExternalCommandExecutionResultDTO result =
                externalCommandExecutor.execute(systemInformationScript, new String[] {});

        if (result.getExitCode() != 0) {
            throw new ExternalCommmandExecutionException(result.getExecutionResult());
        }

        ObjectMapper mapper = new ObjectMapper();

        SystemInformationDTO systemInformation =
                mapper.readValue(result.getExecutionResult(), SystemInformationDTO.class);

        return systemInformation;
    }

    public SseEmitter getSystemInformationStream () {
        SseEmitter sseEmitter = new SseEmitter();

        fixedThreadExecutor.execute(() -> {
            try {
                while (true) {
                    SystemInformationDTO systemInformationDTO = getSystemInformation();

                    SseEmitter.SseEventBuilder event = SseEmitter.event()
                            .data(systemInformationDTO);
                    sseEmitter.send(event);

                    Thread.sleep(1000);
                }
            }
            catch (Exception e) {
                sseEmitter.completeWithError(e);
            }
        });

        return sseEmitter;
    }
}
