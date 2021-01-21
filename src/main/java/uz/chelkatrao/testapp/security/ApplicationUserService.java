package uz.chelkatrao.testapp.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.chelkatrao.testapp.service.UserService;
import uz.chelkatrao.testapp.service.dto.user.UserDetailDto;

@Service
public class ApplicationUserService implements UserDetailsService {

    private UserService userService;

    @Autowired
    public ApplicationUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetailDto userDetailDto = userService.getUserByUsername(username);
        return ApplicationUser.builder()
                .password(userDetailDto.getPassword())
                .username(userDetailDto.getLogin())
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .grantedAuthorities(userDetailDto.getAuthentications())
                .isEnabled(true)
                .build();
    }

}
