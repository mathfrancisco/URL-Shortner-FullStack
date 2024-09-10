package ProjectUrl.UrlShortner.service;

import ProjectUrl.UrlShortner.modal.Url;
import ProjectUrl.UrlShortner.repository.UrlRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

import static ProjectUrl.UrlShortner.logic.GenerateShortUrl.getShortUrl;


@Service
@Transactional
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    public Url getOriginalUrl(String shortUrl) {
        Url url = urlRepository.findByShortUrl(shortUrl);
        if (url == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "URL not found");
        }
        return url;
    }

    public Url generateShortUrl(String originalUrl) {
        Url existingUrl = urlRepository.findByOriginalUrl(originalUrl);
        if (existingUrl != null && !isUrlExpired(existingUrl)) {
            return existingUrl;
        }

        Url urlObject = new Url();
        urlObject.setOriginalUrl(originalUrl);
        urlObject.setShortUrl(getShortUrl(originalUrl));
        long expirationHours = 1; // Tempo de expiração aumentado
        urlObject.setExpirationDate(LocalDateTime.now().plusHours(expirationHours));

        return urlRepository.save(urlObject);
    }

    private boolean isUrlExpired(@NotNull Url url) {
        return LocalDateTime.now().isAfter(url.getExpirationDate());
    }
}



