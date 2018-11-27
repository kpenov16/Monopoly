package dk.monopoly.usecases.setup;

import java.util.List;

public interface SetUpPresenter {
    void sendSuccessMsg();
    void execute(List<Integer> balances);
}
