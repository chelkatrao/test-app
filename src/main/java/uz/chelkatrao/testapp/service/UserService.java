package uz.chelkatrao.testapp.service;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.chelkatrao.testapp.domain.auth.Authority;
import uz.chelkatrao.testapp.domain.auth.User;
import uz.chelkatrao.testapp.repository.auth.AuthorityRepository;
import uz.chelkatrao.testapp.repository.auth.UserRepository;
import uz.chelkatrao.testapp.security.SecurityUtils;
import uz.chelkatrao.testapp.service.dto.user.UserDTO;
import uz.chelkatrao.testapp.service.dto.user.UserDetailDto;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       AuthorityRepository authorityRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * @param login login
     * @return UserDetailDto
     */
    @SneakyThrows
    public UserDetailDto getUserByUsername(String login) {

        User user = userRepository.findByLogin(login).orElseThrow(RuntimeException::new);
        Set<SimpleGrantedAuthority> authentications = new HashSet<>();
        for (Authority authority : user.getAuthorities()) {
            authentications.add(new SimpleGrantedAuthority(authority.getName()));
        }
        return new UserDetailDto(
                user.getLogin(),
                user.getPassword(),
                authentications
        );
    }

    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setActivated(true);
        user.setFirstName(userDTO.getFirstName());
        user.setLogin(userDTO.getLogin());
        user.setCreatedBy(SecurityUtils.getCurrentUserLogin().orElseThrow(RuntimeException::new));
        user.setLastModifiedBy(SecurityUtils.getCurrentUserLogin().orElseThrow(RuntimeException::new));
        user.setAuthorities(authoritiesFromStrings(userDTO.getAuthorities()));
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return userRepository.save(user);
    }

    private Set<Authority> authoritiesFromStrings(Set<String> authoritiesAsString) {
        Set<Authority> authorities = new HashSet<>();

        if (authoritiesAsString != null) {
            authorities = authoritiesAsString.stream().map(string -> {
                Authority auth = new Authority();
                auth.setName(string);
                return auth;
            }).collect(Collectors.toSet());
        }

        return authorities;
    }

    public Optional<UserDTO> updateUser(UserDTO userDTO) {
        return Optional.of(userRepository
                .findById(userDTO.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(user -> {
                    user.setLogin(userDTO.getLogin().toLowerCase());
                    user.setFirstName(userDTO.getFirstName());
                    user.setLastName(userDTO.getLastName());
                    user.setEmail(userDTO.getEmail().toLowerCase());
                    user.setImageUrl(userDTO.getImageUrl());
                    user.setActivated(userDTO.isActivated());
                    Set<Authority> managedAuthorities = user.getAuthorities();
                    managedAuthorities.clear();
                    if (userDTO.getAuthorities() != null) {
                        userDTO.getAuthorities().stream()
                                .map(authorityRepository::findById)
                                .filter(Optional::isPresent)
                                .map(Optional::get)
                                .forEach(managedAuthorities::add);
                    }
                    log.debug("Changed Information for User: {}", user);
                    return userRepository.save(user);
                })
                .map(UserDTO::new);
    }

    public void deleteUser(Long id) {
        userRepository.findById(id).ifPresent(user -> {
            userRepository.delete(user);
            log.debug("Deleted User: {}", user);
        });
    }

    public Page<UserDTO> listUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserDTO::new);
    }

    public Optional<UserDTO> getUser(Long id) {
        return userRepository.findById(id).map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesByLogin);
    }

    public void changePassword(String currentClearTextPassword, String newPassword) {
        SecurityUtils.getCurrentUserLogin()
                .flatMap(userRepository::findOneByLogin)
                .ifPresent(user -> {
                    String currentEncryptedPassword = user.getPassword();
                    if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
                        throw new RuntimeException("Invalid password");
                    }
                    String encryptedPassword = passwordEncoder.encode(newPassword);
                    user.setPassword(encryptedPassword);
                    log.debug("Changed password for User: {}", user);
                });
    }
}
