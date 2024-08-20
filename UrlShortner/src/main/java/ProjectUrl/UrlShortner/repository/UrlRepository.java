package ProjectUrl.UrlShortner.repository;

import ProjectUrl.UrlShortner.modal.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UrlRepository extends JpaRepository<Url,Integer> {

    @Query(value = "select originalUrl from Url where shortUrl = ?1", nativeQuery = true)
    String findByShortUrl(String id);
}
