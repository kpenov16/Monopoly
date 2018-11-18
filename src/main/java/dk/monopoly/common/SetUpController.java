package dk.monopoly.common;

public class SetUpController {
    private SetUpViewModel vm;
    private SetUpImpl setUpImpl;

    public SetUpController(SetUpViewModel vm, SetUpImpl setUpImpl) {
        this.vm = vm;
        this.setUpImpl = setUpImpl;
    }

    public void execute() {
        setUpImpl.execute(vm.playersNames);
    }
}
