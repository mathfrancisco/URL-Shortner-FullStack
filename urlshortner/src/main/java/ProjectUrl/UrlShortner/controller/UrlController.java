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

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UrlController {

    @Autowired
    private UrlService urlService;
    private static final Logger logger = LoggerFactory.getLogger(UrlController.class);

    @GetMapping("/{id}")
    public ResponseEntity<Void> redirectToOriginalUrl(@PathVariable String id) {
        try {
            Url urlMapping = urlService.getOriginalUrl(id);
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(urlMapping.getOriginalUrl()))
                    .build();
        } catch (ResponseStatusException e) {
            logger.error("Error retrieving original URL", e);
            throw e;
        }
    }

    @PostMapping(value = "/", consumes = "text/plain")
    public ResponseEntity<Map<String, String>> generateShortUrl(@RequestBody String url) {
        try {
            // Verificar se a URL já existe
            Url existingUrl = urlService.findByOriginalUrl(url);
            if (existingUrl != null) {
                Map<String, String> response = new HashMap<>();
                response.put("originalUrl", url);
                response.put("shortUrl", existingUrl.getShortUrl());
                return ResponseEntity.ok(response);
            }

            // Se não existir, gerar nova URL curta
            Url generatedUrl = urlService.generateShortUrl(url);
            Map<String, String> response = new HashMap<>();
            response.put("originalUrl", url);
            response.put("shortUrl", generatedUrl.getShortUrl());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            logger.error("Error generating short URL", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Error generating short URL"));
        }
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException e) {
        logger.error("Error: {}", e.getMessage(), e);
        return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception e) {
        logger.error("Unexpected error occurred", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }
}

