package nextstep.subway.line.domain;

import nextstep.subway.station.domain.Station;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SectionRepository extends JpaRepository<Section, Long> {

    List<Section> findByUpStationOrDownStation(Station upStation, Station downStation);
}