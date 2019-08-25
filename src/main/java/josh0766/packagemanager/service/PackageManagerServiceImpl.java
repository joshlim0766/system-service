package josh0766.packagemanager.service;

import josh0766.dto.ExternalCommandExecutionResultDTO;
import josh0766.exception.ContentNotFoundException;
import josh0766.packagemanager.dto.InstallPackageRequestDTO;
import josh0766.packagemanager.dto.InstallPackageResponseDTO;
import josh0766.packagemanager.dto.InstalledPackageDTO;
import josh0766.packagemanager.entity.InstalledPackage;
import josh0766.packagemanager.repository.InstalledPackageRepository;
import josh0766.utility.ExternalCommandExecutor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PackageManagerServiceImpl implements PackageManagerService {

    @Autowired
    private ExternalCommandExecutor externalCommandExecutor;

    @Autowired
    private InstalledPackageRepository installedPackageRepository;

    public void uploadPackage (MultipartFile packageFile) throws IOException {
        String packageFileName = packageFile.getOriginalFilename();

        if (packageFileName.contains("..")) {
            // handle invalid file name
        }

        if (packageFileName.endsWith(".tar.gz") == false) {
            // handle invalid file
        }

        File destFile = null;
        String destFileName = null;

        do {
            destFileName = RandomStringUtils.randomAlphanumeric(32) + ".tar.gz";
            destFile = new File(destFileName);
        } while (destFile.exists());

        destFile.getParentFile().mkdirs();
        packageFile.transferTo(destFile);
    }

    @Transactional
    public InstallPackageResponseDTO installPackage (InstallPackageRequestDTO installPackageRequestDTO) {
        InstallPackageResponseDTO response = new InstallPackageResponseDTO();

        String packageName = installPackageRequestDTO.getPackageName();
        String packageVersion = installPackageRequestDTO.getPackageVersion();
        String installScript = installPackageRequestDTO.getInstallScript();
        String uninstallScript = installPackageRequestDTO.getUninstallScript();

        if (installScript == null || installScript.isEmpty() ||
                packageName == null || packageName.isEmpty() ||
                packageVersion == null || packageVersion.isEmpty()) {
            response.setResultCode(-1);

            return response;
        }

        response.setPackageName(packageName);
        response.setPackageVersion(packageVersion);

        try {
            ExternalCommandExecutionResultDTO executionResult = externalCommandExecutor.execute(
                    installScript, new String[] {});
            response.setResultCode(executionResult.getExitCode());

            log.debug("Install log : " + executionResult.getExecutionResult());
        } catch (IOException e) {
            response.setResultCode(-1);
            e.printStackTrace();
        } catch (InterruptedException e) {
            response.setResultCode(-1);
            e.printStackTrace();
        }

        if (response.getResultCode() == 0) {
            Optional<InstalledPackage> prevInstalledPackage =
                    installedPackageRepository.findFirstByPackageName(packageName);

            InstalledPackage installedPackage = null;
            if (prevInstalledPackage.isPresent()) {
                installedPackage = prevInstalledPackage.get();

                installedPackage.setPackageVersion(packageVersion);
                installedPackage.setInstallScript(installScript);
                installedPackage.setUninstallScript(uninstallScript);
                installedPackage.setStartScript(installPackageRequestDTO.getStartScript());
                installedPackage.setStopScript(installPackageRequestDTO.getStopScript());
            }
            else {
                installedPackage = installPackageRequestDTO.toEntity();
            }

            installedPackageRepository.saveAndFlush(installedPackage);
        }

        return response;
    }

    @Transactional(readOnly = true)
    public List<InstalledPackageDTO> getInstalledPackage () {
        List<InstalledPackage> installedPackages = installedPackageRepository.findAll();

        return installedPackages.stream().map(ip -> {
            return ip.toDTO();
        }).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public InstalledPackageDTO getInstalledPackage (String packageName) {
        Optional<InstalledPackage> installedPackage =
                installedPackageRepository.findFirstByPackageName(packageName);

        if (installedPackage.isPresent() == false) {
            throw new ContentNotFoundException("Not found " + packageName);
        }

        InstalledPackageDTO result = installedPackage.get().toDTO();

        return result;
    }
}
