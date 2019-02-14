package airportservice.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Airport master data entity.
 */
@Entity
@Table(name = "AIRPORT")
@NamedQueries({
    @NamedQuery(name = "Airport.findAll", query = "SELECT a FROM Airport a")})
public class Airport implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "IATACODE")
    private String iatacode;

    @Basic(optional = false)
    @Column(name = "ICAOCODE")
    private String icaocode;

    @Basic(optional = false)
    @Column(name = "DESCR")
    private String descr;

    @Basic(optional = false)
    @Column(name = "CREATED")
    private Timestamp created;

    @Basic(optional = false)
    @Column(name = "CREAUSER")
    private String creauser;

    @Basic(optional = false)
    @Column(name = "UPDATED")
    private Timestamp updated;

    @Basic(optional = false)
    @Column(name = "UPDTUSER")
    private String updtuser;

    @Version
    @Basic(optional = false)
    private int version;

    public Airport() {
    }

    public Airport(String iatacode) {
        this.iatacode = iatacode;
        this.icaocode = "";
        this.descr = "Unknown";
        this.created = new Timestamp(System.currentTimeMillis());
        this.creauser = "System";
        this.updated = this.created;
        this.updtuser = "System";
    }

    public Airport(String iatacode, String icaocode, String descr, String creauser, String updtuser) {
        this.iatacode = iatacode;
        this.icaocode = icaocode;
        this.descr = descr;
        this.created = new Timestamp(System.currentTimeMillis());
        this.creauser = creauser;
        this.updated = this.created;
        this.updtuser = updtuser;
    }

    public Airport(String iatacode, String icaocode, String descr, Timestamp created, String creauser, Timestamp updated, String updtuser) {
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

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public String getCreauser() {
        return creauser;
    }

    public void setCreauser(String creauser) {
        this.creauser = creauser;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    public String getUpdtuser() {
        return updtuser;
    }

    public void setUpdtuser(String updtuser) {
        this.updtuser = updtuser;
    }

    public int getVersion() {
        return version;
    }

    protected void setVersion(int version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iatacode != null ? iatacode.hashCode() : 0);
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
        final Airport other = (Airport) obj;
        if (!Objects.equals(this.iatacode, other.iatacode)) {
            return false;
        }
        if (!Objects.equals(this.icaocode, other.icaocode)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Airport{" + "iatacode=" + iatacode + ", icaocode=" + icaocode + ", descr=" + descr + ", created=" + created + ", creauser=" + creauser + ", updated=" + updated + ", updtuser=" + updtuser + ", version=" + version + '}';
    }
}
