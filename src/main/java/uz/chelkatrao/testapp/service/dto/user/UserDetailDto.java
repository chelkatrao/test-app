package uz.chelkatrao.testapp.service.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import uz.chelkatrao.testapp.service.dto.base.DTO;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailDto implements DTO {
    private String login;
    private String password;
    private Set<SimpleGrantedAuthority> authentications;
}
