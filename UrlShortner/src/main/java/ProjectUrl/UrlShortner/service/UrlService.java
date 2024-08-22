package ProjectUrl.UrlShortner.service;

import ProjectUrl.UrlShortner.modal.Url;
import ProjectUrl.UrlShortner.repository.UrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static ProjectUrl.UrlShortner.logic.GenerateShortUrl.getShortUrl;
import static ProjectUrl.UrlShortner.logic.GenerateShortUrl.isUrlValid;

@Service
public class UrlService {
    private static final Logger logger = LoggerFactory.getLogger(UrlService.class);

    @Autowired
    private UrlRepository urlRepository;

    @Transactional(readOnly = true)
    public String getOriginalUrl(String id) {
        return urlRepository.findByShortUrl(id);
    }

    @Transactional
    public Url generateShortUrl(String url) {
        if (!isUrlValid(url)) {
            logger.error("Invalid URL: {}", url);
            throw new IllegalArgumentException("URL is not valid");
        }

        Url urlObject = new Url();
        urlObject.setOriginalUrl(url);
        urlObject.setShortUrl(getShortUrl(url));

        return urlRepository.save(urlObject);
    }
}