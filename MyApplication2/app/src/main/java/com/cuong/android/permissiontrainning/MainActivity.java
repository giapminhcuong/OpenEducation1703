package com.cuong.android.permissiontrainning;

import android.Manifest;
import android.app.VoiceInteractor;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_PICK_IMAGE = 1;
    private Button mButtonSelectImage;

    private String mImageUrl = "http://media.phunutoday.vn/files/upload_images/2014/06/21/ngoc-trinh3.jpg";
    private ImageView mImageViewGirl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonSelectImage = (Button) findViewById(R.id.button_select_image);
        mButtonSelectImage.setOnClickListener(this);

        mImageViewGirl = (ImageView) findViewById(R.id.image_view_girl);

        Glide.with(this)
                .load(mImageUrl)
                .into(mImageViewGirl);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_select_image) {
            requestPermission();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PICK_IMAGE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "OK bcd", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void requestPermission() {
        int isGrant = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (isGrant == PackageManager.PERMISSION_GRANTED) {
            // truong hop nay da duoc cap quyen
            getImages();
        } else if (isGrant == PackageManager.PERMISSION_DENIED) {
            // truong hop nay chua duoc cap
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                (new AlertDialog.Builder(this))
                        .setTitle("Doc di")
                        .setMessage("Accept mau")
                        .setNegativeButton("Cancel", null)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PICK_IMAGE);
                            }
                        })
                        .create()
                        .show();
            } else {

            }
        }
    }

    public void getImages() {
//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(intent, REQUEST_PICK_IMAGE);

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME};

        Cursor c = getContentResolver()
                .query(uri, projection, null, null, null);
        if (c != null && c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(MediaStore.Images.Media._ID));
                String name = c.getString(c.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                Log.d("MainActivity", id + " - " + name);
            } while (c.moveToNext());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PICK_IMAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getImages();
            } else {
                Toast.makeText(this, "Oi buon qua", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
