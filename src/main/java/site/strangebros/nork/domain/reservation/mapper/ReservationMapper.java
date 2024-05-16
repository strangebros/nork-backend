package site.strangebros.nork.domain.reservation.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import site.strangebros.nork.domain.reservation.entity.Reservation;

@Mapper
public interface ReservationMapper {
    // 작업 예약 생성
    int create(Reservation createInfo);

    Reservation findByMemberIdAndWorkspaceId(@Param("memberId") int memberId, @Param("workspaceId") int workspaceId);
}
