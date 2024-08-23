package ProjectUrl.UrlShortner.repository;

import ProjectUrl.UrlShortner.modal.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<Url, String> {

    // Custom query to find a Url by its short URL
    @Query("SELECT u FROM Url u WHERE u.shortUrl = :shortUrl")
    Url findByShortUrl(@Param("shortUrl") String shortUrl);
}
