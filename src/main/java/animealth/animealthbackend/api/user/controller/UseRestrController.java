package animealth.animealthbackend.api.user.controller;

import animealth.animealthbackend.api.user.dto.UserDTO;
import animealth.animealthbackend.api.user.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/user")
@RestController
public class UseRestrController {

    private final UserServiceImpl userServiceImpl;

    @Autowired
    public UseRestrController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping(value = "/find/all", produces = "application/json; charset=UTF-8")
    public List<UserDTO> findAll() {
        return userServiceImpl.findAll();
    }

    @GetMapping(value = "/find/{id}", produces = "application/json; charset=UTF-8")
    public UserDTO findById(@PathVariable Long id) {
        return userServiceImpl.findById(id);
    }

    @PutMapping(value = "/update/{id}", produces = "application/json; charset=UTF-8")
    public UserDTO update(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return userServiceImpl.update(id, userDTO);
    }

    @DeleteMapping(value = "/delete/{id}", produces = "application/json; charset=UTF-8")
    public UserDTO delete(@PathVariable Long id) {
        return userServiceImpl.delete(id);
    }
}
