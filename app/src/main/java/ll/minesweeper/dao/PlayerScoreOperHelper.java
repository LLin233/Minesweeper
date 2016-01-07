package ll.minesweeper.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Le on 2015/12/30.
 * We might need to store the rank data...just in case create this
 */

public class PlayerScoreOperHelper extends SQLiteOpenHelper {
    private static final String CREATE_TABLE = "create table score_info(_id integer primary key autoincrement,player_name text, score integer)";

    public PlayerScoreOperHelper(Context context, int version) {
        super(context, "score_info", null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }
}
