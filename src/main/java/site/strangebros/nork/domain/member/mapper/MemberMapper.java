package site.strangebros.nork.domain.member.mapper;

import org.apache.ibatis.annotations.Mapper;
import site.strangebros.nork.domain.member.entity.Member;

@Mapper
public interface MemberMapper {
    Member findById(String id);
    //    Member findByToken(Integer id); // 유저 정보 조회를 위한 임시 메소드
}
