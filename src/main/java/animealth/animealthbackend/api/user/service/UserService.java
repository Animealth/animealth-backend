package animealth.animealthbackend.api.user.service;

import animealth.animealthbackend.api.user.dto.UserDTO;
import animealth.animealthbackend.domain.user.User;
import animealth.animealthbackend.domain.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    /**
     * @RequiredArgsConstructor + private final UserRepository userRepository;
     * 동일한 역할을 하지만 좀더 가독성 좋을것 같아여
     */
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();
        /**
         * 변환과정이 복잡하니 UserDTO에 fromEntity 메소드( 엔티티 -> DTO 변환 ) 를 정의 하는게 좋을 것 같아요.
         */
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
        /**
         * 한줄로 줄이기 가능
         * User user = userRepository.findById(id).orElseThrow(
         *                 () -> new EntityNotFoundException("Pet Not Found")
         *         );
         */
        Optional<User> userEntity = userRepository.findById(id);
        User user = userEntity.orElseThrow(() -> new NoSuchElementException("이런 아이디를 가진 User가 존재하지 않습니다."+id));

        /**
         * 요것두 코드 중복을 피할 수 있습니다!
         */
        UserDTO userDTO = UserDTO.builder()
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .nickname(user.getNickname())
                .role(user.getRole())
                .isDeleted(user.getIsDeleted())
                .build();
        // UserDTO userDTO = UserDTO.from(user);
        return userDTO;
    }
    @Transactional
    public UserDTO update(Long userId, UserDTO userDTO) {

//        User user = userRepository.findById(userId).orElseThrow(
//                () -> new EntityNotFoundException(" Not Found")
//        );

        // 변경 허락된 값만 쓰는 DTO 만들기 (이름, 전번, 닉네임)

        // user.update(UpdateUserDTO)
        // return UserDTO.from(user)


        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user = user.update(userDTO.getName(), userDTO.getPhone(), userDTO.getNickname());
            /**
             * 벌크연산이 아니라면 save는 따로 해줄 필요 없습니다!
             * -> Transactional이 끝날때 알아서 dirty checking(변경감지) db에 반영되고
             *  이 메소드안?(트렌잭션 도중) 에서는 조회를 한 시점에서 영속성컨택스트(1차캐시)에서 관리하기 때문에
             *  리턴할때 변경된 값이 리턴됩니다
             */
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
