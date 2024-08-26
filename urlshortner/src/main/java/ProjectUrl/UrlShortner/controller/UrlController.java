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
public ResponseEntity<?> generateShortUrl(@RequestBody UrlRequest urlRequest) {
    try {
        Url url = urlService.generateShortUrl(urlRequest.getUrl());
        return ResponseEntity.status(HttpStatus.CREATED).body(url);
    } catch (IllegalArgumentException e) {
        logger.error("Invalid URL provided", e);
        return ResponseEntity.badRequest().body("Invalid URL provided");
    } catch (Exception e) {
        logger.error("Error generating short URL", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating short URL");
    }
}

}