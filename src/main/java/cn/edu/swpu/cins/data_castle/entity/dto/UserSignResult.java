package cn.edu.swpu.cins.data_castle.entity.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserSignResult {

    private String token;
    private String mail;
}
