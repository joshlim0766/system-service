package josh0766.packagemanager.entity;

import josh0766.packagemanager.dto.InstalledPackageDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "installed_package")
@Data
@NoArgsConstructor
public class InstalledPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "uid")
    private int uid;

    @Column(name = "package_name")
    private String packageName;

    @Column(name = "package_version")
    private String packageVersion;

    @Column(name = "install_script")
    private String installScript;

    @Column(name = "uninstall_script")
    private String uninstallScript;

    @Column(name = "start_script")
    private String startScript;

    @Column(name = "stop_script")
    private String stopScript;

    public InstalledPackageDTO toDTO () {
        InstalledPackageDTO installedPackageDTO = new InstalledPackageDTO();

        installedPackageDTO.setUid(uid);
        installedPackageDTO.setPackageName(packageName);
        installedPackageDTO.setPackageVersion(packageVersion);

        return installedPackageDTO;
    }
}
