package jp.co.aainc.training_camp.team_mizuno.food_hunters;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TableRow;


public class ScoreRanksActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_ranks);

        // TableLayoutのグループを取得
        ViewGroup vg = (ViewGroup)findViewById(R.id.tableLayout1);

        for (int i=1; i<20; i++) {
            // 行を追加
            getLayoutInflater().inflate(R.layout.table_row, vg);
            // 文字設定
            TableRow tr = (TableRow)vg.getChildAt(i);
            String[] name = {"アライド太郎", "山田太郎", "アライド三郎", "アライド四郎",
                    "アライド太郎2", "アライド次郎2","アライド三郎2","アライド四郎2",
                    "アライド太郎3","アライド次郎3", "アライド三郎3","アライド四郎3",
                    "アライド太郎4","アライド次郎4", "アライド三郎4","アライド四郎4",
                    "アライド太郎5","アライド次郎5", "アライド三郎5","アライド四郎5"};
            String[] score = {"12,350","10,800","10,220","9,700",
                    "8,600","8,340","7,905","7,705",
                    "7,505","7,320","7,100","6,700",
                    "6,300","5,800","5,600","5,000",
                    "4,700","4,400","4,000","3,000"};
            String name_tag = "<a href='#'>" + name[i - 1] + "</a>";
            ((TextView)(tr.getChildAt(0))).setText(String.valueOf(i));
            ((TextView)(tr.getChildAt(1))).setText(Html.fromHtml(name_tag));
            ((TextView)(tr.getChildAt(2))).setText(score[i-1]);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_score_ranks, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
