package cn.edu.swpu.cins.data_castle.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchTeam {
    private String teamName;
    private List<String> teamerMail;
}
