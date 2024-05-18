package site.strangebros.nork.domain.reservation.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import site.strangebros.nork.domain.reservation.entity.Reservation;

import java.util.List;

@Mapper
public interface ReservationMapper {
    // 작업 예약 생성
    int create(Reservation createInfo);

    // 예약 조회
    List<Reservation> findByMemberIdsAndWorkspaceIds(@Param("memberId") int memberId, @Param("workspaceId") int workspaceId);
}
