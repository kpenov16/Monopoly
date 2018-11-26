package dk.monopoly.common;

import dk.monopoly.ports.RollingDiceResponse;

import static dk.monopoly.ports.RollingDiceResponse.PreviousFieldType.CHANCE;
import static dk.monopoly.ports.RollingDiceResponse.PreviousFieldType.NORMAL_ROLL;

public class RollingDicePresenterImpl implements RollingDicePresenter {
    private MonopolyGui monopolyGui;
    private RollingDiceViewModel vm;
    public RollingDicePresenterImpl(MonopolyGui monopolyGui, RollingDiceViewModel vm) {
        this.monopolyGui = monopolyGui;
        this.vm = vm;
    }

    @Override
    public void present(RollingDiceResponse response) {
        vm.callingFieldType =  response.callingField;
      if(response.playerPaysRent!=null && response.playerPaysRent) {
          vm.msg = response.playerName + ": " + response.msg + ", has landlord: " + response.landlord.name;
          vm.landlordName = response.landlord.name;
          vm.landlordBalance = response.landlord.balance;
          vm.playerPaysRent = response.playerPaysRent;
      }else {
          vm.msg = response.playerName + ": " + response.msg;
      }
      vm.firstDie = response.firstDie;
      vm.secondDie = 1;
      vm.balance = response.balance;
      vm.isWinner = response.isFinished;
      vm.currentFieldIndex = response.currentFieldIndex;
      monopolyGui.update();

    }
}
