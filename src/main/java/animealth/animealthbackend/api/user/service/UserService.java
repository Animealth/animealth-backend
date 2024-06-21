package animealth.animealthbackend.api.user.service;

import animealth.animealthbackend.api.user.dto.UserDTO;
import animealth.animealthbackend.domain.user.User;
import animealth.animealthbackend.domain.user.UserRepository;
import jakarta.transaction.Transactional;
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
                        .isDeleted(user.getIsDeleted())
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
                .isDeleted(user.getIsDeleted())
                .build();
        return userDTO;
    }
    @Transactional
    public UserDTO update(Long userId, UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user = user.update(userDTO.getName(), userDTO.getPhone(), userDTO.getNickname());
            userRepository.save(user);
            return UserDTO.builder()
                    .name(user.getName())
                    .email(user.getEmail())
                    .phone(user.getPhone())
                    .nickname(user.getNickname())
                    .role(user.getRole())
                    .isDeleted(user.getIsDeleted())
                    .build();
        }else{
            throw new IllegalArgumentException("해당 아이디를 가진 user가 없습니다: "+userId);
        }
    }

    @Transactional
    public UserDTO delete(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setIsDeleted(true);
            userRepository.save(user);
            return UserDTO.builder()
                    .name(user.getName())
                    .email(user.getEmail())
                    .phone(user.getPhone())
                    .nickname(user.getNickname())
                    .role(user.getRole())
                    .isDeleted(true)
                    .build();
        } else {
            throw new IllegalArgumentException("해당 아이디를 가진 user가 없습니다: " + id);
        }
    }

}
