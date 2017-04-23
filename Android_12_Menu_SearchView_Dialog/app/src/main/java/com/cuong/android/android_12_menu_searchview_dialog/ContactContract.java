package com.cuong.android.android_12_menu_searchview_dialog;

import android.provider.BaseColumns;

/**
 * Created by Admin on 17/4/2017.
 */

public class ContactContract {

    private ContactContract(){};

    public class ContactEntry implements BaseColumns {
        public static final String TABLE_NAME = "tbl_contact";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_ADDRESS = "address";

    }
}
