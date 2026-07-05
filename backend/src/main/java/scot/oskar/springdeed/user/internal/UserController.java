package scot.oskar.springdeed.user.internal;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import scot.oskar.springdeed.user.UserService;
import scot.oskar.springdeed.user.UserView;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserView> me(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(userService.displayUser(jwt));
    }
}
