package cn.edu.swpu.cins.data_castle.service;

public interface URLCoderService {
    String encode(String token);

    String decode(String baseToken);
}
