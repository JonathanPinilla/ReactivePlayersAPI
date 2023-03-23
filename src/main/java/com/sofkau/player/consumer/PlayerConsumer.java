package com.sofkau.player.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sofkau.player.usecases.EquipArmorUseCase;
import com.sofkau.player.usecases.UnEquipArmorUseCase;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class PlayerConsumer {

    private final ObjectMapper objectMapper;
    private final EquipArmorUseCase equipArmorUseCase;
    private final UnEquipArmorUseCase unEquipArmorUseCase;

    public PlayerConsumer(ObjectMapper objectMapper, EquipArmorUseCase equipArmorUseCase, UnEquipArmorUseCase unEquipArmorUseCase) {
        this.objectMapper = objectMapper;
        this.equipArmorUseCase = equipArmorUseCase;
        this.unEquipArmorUseCase = unEquipArmorUseCase;
    }

    @RabbitListener(queues = "armor.queue")
    public void consume(String message) throws JsonProcessingException {
        var armorEquippedEvent = objectMapper.readValue(message, ArmorEquippedEvent.class);
        if(armorEquippedEvent.getTypeEvent().equals("ArmorEquipped"))
            equipArmorUseCase.apply(armorEquippedEvent.getArmorEquipped(), armorEquippedEvent.getPlayerId())
                    .subscribe();
        else if(armorEquippedEvent.getTypeEvent().equals("ArmorUnEquipped"))
            unEquipArmorUseCase.apply(armorEquippedEvent.getArmorEquipped(), armorEquippedEvent.getPlayerId())
                    .subscribe();
    }

}
