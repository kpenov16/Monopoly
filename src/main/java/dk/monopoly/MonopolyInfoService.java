package dk.monopoly;

import dk.monopoly.ports.InfoService;

public class MonopolyInfoService extends InfoService {
    private MessageBag messageBag;

    public MessageBag getMessageBag() {
        return messageBag;
    }

    public void setMessageBag(MessageBag messageBag) {
        this.messageBag = messageBag;
    }


    @Override
    public void setPoints(int points) {
        super.points = points;
        evaluate();
    }

    private void evaluate() {
        switch (points){
            case 2:{
                score = +250;
                location = "Tower";
                message = messageBag.towerMessage;//"Du har fundet Tower og får 250 kr, du er rig!";
                break;
            }
            case 3:{
                score = -100;
                location = "Crater";
                message = messageBag.craterMessage;//"Du har fundet Crater og får -100 kr, du er ikke rig!";
                break;
            }
            case 4:{
                score = +100;
                location = "Palace gates";
                message = messageBag.palaceGatesMessage;//"Du har fundet Palace gates og får +100 kr, du er rig!";
                break;
            }
            case 5:{
                score = -20;
                location = "Cold Desert";
                message = messageBag.coldDesertMessage;//"Du har fundet Cold Desert og får -20 kr, du er ikke rig!";
                break;
            }
            case 6:{
                score = +180;
                location = "Walled city";
                message = messageBag.walledCityMessage;//"Du har fundet Walled city og får +180 kr, du er rig!";
                break;
            }
            case 7:{
                score = +0;
                location = "Monastery";
                message = messageBag.monasteryMessage;//"Du har fundet Monastery og får +0 kr !";
                break;
            }
            case 8:{
                score = -70;
                location = "Black cave";
                message = messageBag.blackCaveMessage;//"Du har fundet Black cave og får -70 kr, du er ikke rig!";
                break;
            }
            case 9:{
                score = +60;
                location = "Huts in the mountain";
                message = messageBag.hutsInTheMountainMessage;//"Du har fundet Huts in the mountain og får +60 kr, du er rig!";
                break;
            }
            case 10:{
                score = -80;
                location = "The Werewall";
                message = messageBag.theWerewallMessage;//"Du har fundet The Werewall in the mountain og får -80 kr, du er ikke rig!";
                break;
            }
            case 11:{
                score = -50;
                location = "The pit";
                message = messageBag.thePitMessage;//"Du har fundet The pit in the mountain og får -50 kr, du er ikke rig!";
                break;
            }
            case 12:{
                score = +650;
                location = "Goldmine";
                message = messageBag.goldMineMessage;//"Du har fundet Goldmine in the mountain og får +650 kr, du er ikke rig!";
                break;
            }
            default:
                score = 0;
                location = "";
                message = "";
                break;
        }
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getLocation() {
        return location;
    }

}
