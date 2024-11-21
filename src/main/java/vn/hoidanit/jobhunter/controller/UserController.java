package vn.hoidanit.jobhunter.controller;

import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.service.UserService;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public User creatNewUser(
            @RequestBody User postUser) {

        User hieuUser = this.userService.handleCreatUser(postUser);

        return hieuUser;
    }

    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        this.userService.handleDeleteUser(id);
        return "Xoa thanh cong";
    }

    @GetMapping("/user/{id}")
    public User fetchUserById(@PathVariable("id") long id) {
        return this.userService.fetchUserById(id);
    }

    @GetMapping("/user")
    public List<User> getAllUser() {
        return this.userService.fetchAllUser();
    }

    @PutMapping("/user")
    public User updateUser(@RequestBody User putUser) {
        return this.userService.handlUpdatUser(putUser);

    }
}
