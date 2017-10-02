package cn.edu.swpu.cins.data_castle.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignInUser {
    private String password;
    private String email;
}
