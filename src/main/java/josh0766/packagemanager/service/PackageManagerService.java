package josh0766.packagemanager.service;

import josh0766.packagemanager.dto.InstallPackageRequestDTO;
import josh0766.packagemanager.dto.InstallPackageResponseDTO;
import josh0766.packagemanager.dto.InstalledPackageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PackageManagerService {

    void uploadPackage (MultipartFile packageFile) throws IOException;

    InstallPackageResponseDTO installPackage (InstallPackageRequestDTO installPackageRequestDTO);

    List<InstalledPackageDTO> getInstalledPackage ();

    InstalledPackageDTO getInstalledPackage (String packageName);
}
