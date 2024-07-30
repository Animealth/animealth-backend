package animealth.animealthbackend.api.user.controller;

import animealth.animealthbackend.api.user.dto.UserDTO;
import animealth.animealthbackend.api.user.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
@Controller
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @Autowired
    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping(value = "/find/all")
    public String findAll(Model model) {
        List<UserDTO> users = userServiceImpl.findAll();
        model.addAttribute("users", users);
        return "user/list"; // 반환할 뷰의 이름
    }

    @GetMapping(value = "/find/{id}")
    public String findById(@PathVariable Long id, Model model) {
        UserDTO user = userServiceImpl.findById(id);
        model.addAttribute("user", user);
        return "user/detail"; // 반환할 뷰의 이름
    }

    @PutMapping(value = "/update/{id}")
    public String update(@PathVariable Long id, @RequestBody UserDTO userDTO, Model model) {
        UserDTO updatedUser = userServiceImpl.update(id, userDTO);
        model.addAttribute("user", updatedUser);
        return "user/detail"; // 반환할 뷰의 이름
    }

    @DeleteMapping(value = "/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {
        UserDTO deletedUser = userServiceImpl.delete(id);
        model.addAttribute("user", deletedUser);
        return "user/detail"; // 반환할 뷰의 이름
    }
}

