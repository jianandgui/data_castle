package cn.edu.swpu.cins.data_castle.config.filter;

import cn.edu.swpu.cins.data_castle.utils.JedisAdapter;
import cn.edu.swpu.cins.data_castle.utils.RedisKey;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@AllArgsConstructor
public class TokenFilter implements HandlerInterceptor{
    private JedisAdapter jedisAdapter;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String mail = request.getHeader("data-castle-mail");
        String token = request.getHeader("data-castle-token");
        if (mail == null || token == null) {
            return forbidden(response);
        }
        String key = RedisKey.getDatacastleLogin(mail);
        if (!jedisAdapter.get(key).equals(token) || jedisAdapter.get(key) == null) {
            return forbidden(response);
        }
        return false;
    }

    private boolean forbidden(HttpServletResponse response) {
        response.setStatus(403);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
