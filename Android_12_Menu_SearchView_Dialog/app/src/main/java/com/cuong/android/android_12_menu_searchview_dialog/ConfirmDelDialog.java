package com.cuong.android.android_12_menu_searchview_dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Admin on 15/4/2017.
 */

public class ConfirmDelDialog extends DialogFragment {
    private ContactAdapter mContactAdapter;
    private int mPos;

    public void setup(ContactAdapter adapter, int posDel) {
        mContactAdapter = adapter;
        mPos = posDel;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Xác nhận");
        builder.setMessage("Bạn có muốn xóa:\n\"" + mContactAdapter.getContact(mPos).getName() + "\"?");
        builder.setIcon(R.drawable.del);
        builder.setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mContactAdapter.delUser(mPos);
                Toast.makeText(getActivity(), "Delete Item Selected in Context Menu", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        return builder.create();
    }
}
