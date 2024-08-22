package ProjectUrl.UrlShortner.repository;

import ProjectUrl.UrlShortner.modal.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<Url, String> {
    
    @Query("SELECT u.originalUrl FROM Url u WHERE u.shortUrl = :shortUrl")
    String findOriginalUrlByShortUrl(String shortUrl);

    Url findByShortUrl(String shortUrl);
}
