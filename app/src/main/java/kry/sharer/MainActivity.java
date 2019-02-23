package kry.sharer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import kry.sharer.DynamicUIGenerator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout linearLayout = findViewById(R.id.main_layout);
        linearLayout.setPadding(0, 200, 0, 0);

        List<String> numbers = new ArrayList<>();
        numbers.add("123-123-125");
        numbers.add("456-456-456");
        numbers.add("878-678-678");

        new DynamicUIGenerator(this).generateDynamicUI(linearLayout, numbers);

    }
}
