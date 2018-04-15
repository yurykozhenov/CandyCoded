package com.codeschool.candycoded;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.codeschool.candycoded.CandyContract.CandyEntry;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_POSITION = "position";
    private Candy[] candies;
    private CandyDbHelper candyDbHelper = new CandyDbHelper(this);
    private CandyCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase db = candyDbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM candy", null);
        adapter = new CandyCursorAdapter(this, cursor);

        ListView listView = this.findViewById(R.id.list_view_candy);
        listView.setAdapter(adapter);

        AsyncHttpClient client = new AsyncHttpClient();

        client.get("https://s3.amazonaws.com/courseware.codeschool.com/super_sweet_android_time/API/CandyCoded.json", new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                Log.d("AsyncHttpClient", "response = " + response);
                Gson gson = new GsonBuilder().create();
                candies = gson.fromJson(response, Candy[].class);

                removeOldCandiesFromDatabase();
                addCandiesToDatabase(candies);

                SQLiteDatabase db = candyDbHelper.getWritableDatabase();
                Cursor cursor = db.rawQuery("SELECT * FROM candy", null);
                adapter.changeCursor(cursor);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String response,
                                  Throwable throwable) {
                Log.e("AsyncHttpClient", "response = " + response);
            }
        });

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
                detailIntent.putExtra(EXTRA_POSITION, i);
                startActivity(detailIntent);
            }
        });
    }

    public void removeOldCandiesFromDatabase() {
        SQLiteDatabase db = candyDbHelper.getWritableDatabase();

        db.delete(CandyEntry.TABLE_NAME, "", new String[] {});
    }

    public void addCandiesToDatabase(Candy[] candies) {
        SQLiteDatabase db = candyDbHelper.getWritableDatabase();

        for (Candy candy : candies) {
            ContentValues values = new ContentValues();

            values.put(CandyEntry.COLUMN_NAME_NAME, candy.name);
            values.put(CandyEntry.COLUMN_NAME_PRICE, candy.price);
            values.put(CandyEntry.COLUMN_NAME_DESC, candy.description);
            values.put(CandyEntry.COLUMN_NAME_IMAGE, candy.image);

            db.insert(CandyEntry.TABLE_NAME, null, values);
        }
    }
}
