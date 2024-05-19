package site.strangebros.nork.domain.reservation.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import site.strangebros.nork.domain.reservation.entity.Reservation;

import java.util.List;

@Mapper
public interface ReservationMapper {
    // 작업 예약 생성
    int create(Reservation createInfo);

    // 유저의 모든 예약 조회(모든 워크스페이스에 대하여)
    List<Reservation> findAllByMemberId(int memberId);

    // 유저의 상위 3개 예약 조회(단일 워크스페이스에 대하여, 현재보다 이후 날짜)
    List<Reservation> findByMemberIdAndWorkspaceId(@Param("memberId") int memberId, @Param("workspaceId") int workspaceId);

    // 유저의 상위 3개 예약 조회(모든 워크스페이스에 대하여, 현재보다 이후 날짜)
    List<Reservation> findByMemberId(int memberId);

    // 작업 예약 삭제
    void deleteByReservationId(int reservationId);
}
