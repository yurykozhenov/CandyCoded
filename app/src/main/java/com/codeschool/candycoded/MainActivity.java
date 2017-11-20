package com.codeschool.candycoded;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<String> candyList = new ArrayList<>();

        candyList.add("Tropical Wave");
        candyList.add("Berry Bouncer");
        candyList.add("Grape Gummer");
        candyList.add("Apple of My Eye");
        candyList.add("Much Minty");
        candyList.add("So Fresh");
        candyList.add("Sassy Sandwich Cookie");
        candyList.add("Uni-pop");
        candyList.add("Strawberry Surprise");
        candyList.add("Wish Upon a Star");
        candyList.add("Watermelon Like Whoa");
        candyList.add("Twist 'n' Shout");
        candyList.add("Beary Squad Goals");
        candyList.add("ROYGBIV Spinner");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.list_item_candy,
                R.id.text_view_candy,
                candyList
        );

        ListView listView = this.findViewById(R.id.list_view_candy);
        listView.setAdapter(adapter);

        Context context = this;
        String text = "Hello toast!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int i, long l) {
//                Toast toast = Toast.makeText(MainActivity.this, "" + i, Toast.LENGTH_SHORT);
//                toast.show();
                Intent detailIntent = new Intent(MainActivity.this, DetailActivity.class);
                detailIntent.putExtra("candy_name", candyList.get(i));
                startActivity(detailIntent);
            }
        });
    }
}
