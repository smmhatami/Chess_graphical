package game;

public class Chessman {
    private game.Player owner;
    private char type;
    private int movesDone;
    private game.BoardUnitSquare deathSpot;
    private game.BoardUnitSquare currentPosition;


    public game.Player getOwner() {
        return owner;
    }

    public Chessman(game.Player owner, char type, game.BoardUnitSquare currentPosition) {
        this.owner = owner;
        this.type = type;
        movesDone = 0;
        deathSpot = null;
        this.currentPosition = currentPosition;
    }

    public void setCurrentPosition(game.BoardUnitSquare currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void changeMoved(int count) {
        movesDone += count;
    }

    public game.BoardUnitSquare getCurrentPosition() {
        return currentPosition;
    }

    public boolean isMovePossible(game.BoardUnitSquare startSquare, game.BoardUnitSquare destinationSquare) {
        if (destinationSquare.getChessmanPlayer() == this.owner)
            return false;
        int frontDistance = tellFrontDistance(startSquare, destinationSquare);
        int rightDistance = tellRightDistance(startSquare, destinationSquare);

        if (type == 'P')
            return moveValidatePawn(startSquare, destinationSquare, owner, frontDistance, rightDistance);
        if (type == 'R')
            return moveValidateRook(startSquare, destinationSquare, frontDistance, rightDistance);
        if (type == 'N')
            return moveValidateKnight(frontDistance, rightDistance);
        if (type == 'B')
            return moveValidateBishop(startSquare, destinationSquare, frontDistance, rightDistance);
        if (type == 'Q')
            return moveValidateQueen(startSquare, destinationSquare, frontDistance, rightDistance);
        //if (type == 'K')
        return moveValidateKing(frontDistance, rightDistance);
    }

    @Override
    public String toString() {
        return Character.toString(type) + owner.getColor();
    }

    public String killedToString() {
        return this + " killed in spot " + deathSpot;
    }

    public void setDeathSpot(game.BoardUnitSquare deathSpot) {
        this.deathSpot = deathSpot;
    }

    public char getType() {
        return type;
    }

    private boolean moveValidatePawn(game.BoardUnitSquare startSquare, game.BoardUnitSquare destinationSquare, game.Player owner, int frontDistance, int rightDistance) {
        int playerMovingSide = owner.getColor() == 'w' ? 1 : -1;
        if (destinationSquare.getCurrentChessman() == null) {
            if (startSquare.getSquareFrontRow(playerMovingSide).getCurrentChessman() != null || rightDistance != 0)
                return false;
            if (frontDistance == 2 * playerMovingSide)
                return movesDone == 0;
            return frontDistance == playerMovingSide;
        }
        return (frontDistance == playerMovingSide) && (rightDistance == 1 || rightDistance == -1);
    }

    private boolean moveValidateRook(game.BoardUnitSquare startSquare, game.BoardUnitSquare destinationSquare, int frontDistance, int rightDistance) {
        if (frontDistance * rightDistance != 0)
            return false;
        game.BoardUnitSquare square = startSquare;
        if (frontDistance > 0) {
            while ((square = square.getSquareFrontRow(1)) != destinationSquare) {
                if (square.getCurrentChessman() != null)
                    return false;
            }
        }
        if (frontDistance < 0) {
            while ((square = square.getSquareFrontRow(-1)) != destinationSquare) {
                if (square.getCurrentChessman() != null)
                    return false;
            }
        }
        if (rightDistance > 0) {
            while ((square = square.getSquareRightRow(1)) != destinationSquare) {
                if (square.getCurrentChessman() != null)
                    return false;
            }
        }
        if (rightDistance < 0) {
            while ((square = square.getSquareRightRow(-1)) != destinationSquare) {
                if (square.getCurrentChessman() != null)
                    return false;
            }
        }
        return true;
    }

    private boolean moveValidateKing(int frontDistance, int rightDistance) {
        return frontDistance <= 1 && frontDistance >= -1 && rightDistance <= 1 && rightDistance >= -1;
    }

    private boolean moveValidateQueen(game.BoardUnitSquare startSquare, game.BoardUnitSquare destinationSquare, int frontDistance, int rightDistance) {
        if (frontDistance * rightDistance == 0)
            return moveValidateRook(startSquare, destinationSquare, frontDistance, rightDistance);
        return moveValidateBishop(startSquare, destinationSquare, frontDistance, rightDistance);
    }

    private boolean moveValidateBishop(game.BoardUnitSquare startSquare, game.BoardUnitSquare destinationSquare, int frontDistance, int rightDistance) {
        game.BoardUnitSquare square = startSquare;
        if (frontDistance == rightDistance) {
            if (frontDistance > 0) {
                while ((square = square.getSquareVerticalUpRight(1)) != destinationSquare) {
                    if (square.getCurrentChessman() != null)
                        return false;
                }
                return true;
            }
            if (frontDistance < 0) {
                while ((square = square.getSquareVerticalUpRight(-1)) != destinationSquare) {
                    if (square.getCurrentChessman() != null)
                        return false;
                }
                return true;
            }
        }
        if (frontDistance == -rightDistance) {
            if (frontDistance > 0) {
                while ((square = square.getSquareVerticalUpLeft(1)) != destinationSquare) {
                    if (square.getCurrentChessman() != null)
                        return false;
                }
                return true;
            }
            if (frontDistance < 0) {
                while ((square = square.getSquareVerticalUpLeft(-1)) != destinationSquare) {
                    if (square.getCurrentChessman() != null)
                        return false;
                }
                return true;
            }
        }
        return false;
    }

    private boolean moveValidateKnight(int frontDistance, int rightDistance) {
        return frontDistance * rightDistance == 2 || frontDistance * rightDistance == -2;
    }

    private static int tellRightDistance(game.BoardUnitSquare startSquare, game.BoardUnitSquare destinationSquare) {
        return destinationSquare.getPosition()[1] - startSquare.getPosition()[1];
    }

    private static int tellFrontDistance(game.BoardUnitSquare startSquare, game.BoardUnitSquare destinationSquare) {
        return destinationSquare.getPosition()[0] - startSquare.getPosition()[0];
    }
}
