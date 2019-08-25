package josh0766.monitoring.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SystemInformationDTO {
    @JsonProperty("cpu_usage")
    private int cpuUsage;

    @JsonProperty("memory_usage")
    private int memoryUsage;

    @JsonProperty("process_list")
    private List<String> processList = new ArrayList<>();
}
