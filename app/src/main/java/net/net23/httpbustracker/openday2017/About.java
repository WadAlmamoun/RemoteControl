package net.net23.httpbustracker.openday2017;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }
        public void fb(View view)
        {
            Intent fbIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/Computer-Engineering-Association-Future-University-1330561803691037/?pnref=story"));
            startActivity(fbIntent);
        }

        public void insta(View view)
        {
            Intent instaIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/wad_almamoun/"));
            startActivity(instaIntent);
        }


        public void web(View view)
        {
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://futureu.edu.sd/"));
            startActivity(webIntent);
        }
    }
