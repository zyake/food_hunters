package jp.co.aainc.training_camp.team_mizuno.food_hunters;

import android.app.Fragment;
import android.text.Html;
import android.view.View;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

public class RankingFragment extends Fragment {

    private String TAG = "RankingFragment";

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        Log.i(TAG, "Added");

        View view = inflater.inflate(R.layout.activity_score_ranks, container, false);

        // TableLayoutのグループを取得
        ViewGroup vg = (ViewGroup)view.findViewById(R.id.tableLayout1);

        for (int i=1; i<20; i++) {
            // 行を追加
            inflater.inflate(R.layout.table_row, vg);
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

        return view;
    }
    /*

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.i(TAG, "Added");

        String[] history = { "A","N" };

        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, history);
        setListAdapter(adapter);

    }

     */
}
