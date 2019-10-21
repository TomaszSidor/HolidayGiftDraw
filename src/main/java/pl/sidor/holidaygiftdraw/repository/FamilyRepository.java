package pl.sidor.holidaygiftdraw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sidor.holidaygiftdraw.model.Family;

public interface FamilyRepository extends JpaRepository <Family, Long> {
}
