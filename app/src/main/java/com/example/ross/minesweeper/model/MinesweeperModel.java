package com.example.ross.minesweeper.model;

import com.example.ross.minesweeper.MainActivity;

public class MinesweeperModel {

    private static MinesweeperModel instance = null;
    private static int numMines;
    private static int GAMESIZE;
    private int[][] model;
    private int[][] visible;

    private MinesweeperModel() {
        newGame();
    }

    public void newGame() {
        resizeBoard();
        setNumMines();
        createModel();
        createVisibilityModel();
    }

    public void resizeBoard() {
        GAMESIZE = MainActivity.GAMESIZE;
    }

    public static MinesweeperModel getInstance() {
        if (instance == null) {
            instance = new MinesweeperModel();
        }
        return instance;
    }

    public static final short EMPTY = 0;
    public static final short ONE = 1;
    public static final short TWO = 2;
    public static final short THREE = 3;
    public static final short FOUR = 4;
    public static final short MINE = 5;

//    private int[][] model = createModel();
    private void setNumMines() {
        if (GAMESIZE == 5) numMines = 3;
        if (GAMESIZE == 8) numMines = 7;
        if (GAMESIZE == 10) numMines = 10;
    }

    public int getNumMines() {
        return numMines;
    }

    private void createModel() {
        if (GAMESIZE == 5) {
            model = new int[][]{
                    {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                    {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                    {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                    {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                    {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY}
            };
        }
        else if (GAMESIZE == 8) {
            model = new int[][]{
                    {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                    {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                    {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                    {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                    {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                    {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                    {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                    {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY}
            };
        }
        else if (GAMESIZE == 10) {
            model = new int[][]{
                    {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                    {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                    {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                    {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                    {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                    {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                    {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                    {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                    {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                    {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY}
            };
        }
    }

    private void createVisibilityModel() {
        if (GAMESIZE == 5) {
            visible = new int[][]{
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0}
            };
        }
        if (GAMESIZE == 8) {
            visible = new int[][]{
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0}
            };
        }
        if (GAMESIZE == 10) {
            visible = new int[][]{
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
            };
        }
    }

    public int getFieldContent(int x, int y) { return model[x][y]; }

    public void setFieldContent(int x, int y, int type) { model[x][y] = type;}

    public boolean isVisible(int x, int y) { return visible[x][y] == 1; }

    public boolean isMine(int x, int y) {return getFieldContent(x,y) == MINE;}

    public void markVisible(int x, int y) {
        visible[x][y] = 1;

        if (getFieldContent(x,y) == EMPTY) {
            revealMore(x,y);
        }
    }

    public void revealMore(int x, int y) {
        if (x>0) {
            if (!isVisible(x-1,y)) markVisible(x-1, y);
            if (y>0) {
                if (!isVisible(x-1,y-1))markVisible(x-1, y-1);
            }
            if (y<GAMESIZE-1) {
                if (!isVisible(x-1,y+1)) markVisible(x-1, y+1);
            }
        }
        if (y>0) {
            if (!isVisible(x,y-1)) markVisible(x, y-1);
        }
        if (y<GAMESIZE-1) {
            if (!isVisible(x,y+1)) markVisible(x, y+1);
        }

        if (x<GAMESIZE-1) {
            if (!isVisible(x+1,y)) markVisible(x+1, y);
            if (y>0) {
                if (!isVisible(x+1,y-1)) markVisible(x+1, y-1);
            }
            if (y<GAMESIZE-1) {
                if (!isVisible(x+1,y+1)) markVisible(x+1, y+1);
            }
        }
    }

    public void markInvisible(int x, int y) {
        visible[x][y] = 0;
    }

    public void resetModel() {
        for (int i=0; i<GAMESIZE; i++) {
            for (int j=0; j<GAMESIZE; j++) {
                setFieldContent(i,j,EMPTY);
                markInvisible(i,j);
            }
        }
        setNumMines();
    }

    public int getMines() { return numMines; }

    public void decrementMines() { numMines-=1; }

}
