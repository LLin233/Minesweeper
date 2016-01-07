package ll.minesweeper.entities;

import android.util.Log;

import java.util.Random;

/**
 * Created by Le on 2015/12/30.
 */
public class Minefield {
    MineEntity[][] mField;
    private int level;
    private int mineCount;

    private MineEntity edgeCell = new MineEntity(false, 0, false, true);

    public Minefield(int level) {
        this.level = level;
        mField = new MineEntity[level + 2][level + 2];
        mineCount = 10;
        initField();
    }

    public void initField() {
        for (int i = 0; i < level + 2; i++) {
            for (int j = 0; j < level + 2; j++) {
                if (i == 0 || j == 0 || i == level + 1 || j == level + 1) {
                    mField[i][j] = edgeCell;
                } else {
                    mField[i][j] = new MineEntity();
                }
            }
        }
        Random rand = new Random(System.currentTimeMillis());
        //create mines
        for (int x = 0; x < mineCount; x++) {
            int i = rand.nextInt(level) + 1;
            int j = rand.nextInt(level) + 1;
            if (mField[i][j].isMine()) {
                x--;
                continue;
            } else {
                mField[i][j].setMine(true);
            }
        }
        for (int i = 1; i <= level; i++) {
            for (int j = 1; j <= level; j++) {
                setMineCount(mField[i][j], i, j);
            }
        }
        syso();
    }

    private void setMineCount(MineEntity entity, int i, int j) {
        int count = 0;
        for (int ii = i - 1; ii <= i + 1; ii++) {
            for (int jj = j - 1; jj <= j + 1; jj++) {
                if (mField[ii][jj].isMine())
                    count++;
            }
        }
        entity.setMineCount(count);
    }

    /**
     * get mine instance which in the position
     */
    public MineEntity getEntity(int position) {
        MineEntity entity = null;
        entity = mField[(position) / level + 1][position % level + 1];
        return entity;
    }

    public boolean isWin() {
        int count = 0;
        for (int i = 1; i <= level; i++) {
            for (int j = 1; j <= level; j++) {
                if (!mField[i][j].isShow() || mField[i][j].isFlag()){
                    count++;
                }
            }
        }
        Log.v("Validate game, block ", count + "");
        return count == mineCount;
    }

    /**
     * Print out the whole field
     */
    private void syso() {
        for (int i = 1; i <= level; i++) {
            for (int j = 1; j <= level; j++) {
                if (mField[i][j].isMine()) {
                    System.out.print("*");
                } else if (mField[i][j].getMineCount() == 0) {
                    System.out.print("-");
                } else {
                    System.out.print(mField[i][j].getMineCount());
                }
            }
            System.out.println();
        }
    }


    public void showMines() {
        for (int i = 1; i <= level; i++) {
            for (int j = 1; j <= level; j++) {
                if (mField[i][j].isMine() && !mField[i][j].isShow()) {
                    mField[i][j].setShow(true);
                    mField[i][j].setAutoShow(true);
                }
            }
        }
    }

    public void showBlankArea(int position) {
        if (position >= level * level || position < 0) {
            return;
        }
        int i = position / level + 1;
        int j = position % level + 1;
        if (mField[i][j].isBoundary()) {
            return;
        }
        if (mField[i][j].getMineCount() != 0 || mField[i][j].isShow()) {
            mField[i][j].setShow(true);
            return;
        }
        mField[i][j].setShow(true);
        for (int ii = i - 1; ii <= i + 1; ii++) {
            for (int jj = j - 1; jj <= j + 1; jj++) {
                if (ii <= 0 || jj <= 0 || ii >= level + 1 || jj >= level + 1) {
                    continue;
                }
                showBlankArea((ii - 1) * level + (jj - 1));
            }
        }
    }

    public void showFlag() {
        for (int i = 1; i <= level; i++) {
            for (int j = 1; j <= level; j++) {
                if (mField[i][j].isFlag() && mField[i][j].isMine()) {
                    mField[i][j].setFlagWrong(false);
                } else if (mField[i][j].isFlag() && !mField[i][j].isMine()) {
                    mField[i][j].setFlagWrong(true);
                }
            }
        }
    }

    public void showCheatField() {
        for (int i = 1; i <= level; i++) {
            for (int j = 1; j <= level; j++) {
                if (mField[i][j].isMine() && !mField[i][j].isShow()) {
                    mField[i][j].setShow(true);
                }
            }
        }
    }

    public void hideField() {
        for (int i = 1; i <= level; i++) {
            for (int j = 1; j <= level; j++) {
                if (mField[i][j].isMine() && !mField[i][j].isFlag()) {
                    mField[i][j].setShow(false);
                }
            }
        }
    }
}
