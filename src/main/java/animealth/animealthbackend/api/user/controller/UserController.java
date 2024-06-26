package animealth.animealthbackend.api.user.controller;

import animealth.animealthbackend.api.user.dto.UserDTO;
import animealth.animealthbackend.api.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/find/all", produces = "application/json; charset=UTF-8")
    public List<UserDTO> findAll() {
        return userService.findAll();
    }

    @GetMapping(value = "/find/{id}", produces = "application/json; charset=UTF-8")
    public UserDTO findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PutMapping(value = "/update/{id}", produces = "application/json; charset=UTF-8")
    public UserDTO update(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return userService.update(id, userDTO);
    }

    @DeleteMapping(value = "/delete/{id}", produces = "application/json; charset=UTF-8")
    public UserDTO delete(@PathVariable Long id) {
        return userService.delete(id);
    }
}
