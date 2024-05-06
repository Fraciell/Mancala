# Mancala

### Questions
What is isCanonical?

    @Override
    public int getCurrentPlayer() {
        return 0;
    }

    @Override
    public double getUtilityValue(int i) {
        return 0;
    }

    @Override
    public Set<MancalaAction> getPossibleActions() {
        return null;
    }

    @Override
    public MancalaBoard getBoard() {
        return null;
    }

    @Override
    public Game<MancalaAction, MancalaBoard> doAction(MancalaAction mancalaAction) {
        return null;
    }

    @Override
    public MancalaAction determineNextAction() {
        return null;
    }

    @Override
    public List<ActionRecord<MancalaAction>> getActionRecords() {
        return null;
    }

    @Override
    public boolean isCanonical() {
        return false;
    }

    @Override
    public Game<MancalaAction, MancalaBoard> getGame(int i) {
        return null;
    }