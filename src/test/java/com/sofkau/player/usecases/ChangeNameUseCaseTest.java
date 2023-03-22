package com.sofkau.player.usecases;

import com.sofkau.player.domain.collection.Player;
import com.sofkau.player.domain.dto.PlayerDTO;
import com.sofkau.player.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static reactor.core.publisher.Mono.when;

@ExtendWith(MockitoExtension.class)
class ChangeNameUseCaseTest {

    @Mock
    private PlayerRepository playerRepository;
    private ChangeNameUseCase changeNameUseCase;
    private ModelMapper modelMapper;

    @BeforeEach
    public void setup() {
        modelMapper = new ModelMapper();
        changeNameUseCase = new ChangeNameUseCase(playerRepository, modelMapper);
    }

    @Test
    public void successfulChangeName() {
        //Arrange
        var id = "1";
        var name = "Juan";

        Player player = new Player();
        player.setId(id);
        player.setName("Pedro");
        player.setRaze("Human");

        Player player2 = new Player();
        player2.setId(id);
        player2.setName("Juan");
        player2.setRaze("Human");


        //Mock
        Mockito.when(playerRepository.findById(id)).thenReturn(Mono.just(player));
        Mockito.when(playerRepository.save(player)).thenReturn(Mono.just(player2));

        //Act
        var result = changeNameUseCase.apply(id, name);

        //Assert
        StepVerifier.create(result)
                .expectNextMatches(playerDTO1 -> Objects.equals(playerDTO1.getName(), name))
                .verifyComplete();
    }

}