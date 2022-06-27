package nextstep.subway.path;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.subway.AcceptanceTest;
import nextstep.subway.auth.dto.TokenRequest;
import nextstep.subway.auth.dto.TokenResponse;
import nextstep.subway.line.dto.LineRequest;
import nextstep.subway.line.dto.LineResponse;
import nextstep.subway.station.StationAcceptanceTest;
import nextstep.subway.station.dto.StationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;

import static nextstep.subway.auth.acceptance.AuthAcceptanceFactory.로그인_요청시도;
import static nextstep.subway.line.acceptance.LineAcceptanceTest.지하철_노선_등록되어_있음;
import static nextstep.subway.line.acceptance.LineSectionAcceptanceTest.지하철_노선에_지하철역_등록되어_있음;
import static nextstep.subway.member.MemberAcceptanceFactory.회원_생성을_요청;
import static nextstep.subway.path.PathAcceptanceFactory.이용요금_조회;
import static nextstep.subway.path.PathAcceptanceFactory.최단_경로_조회_요청;
import static nextstep.subway.path.PathAcceptanceFactory.최단_경로_조회_조회_실패;
import static nextstep.subway.path.PathAcceptanceFactory.최단_경로_조회_조회됨;
import static nextstep.subway.path.PathAcceptanceFactory.회원의_최단경로_조회_요청;


@DisplayName("지하철 경로 조회")
public class PathFinderAcceptanceTest extends AcceptanceTest {
    private LineResponse 신분당선;
    private LineResponse 이호선;
    private LineResponse 삼호선;
    private LineResponse 신규노선;
    private StationResponse 강남역;
    private StationResponse 양재역;
    private StationResponse 교대역;
    private StationResponse 남부터미널역;
    private StationResponse 신규상행역;
    private StationResponse 신규하행역;
    private StationResponse 존재하지않는역;

    /**
     * 교대역    --- *2호선 10* ---   강남역
     * |                        |
     * *3호선 3*                   *신분당선 10*
     * |                        |
     * 남부터미널역  --- *3호선 2* ---   양재
     */
    @BeforeEach
    public void setUp() {
        super.setUp();

        강남역 = StationAcceptanceTest.지하철역_등록되어_있음("강남역").as(StationResponse.class);
        양재역 = StationAcceptanceTest.지하철역_등록되어_있음("양재역").as(StationResponse.class);
        교대역 = StationAcceptanceTest.지하철역_등록되어_있음("교대역").as(StationResponse.class);
        남부터미널역 = StationAcceptanceTest.지하철역_등록되어_있음("남부터미널역").as(StationResponse.class);
        신규상행역 = StationAcceptanceTest.지하철역_등록되어_있음("신규상행역").as(StationResponse.class);
        신규하행역 = StationAcceptanceTest.지하철역_등록되어_있음("신규하행역").as(StationResponse.class);
        존재하지않는역 = new StationResponse(100L, "존재하지않는역", LocalDateTime.now(), LocalDateTime.now());

        신분당선 = 지하철_노선_등록되어_있음(LineRequest.of("신분당선", "bg-red-600", 강남역.getId(), 양재역.getId(), 10)).as(LineResponse.class);
        이호선 = 지하철_노선_등록되어_있음(LineRequest.of("이호선", "bg-red-600", 교대역.getId(), 강남역.getId(), 10)).as(LineResponse.class);
        삼호선 = 지하철_노선_등록되어_있음(LineRequest.of("삼호선", "bg-red-600", 교대역.getId(), 양재역.getId(), 5)).as(LineResponse.class);
        신규노선 = 지하철_노선_등록되어_있음(LineRequest.of("신규호선", "bg-red-600", 신규상행역.getId(), 신규하행역.getId(), 5)).as(LineResponse.class);

        지하철_노선에_지하철역_등록되어_있음(삼호선, 교대역, 남부터미널역, 3);
    }

    @Test
    void 최단_경로_조회_성공() {
        ExtractableResponse<Response> response = 최단_경로_조회_요청(교대역.getId(), 양재역.getId());

        최단_경로_조회_조회됨(response, 5);
    }

    @Test
    void 출발지와_도착지가_같아서_조회실패() {
        ExtractableResponse<Response> response = 최단_경로_조회_요청(교대역.getId(), 교대역.getId());

        최단_경로_조회_조회_실패(response);
    }

    @Test
    void 출발지와_도착지_연결되지않아_조회실패() {
        ExtractableResponse<Response> response = 최단_경로_조회_요청(교대역.getId(), 신규상행역.getId());

        최단_경로_조회_조회_실패(response);
    }

    @Test
    void 출발지나_도착지가_존재하지않아_조회실패() {
        ExtractableResponse<Response> 출발지_없음 = 최단_경로_조회_요청(존재하지않는역.getId(), 교대역.getId());
        ExtractableResponse<Response> 도착지_없음 = 최단_경로_조회_요청(교대역.getId(), 존재하지않는역.getId());

        최단_경로_조회_조회_실패(출발지_없음);
        최단_경로_조회_조회_실패(도착지_없음);
    }

    @ParameterizedTest
    @CsvSource(value = {"10:800", "15:1070", "20:1250"}, delimiter = ':')
    void 로그인유저가_최단경로를_조회하면_나이에맞는_할인된요금으로_조회된다(int age, int fare) {
//        given
        String email = "child@email.com";
        String password = "1234";
        ExtractableResponse<Response> 회원생성 = 회원_생성을_요청(email, password, age);
        TokenResponse 유저_토큰 = 로그인_요청시도(new TokenRequest(email, password)).as(TokenResponse.class);

//        when
        ExtractableResponse<Response> 어린이회원_최단거리조회 = 회원의_최단경로_조회_요청(교대역.getId(), 양재역.getId(), 유저_토큰.getAccessToken());

//        then
        최단_경로_조회_조회됨(어린이회원_최단거리조회, 5);
        이용요금_조회(어린이회원_최단거리조회, fare);
    }
}
