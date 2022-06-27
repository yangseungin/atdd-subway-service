package nextstep.subway.line.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Distance {
    private static final int MIN = 0;

    @Column
    private final int distance;

    protected Distance() {
        distance = MIN;
    }

    protected Distance(int distance) {
        if (distance < MIN) {
            throw new IllegalArgumentException("거리는 0보다 작을 수 없습니다.");
        }
        this.distance = distance;
    }

    public static Distance from(int distance) {
        return new Distance(distance);
    }

    public int getDistance() {
        return distance;
    }

    public Distance minus(Distance distance) {
        if (this.distance - distance.getDistance() <= MIN) {
            throw new IllegalArgumentException("역과 역 사이의 거리보다 좁은 거리를 입력해주세요");
        }
        return Distance.from(this.distance - distance.getDistance());
    }

    public Distance plus(Distance distance) {
        return Distance.from(this.distance + distance.getDistance());
    }

    public boolean isLessThan(Distance targetDistance) {
        return distance - targetDistance.distance <= MIN;

    }
}
