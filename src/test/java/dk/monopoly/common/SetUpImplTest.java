package dk.monopoly.common;

import dk.monopoly.ports.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SetUpImplTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

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