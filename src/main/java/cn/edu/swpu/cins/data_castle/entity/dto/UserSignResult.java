package cn.edu.swpu.cins.data_castle.entity.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserSignResult {

    private String token;
    private String mail;
    private String username;
    private boolean matched;
    private int teamId;
}
