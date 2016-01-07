package ll.minesweeper.activities.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import ll.minesweeper.R;
import ll.minesweeper.entities.MineEntity;
import ll.minesweeper.entities.Minefield;

/**
 * Created by Le on 2015/12/30.
 */
public class MineAdapter extends BaseAdapter {
    private int level;
    private Minefield entity;
    private Context context;
    private GridView gv;

    public MineAdapter(int level, Context context, GridView gv) {
        this.level = level;
        entity = new Minefield(level);
        this.context = context;
        this.gv = gv;
    }

    @Override
    public int getCount() {
        return level * level;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_img, null);
        }
        ((ImageView) convertView).setImageResource(getRes(getItem(position)));
        AbsListView.LayoutParams param = new AbsListView.LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                gv.getWidth() / level);   //using gridview to compute width, ensure the field is square
        convertView.setLayoutParams(param);
        return convertView;
    }

    /**
     * Display different resource base on the cell status
     */
    private int getRes(MineEntity entity) {
        int id = 0;
        if (entity.isFlag() && !entity.isFlagWrong()) {
            id = R.mipmap.i_flag;
        } else if (entity.isFlag() && entity.isFlagWrong()) {
            id = R.mipmap.i14;
        } else if (!entity.isShow()) {
            id = R.mipmap.i00;
        } else if (entity.isMine() && entity.isAutoShow()) {
            id = R.mipmap.i12;
        } else if (entity.isMine() && !entity.isAutoShow()) {
            id = R.mipmap.i13;
        } else if (entity.getMineCount() == 0) {
            id = R.mipmap.i09;
        } else {
            id = context.getResources().getIdentifier("i0" + entity.getMineCount(), "mipmap", context.getPackageName());
        }
        return id;
    }

    public boolean isWin() {
        return entity.isWin();
    }

    @Override
    public MineEntity getItem(int position) {
        return entity.getEntity(position);
    }

    public void showMines() {
        entity.showMines();
        notifyDataSetChanged();
    }

    public void showBlankArea(int position) {
        entity.showBlankArea(position);
        notifyDataSetChanged();
    }

    public void showFlag() {
        entity.showFlag();
        notifyDataSetChanged();
    }

    public void showFiled() {
        entity.showCheatField();
        notifyDataSetChanged();
    }

    public void hideFiled() {
        entity.hideField();
        notifyDataSetChanged();
    }
}
