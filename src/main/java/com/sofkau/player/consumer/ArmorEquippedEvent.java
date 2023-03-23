package com.sofkau.player.consumer;

import com.sofkau.player.domain.external.Armor;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArmorEquippedEvent {

    private String playerId;
    private Armor armorEquipped;
    private String typeEvent;

}
