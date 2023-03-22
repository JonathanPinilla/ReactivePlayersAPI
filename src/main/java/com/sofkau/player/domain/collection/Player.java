package com.sofkau.player.domain.collection;

import com.sofkau.player.domain.external.Armor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Document(collection = "players")
@AllArgsConstructor
@NoArgsConstructor
public class Player {

    @Id
    private String id = UUID.randomUUID().toString().substring(0, 10);
    private String name;
    private String raze;
    private Integer level = 1;
    private Double armor = 0.0;
    private Double Damage = 10.0;
    private Double health = 30.0;
    private Double mana = 10.0;
    private Double Speed = 5.0;
    private List<Armor> armors = new ArrayList<>();


    private void updateArmor(Armor armor) {
        if (this.armors.stream().noneMatch(armor1 -> armor1.getArmorType() == armor.getArmorType())) {
            this.armors.add(armor);
            this.armor += armor.getArmor();
            this.Damage += armor.getDamage();
            this.health += armor.getHealth();
            this.mana += armor.getMana();
            this.Speed += armor.getSpeed();
        } else {
            throw new IllegalArgumentException("There is already an armor of this type");
        }
    }

    private void removeArmor(Armor armor) {
        if (this.armors.stream().anyMatch(armor1 ->
                armor1.getArmorType() == armor.getArmorType() &&
                        armor1.getId().equals(armor.getId())
        )) {
            this.armors.remove(armor);
            this.armor -= armor.getArmor();
            this.Damage -= armor.getDamage();
            this.health -= armor.getHealth();
            this.mana -= armor.getMana();
            this.Speed -= armor.getSpeed();
        } else {
            throw new IllegalArgumentException("There is no armor of this type");
        }
    }

    private void levelUp() {
        if (this.level == 10) {
            throw new IllegalArgumentException("You have reached the maximum level");
        }
        this.level++;
        this.armor += 5;
        this.Damage += 5;
        this.health += 5;
        this.mana += 5;
        this.Speed += 5;
    }

}
