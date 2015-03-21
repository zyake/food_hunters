package jp.co.aainc.training_camp.team_mizuno.food_hunters;

import android.app.Fragment;
import android.view.View;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class HistoryFragment extends Fragment {

    private String TAG = "HistoryFragment";

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        Log.i(TAG, "Added");
        View view =  inflater.inflate(R.layout.fragment_history, container, false);

        String[] members = { "お店", "お店", "kacchi0516", "kobashinG",
                "seit", "kei_i_t", "furusin_oriver" };
        ListView list = (ListView)view.findViewById(R.id.history_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_expandable_list_item_1, members);

        list.setAdapter(adapter);

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
