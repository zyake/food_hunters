package jp.co.aainc.training_camp.team_mizuno.food_hunters;

import android.app.Fragment;
import android.view.View;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
public class HistoryFragment extends Fragment {

    private String TAG = "HistoryFragment";

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        Log.i(TAG, "Added");
        return inflater.inflate(R.layout.fragment_history, container, false);
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
