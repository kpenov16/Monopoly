package dk.monopoly.ports;

import java.util.List;

public interface SetUpPresenter {
    public void sendSuccessMsg();
    public void execute(List<Integer> balances);
}
