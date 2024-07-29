package animealth.animealthbackend.api.user.service;

import animealth.animealthbackend.api.user.dto.UserDTO;
import animealth.animealthbackend.domain.user.User;
import animealth.animealthbackend.domain.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOS = users.stream()
                        .map(UserDTO::from)
                        .toList();
        return userDTOS;
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User가 존재하지 않습니다.")
        );

        return UserDTO.from(user);
    }

    public UserDTO update(Long userId, UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user = user.update(userDTO.getName(), userDTO.getPhone(), userDTO.getNickname());

            return UserDTO.from(user);
        }else{
            throw new IllegalArgumentException("해당 아이디를 가진 user가 없습니다: "+userId);
        }
    }

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
