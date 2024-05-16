package site.strangebros.nork.domain.member.mapper;

import org.apache.ibatis.annotations.Mapper;
import site.strangebros.nork.domain.member.entity.Member;
import site.strangebros.nork.domain.member.service.dto.request.SignUpRequest;

@Mapper
public interface MemberMapper {
    // 회원가입
    int signUp(Member signUpInfo);

    Member findByEmail(String email);

    Member findByNickname(String nickname);

    // Member findByToken(Integer id); // 유저 정보 조회를 위한 임시 메소드


}
