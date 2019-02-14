package airportservice.manager;

import airportservice.facade.AirportManagerRemote;
import airportservice.facade.AirportVO;
import airportservice.model.Airport;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Implementation of airport master data operations.
 */
@Stateless(name = "AirportManager")
public class AirportManager implements AirportManagerLocal, AirportManagerRemote {
    @Resource
    SessionContext ctx;

    @PersistenceContext
    private EntityManager em;

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Airport> getAirports() {
        List<Airport> airportList = em.createQuery("select a FROM Airport a ORDER BY a.iatacode").getResultList();

        return airportList;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Airport create(Airport airport) {
        if (airport == null) {
            throw new IllegalArgumentException("argument airport=null is invalid");
        }
        if (airport.getIatacode() == null) {
            throw new IllegalArgumentException("argument airport.iatacode=null is invalid");
        }
        if (airport.getIcaocode() == null) {
            throw new IllegalArgumentException("argument airport.icaocode=null is invalid");
        }

        // Set the create and update user to valid values
        if (airport.getCreauser() == null || airport.getCreauser().isEmpty()) {
            airport.setCreauser(ctx.getCallerPrincipal().getName());
        }
        if (airport.getUpdtuser() == null || airport.getUpdtuser().isEmpty()) {
            airport.setUpdtuser(ctx.getCallerPrincipal().getName());
        }

        // Set the create and update timestamp to valid values
        if (airport.getCreated() == null) {
            airport.setCreated(new Timestamp(System.currentTimeMillis()));
        }
        if (airport.getUpdated() == null) {
            airport.setUpdated(new Timestamp(System.currentTimeMillis()));
        }

        em.persist(new Airport(airport.getIatacode(), airport.getIcaocode(), airport.getDescr(), airport.getCreated(), airport.getCreauser(), airport.getUpdated(), airport.getUpdtuser()));
        em.flush();

        return airport;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Airport remove(String id) {
        if (id == null) {
            return null;
        }

        Airport airport = em.find(Airport.class, id);

        if (airport != null) {
            em.remove(airport);
            em.flush();
            return airport;
        }
        else {
            return null;
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Airport update(Airport airport) {
        if (airport == null) {
            throw new IllegalArgumentException("argument airport=null is invalid");
        }
        if (airport.getIatacode() == null) {
            throw new IllegalArgumentException("argument airport.iatacode=null is invalid");
        }
        if (airport.getIcaocode() == null) {
            throw new IllegalArgumentException("argument airport.icaocode=null is invalid");
        }

        // Set the update user to a valid value
        if (airport.getUpdtuser() == null || airport.getUpdtuser().isEmpty()) {
            airport.setUpdtuser(ctx.getCallerPrincipal().getName());
        }

        Airport tmp = em.find(Airport.class, airport.getIatacode());

        if (tmp != null) {
            tmp.setIcaocode(airport.getIcaocode());
            tmp.setDescr(airport.getDescr());
            tmp.setUpdated(new Timestamp(System.currentTimeMillis()));
            tmp.setUpdtuser(airport.getUpdtuser());

            return em.merge(tmp);
        }
        // Create an airport with update user values
        tmp = create(new Airport(airport.getIatacode(), airport.getIcaocode(), airport.getDescr(), airport.getUpdtuser(), airport.getUpdtuser()));

        return tmp;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<AirportVO> getContent() {
        List<Airport> airportList = em.createQuery("select a FROM Airport a ORDER BY a.iatacode").getResultList();

        List<AirportVO> airportVOList = new ArrayList<>();
        for (Airport airport : airportList) {
            AirportVO arpo = buildAirportVO(airport);

            airportVOList.add(arpo);
        }

        return airportVOList;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<AirportVO> getContent(int offset, int count) {
        if (offset < 0) {
            throw new IllegalArgumentException("offset < 0");
        }
        if (count < 1) {
            throw new IllegalArgumentException("count < 1");
        }

        Query query = em.createQuery("select a FROM Airport a ORDER BY a.iatacode");
        query.setFirstResult(offset);
        query.setMaxResults(count);

        List<Airport> airportList = query.getResultList();

        List<AirportVO> airportVOList = new ArrayList<>();
        for (Airport airport : airportList) {
            AirportVO arpo = buildAirportVO(airport);

            airportVOList.add(arpo);
        }

        return airportVOList;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Airport getById(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("id is null or empty");
        }

        Airport airport = em.find(Airport.class, id);

        return airport;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Airport> getByIcaocode(String icaocode) {
        if (icaocode == null || icaocode.isEmpty()) {
            throw new IllegalArgumentException("icaocode is null or empty");
        }

        Query query = em.createQuery("select a FROM Airport a WHERE a.icaocode like :icaocode");
        query.setParameter("icaocode", icaocode);

        List<Airport> airports = query.getResultList();

        return airports;
    }

    @Override
    public boolean exists(String id) {
        if (getById(id) != null) {
            return true;
        }
        return false;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public long countAirports() {
        Query query;

        query = em.createQuery("SELECT count(a) FROM Airport a");
        long count = (long) query.getSingleResult();

        return count;
    }

    private AirportVO buildAirportVO(Airport arpo) {
        AirportVO airportVO = new AirportVO();

        airportVO.setCreated(AirportVO.convert2String(arpo.getCreated(), "yyyy-MM-dd HH:mm:ss.S"));
        airportVO.setCreauser(arpo.getCreauser());
        airportVO.setDescr(arpo.getDescr());
        airportVO.setIatacode(arpo.getIatacode());
        airportVO.setIcaocode(arpo.getIcaocode());
        airportVO.setUpdated(AirportVO.convert2String(arpo.getUpdated(), "yyyy-MM-dd HH:mm:ss.S"));
        airportVO.setUpdtuser(arpo.getUpdtuser());

        return airportVO;
    }
}
