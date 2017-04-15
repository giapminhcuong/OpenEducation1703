package com.cuong.android.android_12_menu_searchview_dialog;

import java.util.Calendar;
import java.util.Random;

/**
 * Created by Admin on 13/4/2017.
 */

public class Contact {
    private String mName;
    private String mPhone;
    private String mAddress;

    public Contact(String name, String phone, String address) {
        mName = name;
        mPhone = phone;
        mAddress = address;
    }

    public Contact() {
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public static Contact getRandomContact() {
        Random random = new Random(System.currentTimeMillis());
        int index = random.nextInt(100);
        index = (index < 0) ? -index : index;
        return new Contact("Giáp Minh Cương " + index, "0123456 " + random.nextInt(100), "Gốc đề, Hà nội");
    }
}
