package cn.edu.swpu.cins.data_castle.service.Impl;

import cn.edu.swpu.cins.data_castle.service.MailFormatService;
import cn.edu.swpu.cins.data_castle.service.URLCoderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MailFormatServiceImpl implements MailFormatService{


    @Value("${data_castle.server.host}")
    private String host;
    @Value("${data_castle.server.port}")
    private int port;
    @Value("${data_castle.path.user.enable}")
    private String enablePath;
    private URLCoderService urlCoderService;

    @Autowired
    public MailFormatServiceImpl(URLCoderService urlCoderService) {
        this.urlCoderService = urlCoderService;
    }

    @Override
    public String getSignUpSubject(String username) {
        final String format = "尊敬的username，欢迎注册创数据团队data_castle活动";
        return format.replaceAll("username", username);
    }

    @Override
    public String getSignUpContent(String mail, String token) {
        final String previous = "请访问下面的链接以激活您的邮箱（<b>30分钟有效</b>）<br/>";
        String baseMail = urlCoderService.encode(mail);
        String baseToken = urlCoderService.encode(token);
        //String link = "http://"+ host + ':' + port + "/" + enablePath + "?mail=" +	baseMail + "&token=" + baseToken;
        String link = "http://"+ host + ':' + port + "/" + "cins/passCheck/index.html" + "?mail=" +	baseMail + "&token=" + baseToken;
        String htmlLink = "<a href='" + link + "' target='_blank'>" + link + "</a>";
        return previous + htmlLink;
    }
}
