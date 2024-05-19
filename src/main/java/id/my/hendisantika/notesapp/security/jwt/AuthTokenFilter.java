package id.my.hendisantika.notesapp.security.jwt;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Created by IntelliJ IDEA.
 * Project : Notes-App
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 5/20/24
 * Time: 06:45
 * To change this template use File | Settings | File Templates.
 */
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);
    private final JwtUtils jwtUtils;
    private final UserDetailsServiceImpl userDetailsService;
}
