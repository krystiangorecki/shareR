package kry.sharer;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity /*-implements MyRecyclerViewAdapter.ItemClickListener niepotrzebne klikanie na cały wiersz */ {

    private static final String TAG = "ShareR.MainActivity";

    private MyRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if ((intent.getFlags() & PendingIntent.FLAG_CANCEL_CURRENT) != PendingIntent.FLAG_CANCEL_CURRENT)
        {
            // Restarting the same activity with different flags to compensate for different behaviour 
            // when sharing selected text and when sharing link with phone number.
            intent.setFlags(PendingIntent.FLAG_CANCEL_CURRENT);
            startActivity(intent);
        }

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            } else {
                Log.e(TAG, "unhandled type : " + type);
                TextView tv = findViewById(R.id.status);
                tv.setText(type);
            }
        } else if (Intent.ACTION_VIEW.equals(action) || Intent.ACTION_MAIN.equals(action)) {
            // normalne uruchomienie aplikacji
            TextView tv = findViewById(R.id.status);
            tv.setText(".");
        } else {
            Log.e(TAG, "unhandled action: " + action);
            TextView tv = findViewById(R.id.status);
            tv.setText(action);
        }

        /*-
        pole MyRecyclerViewAdapter adapter;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // data to populate the RecyclerView with
        ArrayList<String> animalNames = new ArrayList<>();
        animalNames.add("Horse");
        animalNames.add("Cow");
        animalNames.add("Camel");
        animalNames.add("Sheep");
        animalNames.add("Goat");
        animalNames.add("Sheep");
        animalNames.add("Goat");

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvAnimals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, animalNames);
        // adapter.setClickListener(this); // klikanie na cały wiersz - niepotrzebne
        recyclerView.setAdapter(adapter);*/
    }

    // klikanie na cały wiersz - niepotrzebne
    /*-@Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }*/


    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            processSharedText(sharedText);
        }
    }

    private void processSharedText(String numberText) {
        Set<String> numbers = parseNumbersFromText(numberText);
        TextView tv = findViewById(R.id.status);
        if (numbers.isEmpty()) {
            tv.setText("no numbers");
            finish();
            return;
        }

        generateUIElements(numbers);
    }

    public Set<String> parseNumbersFromText(String numberText) {
        // Łąkowa 555 taki adres 123-123-123  asdf asdf 111 111 111 asdfasd fasd 777888999
        Pattern p = Pattern.compile("(\\d{3}\\D{0,3}\\d{3}\\D{0,3}\\d{3})");
        numberText = numberText.replaceAll(Pattern.quote("+48"), "");
        Matcher m = p.matcher(numberText);
        Set<String> numbers = new LinkedHashSet<>();
        while (m.find()) {
            Log.d(TAG, "found: " + m.group());
            // clean number from nonnumbers
            String cleanNumber = m.group().replaceAll("\\D+", "");
            // insert dashes
            numbers.add(cleanNumber.substring(0, 3) + "-" + cleanNumber.substring(3, 6) + "-" + cleanNumber.substring(6, 9));
        }
        return numbers;
    }

    private void generateUIElements(Set<String> numbers) {
        // set up the RecyclerView
        RecyclerView recyclerView = this.findViewById(R.id.rvNumbers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, new ArrayList<String>());
        // adapter.setClickListener(this); // klikanie na cały wiersz - niepotrzebne
        recyclerView.setAdapter(adapter);

        for (String number : numbers) {
            adapter.addItem(number);
        }
        adapter.notifyDataSetChanged();
    }
}
