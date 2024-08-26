package ProjectUrl.UrlShortner.logic;

import com.google.common.hash.Hashing;
import org.apache.commons.validator.routines.UrlValidator;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class GenerateShortUrl {

    private static final int HASH_LENGTH = 20; // Murmur3_32_fixed produces 128-bit hashes
    private static final int SHORT_URL_LENGTH = 8;

    public static String getShortUrl(String url) {
        String hash = Hashing.murmur3_32_fixed().hashString(url, StandardCharsets.UTF_8).toString();
        byte[] bytes = hash.substring(HASH_LENGTH / 8, HASH_LENGTH / 8 * 2).getBytes(StandardCharsets.UTF_8);
        String encoded = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        return encoded.substring(0, Math.min(SHORT_URL_LENGTH, encoded.length()));
    }

    public static boolean isUrlValid(String url) {
        UrlValidator urlValidator = new UrlValidator(new String[]{"http", "https"});
        return urlValidator.isValid(url);
    }
}
