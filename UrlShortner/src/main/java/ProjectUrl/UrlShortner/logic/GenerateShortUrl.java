package ProjectUrl.UrlShortner.logic;

import com.google.common.hash.Hashing;
import org.apache.commons.validator.routines.UrlValidator;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class GenerateShortUrl {

    private static final String ALLOWED_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_";

    public static String getShortUrl(String url) {
        String hash = Hashing.murmur3_32_fixed().hashString(url, StandardCharsets.UTF_8).toString();
        byte[] bytes = hash.getBytes(StandardCharsets.UTF_8);
        String encoded = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        return encoded.substring(0, Math.min(8, encoded.length()));
    }

    public static boolean isUrlValid(String url) {
        UrlValidator urlValidator = new UrlValidator(new String[]{"http", "https"});
        return urlValidator.isValid(url);
    }
}