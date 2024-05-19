package site.strangebros.nork.domain.keyword.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import site.strangebros.nork.domain.keyword.entity.Keyword;

@Mapper
public interface KeywordMapper {
    List<Keyword> findAll();
}
