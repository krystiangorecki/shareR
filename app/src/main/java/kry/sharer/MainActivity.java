package kry.sharer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kry.sharer.DynamicUIGenerator;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "ShareR.MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get intent, action and MIME type
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            } else {
                Log.e(TAG, "unhandled type : " + type);
                TextView tv = (TextView) findViewById(R.id.status);
                tv.setText(type);
            }
        } else if (Intent.ACTION_VIEW.equals(action) || Intent.ACTION_MAIN.equals(action)) {
            // normalne uruchomienie aplikacji
            TextView tv = (TextView) findViewById(R.id.status);
            tv.setText(".");
        } else {
            Log.e(TAG, "unhandled action: " + action);
            TextView tv = (TextView) findViewById(R.id.status);
            tv.setText(action);
        }

    }

    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            processSharedText(sharedText);
        }
    }

    private void processSharedText(String numberText) {
        // 123-123-123  asdf asdf 111 111 111 asdfasd fasd 777888999
        Pattern p = Pattern.compile("(\\d{3}.+?\\d{3}.+?\\d{3})|\\d{9}");
        numberText = numberText.replaceAll(Pattern.quote("+48"), "");
        Matcher m = p.matcher(numberText);
        Set<String> numbers = new HashSet<>();
        while (m.find()) {
            Log.e(TAG, "found: " + m.group());
            // clean number from nonnumbers
            String cleanNumber = m.group().replaceAll("\\D+", "");
            //insert dashes
            numbers.add(cleanNumber.substring(0, 3) + "-" + cleanNumber.substring(3, 6) + "-" + cleanNumber.substring(6, 9));
        }
        TextView tv = (TextView) findViewById(R.id.status);
        if (numbers.isEmpty()) {
            tv.setText("no numbers");
            finish();
            return;
        }

        LinearLayout linearLayout = findViewById(R.id.main_layout);
        linearLayout.setPadding(0, 200, 0, 0);
        new DynamicUIGenerator(this).generateDynamicUI(linearLayout, numbers);

    }

}
