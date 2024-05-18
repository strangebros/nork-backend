package site.strangebros.nork.global.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import site.strangebros.nork.global.auth.config.RoleAuthenticationInterceptor;
import site.strangebros.nork.global.auth.config.AuthorizationInterceptor;
import site.strangebros.nork.global.auth.config.MemberArgumentResolver;

@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthorizationInterceptor authorizationInterceptor;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor)
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/members/login","/members/signUp", "/members/guest-login");
        registry.addInterceptor(new RoleAuthenticationInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/members/login","/members/signUp", "/members/guest-login", "/workspaces/search");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new MemberArgumentResolver());
    }
}
