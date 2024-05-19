package id.my.hendisantika.notesapp.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Project : Notes-App
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 5/20/24
 * Time: 06:43
 * To change this template use File | Settings | File Templates.
 */
@Service
public class RateLimitingService {
    private final Map<String, Long> clientRequestTimestamps = new HashMap<>();
    private final Map<String, Integer> clientRequestCounts = new HashMap<>();
    @Value("${app.requestLimit}")
    private int REQUEST_LIMIT;  // Change as needed
    @Value("${app.requestLimitMS}")
    private long TIME_INTERVAL_MILLIS;  // Change as needed (1 minute in this example)
}
