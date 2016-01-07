package ll.minesweeper.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import ll.minesweeper.R;
import ll.minesweeper.activities.adapter.MineAdapter;
import ll.minesweeper.dao.PlayerScoreDAO;
import ll.minesweeper.entities.MineEntity;
import ll.minesweeper.utils.VibratorUtil;


/**
 * Created by Le on 2015/12/30.
 */

public class MainActivity extends AppCompatActivity {

    private static final int LEVEL = 8;

    private boolean isCheated = false;
    private GridView gv;
    private MineAdapter adapter;
    private Button startBtn;
    private Timer timer;
    private int howTime;
    private ImageView faceIcon;
    private static Handler handler;
    private TextView showTimeTv;
    private boolean isGameing = false;

    private static final int MESSAGE_UPDATE_TIME = 1;

    private void initView() {
        showTimeTv = (TextView) findViewById(R.id.time);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == MESSAGE_UPDATE_TIME) {
                    showTimeTv.setText("Time: " + howTime + " s");
                }
            }
        };
        faceIcon = (ImageView) findViewById(R.id.faceIcon);
        startBtn = (Button) findViewById(R.id.startbtn);
        gv = (GridView) findViewById(R.id.gv);

        timer = new Timer();
        adapter = new MineAdapter(LEVEL, this, gv);
        gv.setNumColumns(LEVEL);
        gv.setAdapter(adapter);
    }


    private void startGame() {
        isCheated = false;
        timer.cancel();
        timer = new Timer();
        isGameing = true;
        howTime = 0;
        adapter = new MineAdapter(LEVEL, MainActivity.this, gv);
        gv.setAdapter(adapter);
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                howTime++;
                handler.sendEmptyMessage(MESSAGE_UPDATE_TIME);
            }
        }, 0, 1000);
    }


    private void stopGame() {
        timer.cancel();
        isGameing = false;
    }

    private void addListener() {

        startBtn.setOnClickListener(new OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                faceIcon.setImageResource(R.mipmap.happyface);
                startGame();
            }
        });

        faceIcon.setOnClickListener(new OnClickListener() {
                    @SuppressLint("NewApi")
                    @Override
                    public void onClick(View v) {
                        faceIcon.setImageResource(R.mipmap.happyface);
                        startGame();
                    }
                });

        /**long click to add flag*/
        gv.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                if (!isGameing) {     //would not work if game doesn't start
                    return true;
                }
                MineEntity entity = adapter.getItem(position);
                if (entity.isShow() && !entity.isMine()) {
                    return true;
                }
                entity.setFlag(!entity.isFlag());
                entity.setShow(false);
                adapter.notifyDataSetChanged();
                //vibrate device as add flag successfully
                VibratorUtil.Vibrate(MainActivity.this, 500);
                return true;
            }

        });
        gv.setOnItemClickListener(new OnItemClickListener() {

            @SuppressLint("NewApi")
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (!isGameing) {   //would not work if game doesn't start
                    return;
                }
                MineEntity entity = adapter.getItem(position);
                if (entity.isFlag()) {    //flaged
                    return;
                }
                if (!entity.isShow()) {
                    if (entity.isMine()) {    //mined
                        entity.setShow(true);
                        stopGame();
                        faceIcon.setImageResource(R.mipmap.sadface);
                        Toast.makeText(MainActivity.this, "You lose, please start over!", Toast.LENGTH_SHORT).show();
                        adapter.showMines();
                        adapter.showFlag();
                        return;
                    }

                    if (entity.getMineCount() == 0 && !entity.isMine()) {
                        adapter.showBlankArea(position);
                    }
                    entity.setShow(true);

                    if (adapter.isWin()) {
                        Log.v("Validate gmae ", "win");
                        stopGame();
                        Toast.makeText(MainActivity.this, "You win!", Toast.LENGTH_SHORT).show();
                    }
                    adapter.notifyDataSetChanged();
                }
            }

        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        addListener();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_about) {
            Snackbar.make(findViewById(R.id.root_main_layout), "This demo is created by Leonard Lin for the interview given by thumbtack", Snackbar.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void cheatGame(View v) {
        if (!isGameing) return;
        if (isCheated) {
            adapter.hideFiled();
            isCheated = false;
        } else {
            adapter.showFiled();
            isCheated = true;
        }
    }

}
