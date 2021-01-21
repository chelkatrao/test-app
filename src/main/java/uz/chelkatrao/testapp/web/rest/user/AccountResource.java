package uz.chelkatrao.testapp.web.rest.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import uz.chelkatrao.testapp.domain.auth.User;
import uz.chelkatrao.testapp.repository.auth.UserRepository;
import uz.chelkatrao.testapp.security.SecurityUtils;
import uz.chelkatrao.testapp.service.UserService;
import uz.chelkatrao.testapp.service.dto.user.PasswordChangeDTO;
import uz.chelkatrao.testapp.service.dto.user.UserDTO;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/account")
public class AccountResource {

    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public AccountResource(UserService userService,
                           UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    /**
     * @return Get Current Account
     */
    @GetMapping
    public UserDTO getAccount() {
        return userService.getUserWithAuthorities()
                .map(UserDTO::new)
                .orElseThrow(RuntimeException::new);
    }

    /**
     * {@code POST  /account} : update the current user information.
     */
    @PostMapping("/account")
    public void saveAccount(@Valid @RequestBody UserDTO userDTO) {
        String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(RuntimeException::new);
        Optional<User> user = userRepository.findOneByLogin(userLogin);
        if (!user.isPresent()) {
            throw new RuntimeException("User could not be found");
        }
        userService.updateUser(userDTO);
    }


    /**
     * @param passwordChangeDto change password using current password
     */
    @PostMapping(path = "/change-password")
    public void changePassword(@RequestBody PasswordChangeDTO passwordChangeDto) {
        if (!checkPasswordLength(passwordChangeDto.getNewPassword())) {
            throw new RuntimeException("invalid password");
        }
        userService.changePassword(passwordChangeDto.getCurrentPassword(), passwordChangeDto.getNewPassword());
    }

    /**
     * checks password is valid
     */
    private static boolean checkPasswordLength(String password) {
        return !StringUtils.isEmpty(password) &&
                password.length() >= 4 &&
                password.length() <= 100;
    }

}
