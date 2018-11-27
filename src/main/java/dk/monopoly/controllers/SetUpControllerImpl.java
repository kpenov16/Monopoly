package dk.monopoly.controllers;

import dk.monopoly.gui.viewmodels.SetUpViewModel;
import dk.monopoly.usecases.setup.SetUpController;
import dk.monopoly.usecases.setup.SetUpImpl;

public class SetUpControllerImpl implements SetUpController {
    private SetUpViewModel vm;
    private SetUpImpl setUpImpl;

    public SetUpControllerImpl(SetUpViewModel vm, SetUpImpl setUpImpl) {
        this.vm = vm;
        this.setUpImpl = setUpImpl;
    }
    @Override
    public void execute() {
        setUpImpl.execute(vm.playersNames);
    }
}
