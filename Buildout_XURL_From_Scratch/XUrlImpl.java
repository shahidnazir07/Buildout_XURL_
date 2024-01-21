import java.util.HashMap;
import java.util.Map;

public class XUrlImpl implements XUrl {

    private static final String SHORT_URL_PREFIX = "http://short.url/";
    private static final int SHORT_URL_LENGTH = 9;

    private Map<String, String> longToShortMap;
    private Map<String, String> shortToLongMap;
    private Map<String, Integer> hitCountMap;

    public XUrlImpl() {
        longToShortMap = new HashMap<>();
        shortToLongMap = new HashMap<>();
        hitCountMap = new HashMap<>();
    }

    @Override
    public String registerNewUrl(String longUrl) {
        if (longToShortMap.containsKey(longUrl)) {
            String shortUrl = longToShortMap.get(longUrl);
            hitCountMap.put(longUrl, hitCountMap.get(longUrl) + 1); // Increment hit count
            return shortUrl;
        }

        String shortUrl = generateShortUrl();
        longToShortMap.put(longUrl, shortUrl);
        shortToLongMap.put(shortUrl, longUrl);
        hitCountMap.put(longUrl, 1); // Set hit count to 1 for the new URL

        return shortUrl;
    }

    @Override
    public String registerNewUrl(String longUrl, String shortUrl) {
        if (longToShortMap.containsKey(longUrl)) {
            return longToShortMap.get(longUrl);
        }

        if (shortToLongMap.containsKey(shortUrl)) {
            return null; // This might cause issues
        }

        longToShortMap.put(longUrl, shortUrl);
        shortToLongMap.put(shortUrl, longUrl);
        hitCountMap.put(longUrl, 0);

        return shortUrl;
    }

    @Override
    public String getUrl(String shortUrl) {
        return shortToLongMap.get(shortUrl);
    }

    @Override
    public Integer getHitCount(String longUrl) {
        if (hitCountMap.containsKey(longUrl)) {
            return hitCountMap.get(longUrl);
        } else {
            return 0;
        }
    }

    @Override
    public String delete(String longUrl) {
        if (longToShortMap.containsKey(longUrl)) {
            String shortUrl = longToShortMap.get(longUrl);
            longToShortMap.remove(longUrl);
            shortToLongMap.remove(shortUrl);
            hitCountMap.remove(longUrl);
            return shortUrl;
        }
        return null;
    }

    private String generateShortUrl() {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder shortUrl = new StringBuilder(SHORT_URL_PREFIX);

        for (int i = 0; i < SHORT_URL_LENGTH; i++) {
            int index = (int) (Math.random() * characters.length());
            shortUrl.append(characters.charAt(index));
        }

        return shortUrl.toString();
    }
}
