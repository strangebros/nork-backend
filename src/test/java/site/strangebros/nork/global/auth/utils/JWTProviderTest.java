package site.strangebros.nork.global.auth.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import site.strangebros.nork.domain.member.entity.MemberRole;
import site.strangebros.nork.global.auth.dto.MemberAuthority;
import site.strangebros.nork.global.auth.exception.InvalidJWTException;

@SpringBootTest
class JWTProviderTest {

    @MockBean
    private Clock clock;

    @Autowired
    private JWTProvider jwtProvider;

    @Test
    void 정상_token을_파싱하면_memberId가_반환된다() {
        // given
        doNotMockClock();

        // when
        String accessToken = jwtProvider.buildAccessToken(
                MemberAuthority.builder().id(1).role(MemberRole.MEMBER).build()
        );

        // then
        assertThat(jwtProvider.parseAccessToken(accessToken).getId()).isEqualTo(1);
    }

    @Test
    void 생성_후_30분이_지난_token_전달시_예외가_발생한다() {
        // given
        Instant now = LocalDateTime.now().toInstant(ZoneOffset.UTC);
        mockClockTo(now.minusSeconds(60 * 30).toString());

        // when
        String accessToken = jwtProvider.buildAccessToken(
                MemberAuthority.builder().id(1).role(MemberRole.MEMBER).build()
        );
        doNotMockClock();

        // then
        assertThatThrownBy(() -> jwtProvider.parseAccessToken(accessToken))
                .isInstanceOf(InvalidJWTException.class);
    }

    @Test
    void 잘못된_token_전달시_예외가_발생한다() {
        // given
        doNotMockClock();

        // when
        String accessToken = jwtProvider.buildAccessToken(
                MemberAuthority.builder().id(1).role(MemberRole.MEMBER).build()
        );
        String invalidAccessToken = "b" + accessToken + "a";

        // then
        assertThatThrownBy(() -> jwtProvider.parseAccessToken(invalidAccessToken))
                .isInstanceOf(InvalidJWTException.class);
    }

    /**
     * Clock 빈이 실제 시간을 반환하도록 한다.
     */
    private void doNotMockClock() {
        mockClockTo(LocalDateTime.now().toInstant(ZoneOffset.UTC).toString());
    }

    /**
     * 주어진 시간으로 Clock 빈을 mocking 한다.
     * @param dateTime mocking을 원하는 시간을 <b>ISO-8601 형식</b>으로 전달한다.
     */
    private void mockClockTo(String dateTime) {
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());
        when(clock.instant()).thenReturn(Instant.parse(dateTime));
    }
}
