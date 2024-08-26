package ProjectUrl.UrlShortner.controller;

import ProjectUrl.UrlShortner.modal.Url;
import ProjectUrl.UrlShortner.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("url/shorter")
@CrossOrigin(origins = "http://localhost:4200")
public class UrlController {

    @Autowired
    private UrlService urlService;

    @GetMapping("/{id}")
    public String getOriginalUrl(@PathVariable String id) {
        String originalUrl = String.valueOf(urlService.getOriginalUrl(id));
        if (originalUrl == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "URL not found");
        }
        return originalUrl;
    }

    @PostMapping
@ResponseStatus(HttpStatus.CREATED)
public Url generateShortUrl(@RequestBody UrlRequest urlRequest) {
    if (urlRequest.getUrl() == null || urlRequest.getUrl().isEmpty()) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "URL cannot be empty");
    }
    return urlService.generateShortUrl(urlRequest.getUrl());
}

}