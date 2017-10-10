package cn.edu.swpu.cins.data_castle.entity.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MatchTeam {
    private String teamName;
    private List<String> teamerMail;
}
