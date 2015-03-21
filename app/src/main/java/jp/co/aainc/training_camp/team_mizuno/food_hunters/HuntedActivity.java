package jp.co.aainc.training_camp.team_mizuno.food_hunters;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TableRow;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View;

public class HuntedActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hunted);

        // TableLayoutのグループを取得
        Button okButton = (Button)findViewById(R.id.ok_button);
        okButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

}
