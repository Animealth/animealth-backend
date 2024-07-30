package animealth.animealthbackend.api.user.service;

import animealth.animealthbackend.api.user.dto.UserDTO;

import java.util.List;

public interface UserService {

    /**
     * 유저 리스트 조회
     */
    List<UserDTO> findAll();

    /**
     * 유저 아이디로 찾기
     */
    UserDTO findById(Long id);

    /**
     * 유저 업데이트
     */
    UserDTO update(Long userId, UserDTO userDTO);

    /**
     * 유저 삭제
     */
    UserDTO delete(Long id);
}
