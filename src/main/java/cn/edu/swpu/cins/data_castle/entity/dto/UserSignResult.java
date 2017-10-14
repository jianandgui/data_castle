package cn.edu.swpu.cins.data_castle.entity.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserSignResult {

    private String token;
    private String username;
    private String mail;
}
