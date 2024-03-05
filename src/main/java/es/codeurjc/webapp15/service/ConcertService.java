package es.codeurjc.webapp15.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.codeurjc.webapp15.repository.ConcertRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import es.codeurjc.webapp15.model.Concert;

@Service
public class ConcertService {
    @Autowired
    private ConcertRepository repository;

    @Autowired
    private EntityManager entityManager;

    public Optional<Concert> findById(long id) {
        return repository.findById(id);
    }
    
    public boolean exist(long id) {
        return repository.existsById(id);
    }

    public List<Concert> findAll() {
        return repository.findAll();
    }

    public Page<Concert> findConcerts(Pageable page, List<String> locations, List<String> artists,
                                      Optional<LocalDateTime> before, Optional<LocalDateTime> after,
                                      Optional<Float> priceLowerThan, Optional<Float> priceHigherThan) {

        StringBuilder jpql = new StringBuilder("SELECT c FROM Concert c WHERE 1=1");

        if (locations != null && !locations.isEmpty())
            jpql.append(" AND c.place IN :locations");

        if (artists != null && !artists.isEmpty())
            jpql.append(" AND c.artist.name IN :artists");
        
        jpql.append(" ORDER BY c.datetime");

        Query query = entityManager.createQuery(jpql.toString());

        if (locations != null && !locations.isEmpty())
            query.setParameter("locations", locations);

        if (artists != null && !artists.isEmpty())
            query.setParameter("artists", artists);

        query.setFirstResult(page.getPageNumber() * page.getPageSize());
        query.setMaxResults(page.getPageSize());

        return new PageImpl<>(query.getResultList());
	}

    public List<Long> countConcertsByMonthInRange(long months) {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime plusMonths = now.plusMonths(months);

        Query query = entityManager.createQuery("SELECT MONTH(c.datetime), COUNT(c.datetime) FROM Concert c WHERE c.datetime BETWEEN :now AND :plusmonths GROUP BY MONTH(c.datetime)");
        query.setParameter("now", now);
        query.setParameter("plusmonths", plusMonths);
        Logger.getAnonymousLogger().info(String.valueOf(query.getFirstResult()));
        return query.getResultList();
    }

    public void save(Concert concert) {
        repository.save(concert);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }
}
