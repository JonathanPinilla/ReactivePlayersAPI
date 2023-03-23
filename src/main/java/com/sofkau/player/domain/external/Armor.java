package com.sofkau.player.domain.external;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "player_armor")
public class Armor {

    @Id
    private String id;
    private ArmorType armorType;
    private String armorFamily;
    private Double armor;
    private Double damage;
    private Double health;
    private Double mana;
    private Double speed;
    private Boolean isEquipped;

}