package com.cuong.android.permissiontrainning;

import android.Manifest;
import android.app.VoiceInteractor;
import android.content.Context;
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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_PICK_IMAGE = 1;
//    private Button mButtonSelectImage;
//
//    private String mImageUrl = "http://media.phunutoday.vn/files/upload_images/2014/06/21/ngoc-trinh3.jpg";
//    private ImageView mImageViewGirl;

    RecyclerView mRecyclerViewPhoto;
    List<Picture> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
//        mButtonSelectImage = (Button) findViewById(R.id.button_select_image);
//        mButtonSelectImage.setOnClickListener(this);
//
//        mImageViewGirl = (ImageView) findViewById(R.id.image_view_girl);
//
//        Uri uri = Uri.parse(MediaStore.Images.Media.EXTERNAL_CONTENT_URI + "/" + "10737");
//
//        Glide.with(this)
//                .loadFromMediaStore(uri)
//                .into(mImageViewGirl);

        mRecyclerViewPhoto = (RecyclerView) findViewById(R.id.recycler_view_photo);
        mRecyclerViewPhoto.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        list = new ArrayList<>();
        requestPermission();
        PhotoAdapter adapter = new PhotoAdapter(list, this);
        mRecyclerViewPhoto.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
//        if (view.getId() == R.id.button_select_image) {
//            requestPermission();
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_PICK_IMAGE) {
//            if (resultCode == RESULT_OK) {
//                Toast.makeText(this, "OK bcd", Toast.LENGTH_SHORT).show();
//            }
//        }
    }

    public void requestPermission() {
        int isGrant = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (isGrant == PackageManager.PERMISSION_GRANTED) {
            // truong hop nay da duoc cap quyen
            list = getImages();
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

    public List<Picture> getImages() {
//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(intent, REQUEST_PICK_IMAGE);

        List<Picture> list = new ArrayList<>();

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME};

        Cursor c = getContentResolver()
                .query(uri, projection, null, null, null);
        if (c != null && c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(MediaStore.Images.Media._ID));
                String name = c.getString(c.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                list.add(new Picture(id, name));
//                Log.d("MainActivity", "uri=" + MediaStore.Images.Media.getContentUri(name));
//                Log.d("MainActivity", id + " - " + name);
            } while (c.moveToNext());
        }
        return list;
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
