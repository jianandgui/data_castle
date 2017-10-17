package cn.edu.swpu.cins.data_castle.config;

import cn.edu.swpu.cins.data_castle.config.filter.TokenFilter;
import cn.edu.swpu.cins.data_castle.service.PasswordEncoderService;
import cn.edu.swpu.cins.data_castle.utils.JedisAdapter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@AllArgsConstructor
public class WebFilterConfig extends WebMvcConfigurerAdapter {

    private JedisAdapter jedisAdapter;
    private PasswordEncoderService passwordEncoderService;
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/*").allowedOrigins("*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       /* registry
                .addInterceptor(new HttpRequestInterceptor())
                .addPathPatterns("/**");
        registry
                .addInterceptor(timerInterceptor)
                .addPathPatterns("/user/signup", "/user/enable", "/match/upload", "/match/join");*/
        registry
                .addInterceptor(new TokenFilter(jedisAdapter, passwordEncoderService))
                .addPathPatterns("/dataCastle/match/team")
                .addPathPatterns("/dataCastle/match/answer");
    }

}
