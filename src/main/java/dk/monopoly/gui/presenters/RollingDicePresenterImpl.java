package dk.monopoly.gui.presenters;

import dk.monopoly.gui.viewmodels.RollingDiceViewModel;
import dk.monopoly.entities.RollingDiceResponse;
import dk.monopoly.usecases.rollingdice.RollDiceView;
import dk.monopoly.usecases.rollingdice.RollingDicePresenter;

public class RollingDicePresenterImpl implements RollingDicePresenter {
    private RollDiceView view;
    private RollingDiceViewModel vm;
    public RollingDicePresenterImpl(RollDiceView view, RollingDiceViewModel vm) {
        this.view = view;
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
      view.update();

    }
}
