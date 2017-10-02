package cn.edu.swpu.cins.data_castle.service.Impl;

import cn.edu.swpu.cins.data_castle.service.URLCoderService;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class URLCoderServiceImpl implements URLCoderService{
    private final Base64.Encoder encoder = Base64.getUrlEncoder();
    private final Base64.Decoder decoder = Base64.getUrlDecoder();

    @Override
    public String encode(String token) {
        return encoder.encodeToString(token.getBytes());
    }

    @Override
    public String decode(String baseToken) {
        return new String(decoder.decode(baseToken));
    }
}
