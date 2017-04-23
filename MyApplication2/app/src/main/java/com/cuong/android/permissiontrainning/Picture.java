package com.cuong.android.permissiontrainning;

import android.net.Uri;
import android.provider.MediaStore;

/**
 * Created by Admin on 23/4/2017.
 */

public class Picture {
    int id;
    String name;

    public Picture() {
    }

    public Picture(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getUri() {
        return Uri.parse(MediaStore.Images.Media.EXTERNAL_CONTENT_URI + "/" + id);
    }
}
