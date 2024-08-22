package ProjectUrl.UrlShortner.modal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Url {

    @Id
    private String shortUrl;

    @Column(nullable = false)
    private String originalUrl;

    // Optional: if you want to keep track of when the URL was created
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}