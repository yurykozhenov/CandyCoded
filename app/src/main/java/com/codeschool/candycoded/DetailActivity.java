package com.codeschool.candycoded;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.codeschool.candycoded.CandyContract.CandyEntry;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = DetailActivity.this.getIntent();

        if (intent.hasExtra("position")) {
            int position = intent.getIntExtra("position", 0);

            CandyDbHelper candyDbHelper = new CandyDbHelper(this);
            SQLiteDatabase db = candyDbHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM candy", null);
            cursor.moveToPosition(position);

            String candyName = cursor.getString(cursor.getColumnIndexOrThrow(CandyEntry.COLUMN_NAME_NAME));
            String candyPrice = cursor.getString(cursor.getColumnIndexOrThrow(CandyEntry.COLUMN_NAME_PRICE));
            String candyImage = cursor.getString(cursor.getColumnIndexOrThrow(CandyEntry.COLUMN_NAME_IMAGE));
            String candyDesc = cursor.getString(cursor.getColumnIndexOrThrow(CandyEntry.COLUMN_NAME_DESC));

            ImageView imageView = this.findViewById(R.id.image_view_candy);
            Picasso.with(this).load(candyImage).into(imageView);

            TextView textViewName = this.findViewById(R.id.text_view_name);
            textViewName.setText(candyName);

            TextView textViewPrice = this.findViewById(R.id.text_view_price);
            textViewPrice.setText("$" + candyPrice + "/lb");

            TextView textViewDesc = this.findViewById(R.id.text_view_desc);
            textViewDesc.setText(candyDesc);
        }
    }
}
