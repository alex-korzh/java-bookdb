package name.dezalator.bookdb.controller;

import name.dezalator.bookdb.model.User;
import name.dezalator.bookdb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/users/")
public class AdminUserController {
    private final UserService userService;

    @Autowired
    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("ban/{id}")
    public void banUser(@PathVariable("id") User user) {
        userService.banUser(user);
    }

    @PostMapping("unban/{id}")
    public void unbanUser(@PathVariable("id") User user) {
        userService.unbanUser(user);
    }
}
