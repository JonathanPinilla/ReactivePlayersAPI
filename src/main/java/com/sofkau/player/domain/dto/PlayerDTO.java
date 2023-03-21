package com.sofkau.player.domain.dto;

import com.sofkau.player.domain.external.Armor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDTO {

    private String id;
    private String name;
    private String raze;
    private Integer level;
    private Double armor;
    private Double Damage;
    private Double health;
    private Double mana;
    private Double Speed;
    private List<Armor> armors;

}
