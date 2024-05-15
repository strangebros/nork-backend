package site.strangebros.nork.domain.member.mapper;

import org.apache.ibatis.annotations.Mapper;
import site.strangebros.nork.domain.member.entity.Member;

@Mapper
public interface MemberMapper {
    Member findById(String id);
}
