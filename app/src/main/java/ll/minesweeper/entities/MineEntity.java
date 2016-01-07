package ll.minesweeper.entities;

/**
 * Created by Le on 2015/12/30.
 */
public class MineEntity {

    private boolean isShow;
    private int mineCount;
    private boolean isMine;
    private boolean isBoundary;
    private boolean isAutoShow = false;
    private boolean isFlag;
    private boolean isFlagWrong;

    public boolean isFlagWrong() {
        return isFlagWrong;
    }

    public void setFlagWrong(boolean isBiaoJiWrong) {
        this.isFlagWrong = isBiaoJiWrong;
    }

    public boolean isFlag() {
        return isFlag;
    }

    public void setFlag(boolean flag) {
        this.isFlag = flag;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean isShow) {
        this.isShow = isShow;
    }

    public int getMineCount() {
        return mineCount;
    }

    public void setMineCount(int mineCount) {
        this.mineCount = mineCount;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean isLei) {
        this.isMine = isLei;
    }

    public boolean isBoundary() {
        return isBoundary;
    }

    public void setBoundary(boolean isBian) {
        this.isBoundary = isBian;
    }

    public MineEntity(boolean isShow, int mineCount, boolean isMine,
                      boolean isBoundary) {
        super();
        this.isShow = isShow;
        this.mineCount = mineCount;
        this.isMine = isMine;
        this.isBoundary = isBoundary;
    }

    public MineEntity() {
        this.isShow = false;
    }

    public void setAutoShow(boolean isAutoShow) {
        this.isAutoShow = isAutoShow;
    }

    public boolean isAutoShow() {
        return isAutoShow;
    }

}
