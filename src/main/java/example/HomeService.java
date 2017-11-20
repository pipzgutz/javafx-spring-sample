package example;

import org.springframework.stereotype.Service;

/**
 * @author Philip Mark Gutierrez <pgutierrez@owens.com>
 * @version 1.0
 * @since November 19, 2017
 */
@Service
public class HomeService {
    public String greet(String name) {
        return "Hello " + name + "!";
    }
}
