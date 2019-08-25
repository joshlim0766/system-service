package josh0766.system;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/system")
public class SystemController {
    @GetMapping(
            value = "/health",
            produces = { MediaType.APPLICATION_JSON_UTF8_VALUE }
    )
    public String checkHealth () {
        return "1";
    }
}
