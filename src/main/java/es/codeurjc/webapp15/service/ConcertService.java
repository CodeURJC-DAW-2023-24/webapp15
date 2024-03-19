package es.codeurjc.webapp15.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.codeurjc.webapp15.repository.ConcertRepository;
import es.codeurjc.webapp15.repository.TicketRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import es.codeurjc.webapp15.model.Artist;
import es.codeurjc.webapp15.model.Concert;
import es.codeurjc.webapp15.model.Ticket;

@Service
public class ConcertService {

    @Autowired
    private ConcertRepository repository;

    @Autowired
    private TicketRepository ticketRepository;

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
    public Page<Concert> findAllPage(PageRequest page) {
        return repository.findAll(page);
    }
    public List<String> findLocations(){
        return repository.findLocations();
    }
    public List<String> findArtists(){
        return repository.findArtists();
    }
    
    public List<Artist> findByConcert(List<Concert> concerts){
        return repository.findByConcert(concerts);
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

    public List<Object> countConcertsByMonthInRange(long months) {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime plusMonths = now.plusMonths(months);

        Query query = entityManager.createQuery("SELECT new map(new map(YEAR(c.datetime) as year, MONTH(c.datetime) as month) as date, COUNT(c) as count) FROM Concert c WHERE c.datetime BETWEEN :now AND :plusmonths GROUP BY YEAR(c.datetime), MONTH(c.datetime)");

        // Query query = entityManager.createQuery("SELECT MONTH(c.datetime), COUNT(c.datetime) FROM Concert c WHERE c.datetime BETWEEN :now AND :plusmonths GROUP BY MONTH(c.datetime)");
        query.setParameter("now", now);
        query.setParameter("plusmonths", plusMonths);
        Logger.getAnonymousLogger().info(String.valueOf(query.getFirstResult()));
        return query.getResultList();
    }

    public void save(Concert concert) {
        repository.save(concert);
    }

    public void delete(long id) {
         // Search concert in BBDD
         Optional<Concert> optionalConcert = repository.findById(id);
         if (optionalConcert.isPresent()) {
             Concert concert = optionalConcert.get();
             
             // Delete tickets of this concert
             List<Ticket> tickets = concert.getTickets();
             if (tickets != null) {
                 for (Ticket ticket : tickets) {
                     ticketRepository.delete(ticket);
                 }
             }
             
             // Delete concert
             repository.deleteById(id);
         
     }
 
    }

    public  Page<Concert> findByArtistName(String name, Pageable page){
        return repository.findByArtistName(name,page);
    }
}
