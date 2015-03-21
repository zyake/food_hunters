package jp.co.aainc.training_camp.team_mizuno.food_hunters.utils;

import android.view.View;
import android.widget.TextView;

public class TextViewHelper {

    private final View parentView;

    public TextViewHelper(View parentView) {
        this.parentView = parentView;
    }

    public TextViewHelper setText(int resource, String text) {
        TextView textView = (TextView) parentView.findViewById(resource);
        if (text != null) {
            textView.setText(text);
        } else {
            textView.setText("");
        }
        return this;
    }
}
