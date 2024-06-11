package animealth.animealthbackend.api.service;

import animealth.animealthbackend.api.dto.UserDTO;
import animealth.animealthbackend.domain.user.User;
import animealth.animealthbackend.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOS = users.stream()
                .map(user -> UserDTO.builder()
                        .name(user.getName())
                        .email(user.getEmail())
                        .phone(user.getPhone())
                        .nickname(user.getNickname())
                        .role(user.getRole())
                        .build())
                .collect(Collectors.toList());
        return userDTOS;
    }

    public UserDTO findById(Long id) {
        Optional<User> userEntity = userRepository.findById(id);
        User user = userEntity.orElseThrow(() -> new NoSuchElementException("이런 아이디를 가진 User가 존재하지 않습니다."+id));

        UserDTO userDTO = UserDTO.builder()
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .nickname(user.getNickname())
                .role(user.getRole())
                .build();
        return userDTO;
    }


}
