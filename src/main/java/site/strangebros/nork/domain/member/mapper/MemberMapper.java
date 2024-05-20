package site.strangebros.nork.domain.member.mapper;

import org.apache.ibatis.annotations.Mapper;
import site.strangebros.nork.domain.member.entity.Member;
import site.strangebros.nork.domain.member.service.dto.request.SignUpRequest;

@Mapper
public interface MemberMapper {
    // 회원가입
    int signUp(Member signUpInfo);

    // 이메일로 찾기 (로그인, 회원가입 이메일 중복확인)
    Member findByEmail(String email);

    // 닉네임으로 찾기 (회원가입 닉네임 중복확인)
    Member findByNickname(String nickname);

    Member findById(int memberId);

    // Member findByToken(Integer id); // 유저 정보 조회를 위한 임시 메소드

    Member findById(int id);

}
