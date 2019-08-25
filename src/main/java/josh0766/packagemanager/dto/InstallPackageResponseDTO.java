package josh0766.packagemanager.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InstallPackageResponseDTO {
    @JsonProperty("result_code")
    private int resultCode;

    @JsonProperty("package_name")
    private String packageName;

    @JsonProperty("package_version")
    private String packageVersion;
}
