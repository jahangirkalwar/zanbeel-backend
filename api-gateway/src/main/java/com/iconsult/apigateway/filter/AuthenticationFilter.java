//package com.iconsult.apigateway.filter;
//
//import com.iconsult.apigateway.util.JwtUtil;
//import org.hibernate.service.spi.ServiceException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
//import org.springframework.http.HttpHeaders;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.List;
//import java.util.function.Predicate;
//import java.util.logging.Logger;
//
//@Component
//public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
//
//    private static final Logger logger = Logger.getLogger(AuthenticationFilter.class.getName());
//
//    @Autowired
//    private RouteValidator validator;
//
//    @Autowired
//    private RestTemplate template;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    public AuthenticationFilter() {
//        super(Config.class);
//    }
//
//    @Override
//    public GatewayFilter apply(Config config) {
//        return (exchange, chain) -> {
//            if (validator.isSecured.test(exchange.getRequest())) {
//                logger.info("Request is secured, checking for authorization header");
//
//                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
//                    logger.severe("Missing authorization header");
//                    throw new RuntimeException("Missing authorization header");
//                }
//
//                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
//                if (authHeader != null && authHeader.startsWith("Bearer ")) {
//                    authHeader = authHeader.substring(7);
//                }
//
//                try {
//                    // REST call to AUTH service
//                    String url = "http://USER-SERVICE/v1/customer/validateToken?token=" + authHeader;
//                    logger.info("Validating token with URL: " + url);
//                    template.getForObject(url, String.class);
//                    jwtUtil.validateToken(authHeader);
//                    logger.info("Token validated successfully");
//
//                } catch (Exception e) {
//                    logger.severe("Invalid access: " + e.getMessage());
//                    throw new ServiceException("Unauthorized access to application", e);
//                }
//            }
//            return chain.filter(exchange);
//        };
//    }
//
//    public static class Config {
//        // Empty class as placeholder for any future configurations
//    }
//}

package com.iconsult.apigateway.filter;

import com.iconsult.apigateway.util.JwtUtil;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private static final Logger logger = Logger.getLogger(AuthenticationFilter.class.getName());

    @Autowired
    private RouteValidator validator;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                logger.info("Request is secured, checking for authorization header");

                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    logger.severe("Missing authorization header");
                    throw new RuntimeException("Missing authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }

                try {
                    // Validate token with WebClient
                    //String url = "http://USER-SERVICE/v1/customer/validateToken?token=" + authHeader;
                    //logger.info("Validating token with URL: " + url);
                    jwtUtil.validateToken(authHeader);
                } catch (Exception e) {
                    logger.severe("Invalid access: " + e.getMessage());
                    throw new ServiceException("Unauthorized access to application", e);
                }
            }
            return chain.filter(exchange);
        };
    }

    public static class Config {
        // Empty class as placeholder for any future configurations
    }
}

