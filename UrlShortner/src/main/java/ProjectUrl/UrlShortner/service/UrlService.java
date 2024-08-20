package ProjectUrl.UrlShortner.service;

import ProjectUrl.UrlShortner.modal.Url;
import ProjectUrl.UrlShortner.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static ProjectUrl.UrlShortner.logic.GenerateShortUrl.getShortUrl;
import static ProjectUrl.UrlShortner.logic.GenerateShortUrl.isUrlValid;

@Service
public class UrlService {
    @Autowired
    private UrlRepository urlRepository;


    public String getOriginlUrl(String id) {
        return urlRepository.findByShortUrl(id);
    }

    public Url generateShortUrl(String url) {
        if(! isUrlValid(url)) {
            System.out.println("URL is not valid");
            return null;
        }

        Url urlObject = new Url();
        urlObject.setOriginalUrl(url);
        urlObject.setShortUrl(getShortUrl(url));

        return urlRepository.save(urlObject);
    }
}
