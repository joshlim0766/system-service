package josh0766.packagemanager.controller;

import josh0766.packagemanager.service.PackageManagerService;
import josh0766.packagemanager.dto.InstallPackageRequestDTO;
import josh0766.packagemanager.dto.InstallPackageResponseDTO;
import josh0766.packagemanager.dto.InstalledPackageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/package")
public class PackageManagerController {

    @Autowired
    private PackageManagerService packageManagerService;

    @PostMapping(
            value = "/upload"
    )
    public void uploadPackage (@RequestPart MultipartFile packageFile) throws IOException {
        packageManagerService.uploadPackage(packageFile);
    }

    @PostMapping(
            value = "/install",
            produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
            consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }
    )
    public InstallPackageResponseDTO installPackage (@RequestBody InstallPackageRequestDTO installPackageRequestDTO) {
        return packageManagerService.installPackage(installPackageRequestDTO);
    }

    @DeleteMapping(
            value = "/{packageName}"
    )
    public void uninstallPackage (@PathVariable String packageName) {
    }

    @GetMapping(
            produces = { MediaType.APPLICATION_JSON_UTF8_VALUE }
    )
    public List<InstalledPackageDTO> getInstalledPackages () {
        return packageManagerService.getInstalledPackage();
    }

    @GetMapping(
            value = "/{packageName}",
            produces = { MediaType.APPLICATION_JSON_UTF8_VALUE }
    )
    public InstalledPackageDTO getInstalledPackage (@PathVariable String packageName) {
        return packageManagerService.getInstalledPackage(packageName);
    }
}
