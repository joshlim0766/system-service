package josh0766.packagemanager.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import josh0766.packagemanager.entity.InstalledPackage;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InstallPackageRequestDTO {
    @JsonProperty("package_name")
    private String packageName;

    @JsonProperty("package_version")
    private String packageVersion;

    @JsonProperty("install_script")
    private String installScript;

    @JsonProperty("uninstall_script")
    private String uninstallScript;

    @JsonProperty("start_script")
    private String startScript;

    @JsonProperty("stop_script")
    private String stopScript;

    public InstalledPackage toEntity () {
        InstalledPackage installedPackage = new InstalledPackage();

        installedPackage.setPackageName(packageName);
        installedPackage.setPackageVersion(packageVersion);
        installedPackage.setInstallScript(installScript);
        installedPackage.setUninstallScript(uninstallScript);
        installedPackage.setStartScript(startScript);
        installedPackage.setStopScript(stopScript);

        return installedPackage;
    }
}
