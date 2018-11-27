package dk.monopoly.common;

import dk.monopoly.entities.impls.MonopolyPlayer;
import dk.monopoly.entities.Player;
import dk.monopoly.gateways.PlayerGateway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerGatewayImplTest {
    private PlayerGateway playerGateway = null;

    @BeforeEach
    void setUp() {
        playerGateway = new PlayerGatewayImpl();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void getAllPlayersExceptPlayer(){
        //arrange
        MonopolyPlayer lily = (MonopolyPlayer) Context.createPlayer("Lily");
        MonopolyPlayer lucy = (MonopolyPlayer) Context.createPlayer("Lucy");
        MonopolyPlayer bob = (MonopolyPlayer) Context.createPlayer("Bob");
        playerGateway.addPlayer(lily);
        playerGateway.addPlayer(lucy);
        playerGateway.addPlayer(bob);

        //act
        List<Player> playersResponse = playerGateway.getAllPlayersByNameExcept(lucy.getName());

        //assert
        assertEquals(2, playersResponse.size());
        assertEquals(false, playersResponse.contains(lucy));
        assertEquals(true, playersResponse.contains(lily));
        assertEquals(true, playersResponse.contains(bob));
    }

}