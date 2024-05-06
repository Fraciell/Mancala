package board;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.TreeSet;

@Getter
@Setter
public class MancalaAction {

    int targetPit;

    public MancalaAction(int targetPit) {
        this.targetPit = targetPit;
    }

    public static Set<MancalaAction> getPlayableActionsFromPits(int[] playerPits) {
        Set<MancalaAction> possibleActions = new TreeSet<>();
        for (int i = 0; i < playerPits.length; i++) {
            if (playerPits[i] > 0) {
                possibleActions.add(new MancalaAction(i));
            }
        }
        return possibleActions;
    }
}
