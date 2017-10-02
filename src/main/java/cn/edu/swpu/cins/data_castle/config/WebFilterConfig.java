package cn.edu.swpu.cins.data_castle.config;

import cn.edu.swpu.cins.data_castle.config.filter.TokenFilter;
import cn.edu.swpu.cins.data_castle.utils.JedisAdapter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@AllArgsConstructor
public class WebFilterConfig extends WebMvcConfigurerAdapter {

    private JedisAdapter jedisAdapter;
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
                .addInterceptor(new TokenFilter(jedisAdapter))
                .addPathPatterns("/dataCastle/*")
                .excludePathPatterns("/dataCastle/user","/dataCastle/user/enable");
    }

}
