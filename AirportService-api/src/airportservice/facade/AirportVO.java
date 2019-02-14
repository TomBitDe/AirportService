package airportservice.facade;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import javax.xml.bind.annotation.*;

/**
 * Airport Value Object
 */
@XmlRootElement(name = "AirportVO")
public class AirportVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String iatacode;
    private String icaocode;
    private String descr;
    private String created;
    private String creauser;
    private String updated;
    private String updtuser;

    public AirportVO() {
    }

    public AirportVO(String iatacode) {
        this.iatacode = iatacode;
        this.icaocode = "";
        this.descr = "Unknown";
        this.created = convert2String(new Timestamp(System.currentTimeMillis()), "yyyyMMddHHmmssS");
        this.creauser = "System";
        this.updated = this.created;
        this.updtuser = "System";
    }

    public AirportVO(String iatacode, String icaocode, String descr, String creauser, String updtuser) {
        this.iatacode = iatacode;
        this.icaocode = icaocode;
        this.descr = descr;
        this.created = convert2String(new Timestamp(System.currentTimeMillis()), "yyyyMMddHHmmssS");
        this.creauser = creauser;
        this.updated = this.created;
        this.updtuser = updtuser;
    }

    public AirportVO(String iatacode, String icaocode, String descr, String created, String creauser, String updated, String updtuser) {
        this.iatacode = iatacode;
        this.icaocode = icaocode;
        this.descr = descr;
        this.created = created;
        this.creauser = creauser;
        this.updated = updated;
        this.updtuser = updtuser;
    }

    public String getIatacode() {
        return iatacode;
    }

    public void setIatacode(String iatacode) {
        this.iatacode = iatacode;
    }

    public String getIcaocode() {
        return icaocode;
    }

    public void setIcaocode(String icaocode) {
        this.icaocode = icaocode;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getCreauser() {
        return creauser;
    }

    public void setCreauser(String creauser) {
        this.creauser = creauser;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getUpdtuser() {
        return updtuser;
    }

    public void setUpdtuser(String updtuser) {
        this.updtuser = updtuser;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + Objects.hashCode(this.iatacode);
        hash = 43 * hash + Objects.hashCode(this.icaocode);
        hash = 43 * hash + Objects.hashCode(this.descr);
        hash = 43 * hash + Objects.hashCode(this.created);
        hash = 43 * hash + Objects.hashCode(this.creauser);
        hash = 43 * hash + Objects.hashCode(this.updated);
        hash = 43 * hash + Objects.hashCode(this.updtuser);
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
        final AirportVO other = (AirportVO) obj;
        if (!Objects.equals(this.iatacode, other.iatacode)) {
            return false;
        }
        if (!Objects.equals(this.icaocode, other.icaocode)) {
            return false;
        }
        if (!Objects.equals(this.descr, other.descr)) {
            return false;
        }
        if (!Objects.equals(this.creauser, other.creauser)) {
            return false;
        }
        if (!Objects.equals(this.updtuser, other.updtuser)) {
            return false;
        }
        if (!Objects.equals(this.created, other.created)) {
            return false;
        }
        if (!Objects.equals(this.updated, other.updated)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AirportVO{" + "iatacode=" + iatacode + ", icaocode=" + icaocode + ", descr=" + descr + ", created=" + created + ", creauser=" + creauser + ", updated=" + updated + ", updtuser=" + updtuser + '}';
    }

    /**
     * Convert a Timestamp to a String using the given format.
     *
     * @param ts  the timestamp
     * @param fmt the format to use for conversion
     *
     * @return the resulting string
     */
    public static String convert2String(Timestamp ts, String fmt) {
        Date date = new Date();
        date.setTime(ts.getTime());
        String formattedDate = new SimpleDateFormat(fmt).format(date);

        return formattedDate;
    }
}
