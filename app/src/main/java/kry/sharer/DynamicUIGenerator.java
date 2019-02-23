package kry.sharer;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class DynamicUIGenerator {

    private Context ctx;

    public DynamicUIGenerator(Context ctx) {
        this.ctx = ctx;
    }

    public void generateDynamicUI(LinearLayout linearLayout, List<String> numbers) {
        for (String number : numbers) {
            addNewRowWithLinearLayout(linearLayout, number);
        }
    }

    private void addNewRowWithLinearLayout(LinearLayout parentLayout, String number) {
        LinearLayout newLL = new LinearLayout(ctx);
        // newLL.setBackgroundColor(Color.DKGRAY);
        newLL.setOrientation(LinearLayout.HORIZONTAL);
        newLL.setGravity(Gravity.CENTER_HORIZONTAL);
        LinearLayout.LayoutParams LLParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        newLL.setLayoutParams(LLParams);
        parentLayout.addView(newLL);

        addTextView(newLL, number);
        addButton(newLL, "r");
        addButton(newLL, "g");
    }

    private void addButton(LinearLayout linearLayout, String text) {
        ContextThemeWrapper newContext = new ContextThemeWrapper(ctx, R.style.Widget_AppCompat_Button_Small);
        final Button newButton = new Button(newContext);
        newButton.setText(text);
        newButton.setMinHeight(0);
        newButton.setMinimumHeight(0);
        newButton.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ctx,
                        newButton.getText(), Toast.LENGTH_LONG).show();
            }
        });
        linearLayout.addView(newButton);
    }

    private void addTextView(LinearLayout linearLayout, String text) {
        TextView valueTV = new TextView(ctx);
        valueTV.setText(text);
        valueTV.setHeight(100);
        valueTV.setPadding(0, 0, 50, 0);
        valueTV.setId(text.hashCode());
        valueTV.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.addView(valueTV);
    }
}
