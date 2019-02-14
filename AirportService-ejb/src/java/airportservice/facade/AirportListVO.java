package airportservice.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Airport list Value Object
 */
@XmlRootElement(name = "AirportListVO")
public class AirportListVO {
    private static final long serialVersionUID = 1L;

    private List<AirportVO> airportList;

    public AirportListVO() {
        airportList = new ArrayList();
    }

    public AirportListVO(List<AirportVO> airportList) {
        this.airportList = airportList;
    }

    public List<AirportVO> getAirportList() {
        return airportList;
    }

    public void setAirportList(List<AirportVO> airportList) {
        this.airportList = airportList;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.airportList);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AirportListVO other = (AirportListVO) obj;
        if (!Objects.equals(this.airportList, other.airportList)) {
            return false;
        }
        return true;
    }
}
