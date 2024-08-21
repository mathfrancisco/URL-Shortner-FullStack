package ProjectUrl.UrlShortner.modal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String ShortUrl;
    private String OriginalUrl;
}