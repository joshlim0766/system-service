package josh0766.packagemanager.repository;

import josh0766.packagemanager.entity.InstalledPackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstalledPackageRepository extends JpaRepository<InstalledPackage, Integer> {
    Optional<InstalledPackage> findFirstByPackageName (String packageName);
}
