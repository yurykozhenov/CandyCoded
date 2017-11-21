package com.codeschool.candycoded;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = DetailActivity.this.getIntent();

        String candyName = "";
        if (intent.hasExtra("candy_name")) {
            candyName = intent.getStringExtra("candy_name");
        }

        String candyImage = "";
        if (intent.hasExtra("candy_image")) {
            candyImage = intent.getStringExtra("candy_image");
        }

        String candyPrice = "";
        if (intent.hasExtra("candy_price")) {
            candyPrice = intent.getStringExtra("candy_price");
        }

        String candyDesc = "";
        if (intent.hasExtra("candy_desc")) {
            candyDesc = intent.getStringExtra("candy_desc");
        }

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
