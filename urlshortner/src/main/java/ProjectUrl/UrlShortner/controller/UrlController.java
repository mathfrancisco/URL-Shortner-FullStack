<<<<<<< Updated upstream
=======
package ProjectUrl.UrlShortner.controller;

import ProjectUrl.UrlShortner.modal.Url;
import ProjectUrl.UrlShortner.service.UrlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;



>>>>>>> Stashed changes
@RestController
@RequestMapping("url/shorten")
@CrossOrigin(origins = "http://localhost:4200")
public class UrlController {

    private static final Logger logger = LoggerFactory.getLogger(UrlController.class);

    @Autowired
    private UrlService urlService;

    @GetMapping("/{id}")
<<<<<<< Updated upstream
    public ResponseEntity<Url> getOriginalUrl(@PathVariable String id) {
        try {
            Url url = urlService.getOriginalUrl(id);
            if (url == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "URL not found");
            }
            return ResponseEntity.ok(url);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid URL ID provided", e);
            return ResponseEntity.badRequest().body(null);
=======
    public ResponseEntity<String> getOriginalUrl(@PathVariable String id) {
        try {
            Url originalUrl = urlService.getOriginalUrl(id);
            if (originalUrl == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "URL not found");
            }
            return ResponseEntity.ok(originalUrl.getOriginalUrl());
        } catch (Exception e) {
            logger.error("Error getting original URL for ID: {}", id, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrieve original URL", e);
>>>>>>> Stashed changes
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
<<<<<<< Updated upstream
    public ResponseEntity<Url> generateShortUrl(@RequestBody UrlRequest urlRequest) {
        try {
            Url url = urlService.generateShortUrl(urlRequest.getUrl());
            return ResponseEntity.ok(url);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid URL provided", e);
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            logger.error("Error generating short URL", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
=======
    public Url generateShortUrl(@Validated @RequestBody String url) throws Exception {
        try {
            return urlService.generateShortUrl(url);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid URL: {}", url, e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid URL", e);
        } catch (Exception e) {
            logger.error("Failed to generate short URL", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleInvalidUrl(IllegalArgumentException e) {
        logger.error("Invalid URL: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        logger.error("An error occurred: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred. Please try again later.");
    }
>>>>>>> Stashed changes
}
