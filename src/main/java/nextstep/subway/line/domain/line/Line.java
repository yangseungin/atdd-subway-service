package nextstep.subway.line.domain.line;

import nextstep.subway.BaseEntity;
import nextstep.subway.exception.LineException;
import nextstep.subway.exception.error.ErrorCode;
import nextstep.subway.line.domain.section.Section;
import nextstep.subway.line.domain.section.Sections;
import nextstep.subway.station.domain.Station;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

import static nextstep.subway.utils.ValidationUtils.isEmpty;

@Entity
public class Line extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private String color;

    private BigDecimal plusFare;

    @Embedded
    private Sections sections = new Sections();

    public Line() {
    }

    public Line(String name, String color) {
        validation(name, color);
        this.name = name;
        this.color = color;
    }

    public Line(String name, String color, BigDecimal fare) {
        this(name, color);
        this.plusFare = fare == null ? BigDecimal.ZERO : fare;
    }

    public Line(String name, String color, Station upStation, Station downStation, int distance) {
        this(name, color, BigDecimal.ZERO);
        sections.addLineStation(new Section(this, upStation, downStation, distance));
    }

    public Line(String name, String color, Station upStation, Station downStation, int distance, BigDecimal plusFare) {
        this(name, color, plusFare);
        sections.addLineStation(new Section(this, upStation, downStation, distance));
    }

    public void update(Line line) {
        this.name = line.getName();
        this.color = line.getColor();
    }

    private void validation(String name, String color) {
        if (isEmpty(name)) {
            throw new LineException(ErrorCode.BAD_ARGUMENT, "노선의 이름이 없습니다.");
        }
        if (isEmpty(color)) {
            throw new LineException(ErrorCode.BAD_ARGUMENT, "노선의 색상이 없습니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public List<Section> getSections() {
        return sections.getSections();
    }

    public List<Station> getStations() {
        return sections.getStations();
    }

    public BigDecimal getPlusFare() {
        return plusFare;
    }

    public void addSection(Station upStation, Station downStation, int distance) {
        sections.addLineStation(new Section(this, upStation, downStation, distance));
    }

    public void removeSection(Station station) {
        sections.removeLineStation(this, station);
    }

}
