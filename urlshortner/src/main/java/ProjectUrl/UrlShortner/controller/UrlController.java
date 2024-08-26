@RestController
@RequestMapping("url/shorter")
@CrossOrigin(origins = "http://localhost:4200")
public class UrlController {

    @Autowired
    private UrlService urlService;

    @GetMapping("/{id}")
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
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
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
}
