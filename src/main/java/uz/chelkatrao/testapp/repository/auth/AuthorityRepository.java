package uz.chelkatrao.testapp.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.chelkatrao.testapp.domain.auth.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
