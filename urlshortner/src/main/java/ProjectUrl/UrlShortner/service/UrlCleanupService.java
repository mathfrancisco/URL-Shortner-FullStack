package ProjectUrl.UrlShortner.service;

import ProjectUrl.UrlShortner.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UrlCleanupService {

    @Autowired
    private UrlRepository urlRepository;

    // This method will run every hour
    @Scheduled(fixedRate = 3600000) // 1 hour in milliseconds
    public void deleteExpiredUrls() {
        LocalDateTime now = LocalDateTime.now();
        urlRepository.deleteAllByExpirationDateBefore(now);
        System.out.println("Expired URLs deleted at: " + now);
    }
}