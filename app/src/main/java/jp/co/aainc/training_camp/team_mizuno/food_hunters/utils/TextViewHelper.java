package jp.co.aainc.training_camp.team_mizuno.food_hunters.utils;

import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.view.View;
import android.widget.TextView;

public class TextViewHelper {

    private final View parentView;

    public TextViewHelper(View parentView) {
        this.parentView = parentView;
    }

    public TextViewHelper setText(int resource, CharSequence text) {
        TextView textView = (TextView) parentView.findViewById(resource);
        if (text != null) {
            textView.setText(text);
        } else {
            textView.setText("");
        }
        return this;
    }

    public TextViewHelper setLinkText(int resource, Spanned html) {
        TextView textView = (TextView) parentView.findViewById(resource);
        if (html != null) {
            textView.setText(html);
        } else {
            textView.setText("");
        }
        MovementMethod movementMethod = LinkMovementMethod.getInstance();
        textView.setMovementMethod(movementMethod);
        return this;
    }
}
