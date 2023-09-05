package dsi.esprit.tn.repository;

import dsi.esprit.tn.models.ERole;
import dsi.esprit.tn.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
