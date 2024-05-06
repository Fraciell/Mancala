package board;


import at.ac.tuwien.ifs.sge.game.ActionRecord;
import at.ac.tuwien.ifs.sge.game.Game;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class Mancala implements Game<MancalaAction, MancalaBoard> {

    private MancalaBoard mancalaBoard;

    private CurrentPlayer currentPlayer = CurrentPlayer.PLAYER_1;

    private final List<ActionRecord<MancalaAction>> actionRecords;


    private boolean canonical = true;


    public Mancala() {
        this.mancalaBoard = new MancalaBoard();
        this.actionRecords = new ArrayList<>();
    }

    public Mancala(int numberOfPits, int numberOfSeeds) {
        this.mancalaBoard = new MancalaBoard(numberOfPits, numberOfSeeds);
        this.actionRecords = new ArrayList<>();

    }

    public Mancala(MancalaBoard mancalaBoard) {
        this.mancalaBoard = mancalaBoard;
        this.actionRecords = new ArrayList<>();
    }

    public Mancala(String board, int numberOfPlayers) {
        this(6, 4);
        if (numberOfPlayers != 2) {
            throw new IllegalArgumentException("2 player game");
        }
    }


    public Mancala(int currentPlayer, boolean canonical, List<ActionRecord<MancalaAction>> actionRecords,
                   MancalaBoard board) {
        if (currentPlayer == 0) {
            this.currentPlayer = CurrentPlayer.PLAYER_1;
        } else {
            this.currentPlayer = CurrentPlayer.PLAYER_2;
        }
        this.actionRecords = actionRecords;
        this.mancalaBoard = board;
        this.canonical = canonical;
    }

    public Mancala(Mancala mancala) {
        this.mancalaBoard = mancala.mancalaBoard;
        this.actionRecords = mancala.actionRecords;
    }

    @Override
    public int getMinimumNumberOfPlayers() {
        return 2;
    }

    @Override
    public int getMaximumNumberOfPlayers() {
        return 2;
    }

    @Override
    public int getNumberOfPlayers() {
        return 2;
    }

    @Override
    public double getUtilityValue(int i) {
        if (i == 0) {
            return mancalaBoard.player1Loft;
        } else {
            return mancalaBoard.player2Loft;
        }
    }


    @Override
    public Set<MancalaAction> getPossibleActions() {
        return MancalaAction.getPlayableActionsFromPits(mancalaBoard.getPlayerPits(currentPlayer));
    }

    @Override
    public MancalaBoard getBoard() {
        return mancalaBoard;
    }

    @Override
    public Game<MancalaAction, MancalaBoard> doAction(MancalaAction mancalaAction) {

        Mancala next = new Mancala(this);


        int[] currentPlayerPits = mancalaBoard.getPlayerPits(currentPlayer);
        int currentPlayerLoft = mancalaBoard.getPlayerLoft(currentPlayer);
        int[] oppositePlayerPits = mancalaBoard.getPlayerPits(currentPlayer.nextPlayer());
        int pitPointer = mancalaAction.targetPit;
        int pitLimit = currentPlayerPits.length;
        int seeds = currentPlayerPits[pitPointer];

        currentPlayerPits[pitPointer] = 0;
        pitPointer++;

        while (seeds > 0) {
            // ADD TO CURRENT PITS
            for (int i = 0; i < pitLimit - pitPointer; i++) {
                if (seeds > 0) {
                    currentPlayerPits[i] += 1;
                    seeds--;
                }
            }

            // ADD TO CURRENT LOFT
            if (seeds > 0) {
                currentPlayerLoft += 1;
                seeds--;
            }

            // ADD TO ENEMY PITS
            for (int i = 0; i < pitLimit; i++) {
                if (seeds > 0) {
                    oppositePlayerPits[i] += 1;
                    seeds--;
                }
            }
        }
        MancalaBoard newBoardState;
        if (this.currentPlayer == CurrentPlayer.PLAYER_1) {
            newBoardState = new MancalaBoard(currentPlayerPits, currentPlayerLoft, oppositePlayerPits, mancalaBoard.getPlayerLoft(currentPlayer.nextPlayer()));
            next.actionRecords.add(new ActionRecord<>(0, mancalaAction));

        } else {
            newBoardState = new MancalaBoard(oppositePlayerPits, mancalaBoard.getPlayerLoft(currentPlayer.nextPlayer()), currentPlayerPits, currentPlayerLoft);
            next.actionRecords.add(new ActionRecord<>(1, mancalaAction));

        }

        next.mancalaBoard = newBoardState;
        next.currentPlayer = this.currentPlayer.nextPlayer();

        if (isGameOver()) {
            System.out.println("BİTTİ");
        }

        return next;
    }

    @Override
    public int getCurrentPlayer() {
        if (this.currentPlayer == CurrentPlayer.PLAYER_1) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public MancalaAction determineNextAction() {
        return null;
    }

    @Override
    public List<ActionRecord<MancalaAction>> getActionRecords() {
        return Collections.unmodifiableList(actionRecords);
    }

    @Override
    public boolean isCanonical() {
        return this.canonical;
    }

    @Override
    public Game<MancalaAction, MancalaBoard> getGame(int i) {
        return this;
    }


    @Override

    public boolean isGameOver() {
        return mancalaBoard.isOneOfThePitsEmpty();
    }

    public void nextPlayer() {
        // BU BU ŞEKİLDE DEĞİŞTİRİYOR MU PLAYER IDYİ YOKSA DİREK VARİABLEYE ASSİGNLAMAN MI LAZIM BAKMAYI UNUTMA.
        this.currentPlayer.nextPlayer();
    }
}
