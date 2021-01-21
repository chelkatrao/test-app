package uz.chelkatrao.testapp.web.rest.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.chelkatrao.testapp.domain.auth.User;
import uz.chelkatrao.testapp.service.UserService;
import uz.chelkatrao.testapp.service.dto.user.UserDTO;

@RestController
@RequestMapping("/api/user")
public class UserResource {

    private final UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    /**
     * @param userDTO Creating User
     * @return Create User
     */
    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(this.userService.createUser(userDTO));
    }

    /**
     * @param userDTO Updating User
     * @return Updated User
     */
    @PutMapping("/update")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(this.userService.updateUser(userDTO).orElseThrow(RuntimeException::new));
    }

    /**
     * @param id deleting User id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable Long id) {
        this.userService.deleteUser(id);
    }

    /**
     * @param pageable Pagination model
     * @return Paging UserDTO model
     */
    @GetMapping("/list")
    public ResponseEntity<Page<UserDTO>> getAllUsers(Pageable pageable) {
        return ResponseEntity.ok(this.userService.listUsers(pageable));
    }

    /**
     * @param id User id
     * @return UserDTO by user id
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(this.userService.getUser(id).orElseThrow(RuntimeException::new));
    }

}
