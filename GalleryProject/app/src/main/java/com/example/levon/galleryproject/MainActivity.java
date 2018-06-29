package com.example.levon.galleryproject;

import android.app.usage.ExternalStorageStats;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imageView;
    private static final int REQUEST_FOR_GALLERY = 128;
    private static final String PHOTO_PATH = "https://www.gettyimages.ca/gi-resources/images/Homepage/Hero/UK/CMS_Creative_164657191_Kingfisher.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1 = findViewById(R.id.buttondrow);
        Button button2 = findViewById(R.id.buttonurl);
        Button button3 = findViewById(R.id.buttongallery);
        imageView = findViewById(R.id.openimage);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttondrow:
                imageView.setImageResource(R.drawable.aaa);
                break;
            case R.id.buttonurl:
                Picasso.get().load(PHOTO_PATH).into(imageView);
                break;
            case R.id.buttongallery:
                Intent intent = new Intent(Intent.ACTION_PICK);
                File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.MEDIA_CHECKING);
                String pictureDirectoryPath = pictureDirectory.getPath();
                Uri data = Uri.parse(pictureDirectoryPath);
                intent.setDataAndType(data, "image/*");
                startActivityForResult(intent, REQUEST_FOR_GALLERY);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_FOR_GALLERY && data != null) {
                Uri imagePath = data.getData();
                InputStream inputStream;
                try {
                    inputStream = getContentResolver().openInputStream(imagePath);
                    Bitmap image = BitmapFactory.decodeStream(inputStream);
                    imageView.setImageBitmap(image);
                } catch (FileNotFoundException e) {
                    Toast.makeText(this, R.string.exceptiontoast, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
