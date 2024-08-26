package ProjectUrl.UrlShortner.controller;

import ProjectUrl.UrlShortner.modal.Url;
import ProjectUrl.UrlShortner.service.UrlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("url/shorten")
@CrossOrigin(origins = "http://localhost:4200")
public class UrlController {

    private static final Logger logger = LoggerFactory.getLogger(UrlController.class);

    @Autowired
    private UrlService urlService;

    @GetMapping("/{id}")
    public ResponseEntity<String> getOriginalUrl(@PathVariable String id) {
        Url originalUrl = urlService.getOriginalUrl(id);
        if (originalUrl == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "URL not found");
        }
        return ResponseEntity.ok(originalUrl.getOriginalUrl());
    }

    @PostMapping
    public ResponseEntity<Url> generateShortUrl(@RequestBody String url) {
        try {
            Url generatedUrl = urlService.generateShortUrl(url);
            return ResponseEntity.status(HttpStatus.CREATED).body(generatedUrl);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException e) {
        logger.error("Error: {}", e.getMessage(), e);
        return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        logger.error("An unexpected error occurred: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred. Please try again later.");
    }
}