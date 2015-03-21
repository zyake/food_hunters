package jp.co.aainc.training_camp.team_mizuno.food_hunters.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.view.View;
import android.widget.TextView;

public class ViewHelper {

    private final View parentView;

    public static void activateBrowser(Context context, String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(browserIntent);
    }

    public ViewHelper(View parentView) {
        this.parentView = parentView;
    }

    public ViewHelper setText(int resource, CharSequence text) {
        TextView textView = (TextView) parentView.findViewById(resource);
        if (text != null) {
            textView.setText(text);
        } else {
            textView.setText("");
        }
        return this;
    }

    public ViewHelper setLinkText(int resource, Spanned html) {
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
