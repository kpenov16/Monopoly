package dk.monopoly.ports;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InfoServiceTest {
    private InfoService infoService;

    @BeforeEach
    void setUp() {
        infoService = Context.createInfoService("DK");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void given2Points_returnScoreIsPositive250(){
        //arrange
        //act
        infoService.setPoints(2);

        //assert
        assertEquals(250, infoService.getScore());
        assertEquals("Tower", infoService.getLocation());
        assertEquals("Du har fundet Tower og får 250 kr, du er rig!", infoService.getMessage());
    }

    @Test
    public void given3Points_returnScoreIsNegative100(){
        //act
        infoService.setPoints(3);

        //assert
        assertEquals(-100, infoService.getScore());
        assertEquals("Crater", infoService.getLocation());
        assertEquals("Du har fundet Crater og får -100 kr, du er ikke rig!", infoService.getMessage());
    }

    @Test
    public void givenInvalidPoints_returnScoreIsZero(){
        //act
        infoService.setPoints(1);

        //assert
        assertEquals(0, infoService.getScore());
        assertEquals("", infoService.getLocation());
        assertEquals("", infoService.getMessage());
    }
}