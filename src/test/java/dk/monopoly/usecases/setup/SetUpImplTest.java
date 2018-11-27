package dk.monopoly.usecases.setup;

import dk.monopoly.entities.Field;
import dk.monopoly.entities.Player;
import dk.monopoly.gateways.FieldGateway;
import dk.monopoly.gateways.PlayerGateway;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SetUpImplTest {
    @Test
    public void givenNumberOfPlayers_returnCorrectStartBalance(){
        SetUpImpl setUpImpl = new SetUpImpl(new FakeSetUpPresenter(), new FakePlayerGateway(), new FakeFieldGateway());

        assertEquals(2000,setUpImpl.getStartBalance(2));
        assertEquals(1800,setUpImpl.getStartBalance(3));
        assertEquals(1600,setUpImpl.getStartBalance(4));
    }

    private class FakeSetUpPresenter implements SetUpPresenter {
        @Override
        public void sendSuccessMsg() {}
        @Override
        public void execute(List<Integer> balances) {}
    }
    private class FakePlayerGateway implements PlayerGateway {
        @Override
        public Player getPlayerMyName(String playerName) { return null; }
        @Override
        public void addPlayer(Player player) { }

        @Override
        public List<Player> getAllPlayersByNameExcept(String playerName) {
            return null;
        }

        @Override
        public List<Player> getAllPlayers() {
            return null;
        }
    }
    private class FakeFieldGateway implements FieldGateway {
        @Override
        public Field getFieldByIndex(int fieldIndex) {
            return null;
        }

        @Override
        public void addField(Field field) {

        }
    }
}