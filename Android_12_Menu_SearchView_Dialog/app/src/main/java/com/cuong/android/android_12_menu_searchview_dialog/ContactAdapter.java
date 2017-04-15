package com.cuong.android.android_12_menu_searchview_dialog;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 13/4/2017.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private List<Contact> mContactList;
    private List<Contact> mContactListBackup;   // tham chieu tam den toan bo contact
    private Context mContext;

    public ContactAdapter(List<Contact> contactList, Context context) {
        mContactListBackup = contactList;
        mContactList = mContactListBackup;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_contact, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Contact contact = mContactList.get(position);
        holder.bindData(contact);
    }

    @Override
    public int getItemCount() {
        return (mContactList != null) ? mContactList.size() : 0;
    }

    public void addUser(Contact contact) {
        mContactListBackup.add(0, contact);
        if (mContactListBackup.equals(mContactList)) notifyItemInserted(0);
    }

    public void getFilter(String query) {
        query = query.toLowerCase();
        Log.d("AAA", "query = " + query);
        if (query.isEmpty()) {
            mContactList = mContactListBackup;
            notifyDataSetChanged();
            return;
        }

        mContactList = new ArrayList<>();
        for (int i = 0; i < mContactListBackup.size(); i++) {
            Contact contact = mContactListBackup.get(i);
            String name = contact.getName().toLowerCase();
            String phone = contact.getPhone().toLowerCase();
            String address = contact.getAddress().toLowerCase();
            if (name.contains(query)
                    || phone.contains(query)
                    || address.contains(query)) {
                mContactList.add(contact);
            }
        }

        notifyDataSetChanged();
    }

    public void delUser(int pos) {
        boolean deleted = false;
        Contact contact = mContactList.get(pos);
        for (int i=0; i< mContactListBackup.size(); i++) {
            if (mContactListBackup.get(i).equals(contact)) {
                mContactListBackup.remove(i);
                deleted = true;
                break;
            }
        }
        for (int i=0; i< mContactList.size(); i++) {
            if (mContactList.get(i).equals(contact)) {
                mContactList.remove(i);
                break;
            }
        }
        if (deleted) notifyItemRemoved(pos);
    }

    public Contact getContact(int pos) {
        return mContactList.get(pos);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageViewAvatar;
        private TextView mTextViewName;
        private TextView mTextViewPhone;
        private TextView mTextViewAddress;

        public ViewHolder(View itemView) {
            super(itemView);
            ((MainActivity) mContext).registerForContextMenu(itemView);
            itemView.setOnClickListener(((MainActivity) mContext));
            mImageViewAvatar = (ImageView) itemView.findViewById(R.id.image_view_avatar);
            mTextViewName = (TextView) itemView.findViewById(R.id.text_view_name);
            mTextViewAddress = (TextView) itemView.findViewById(R.id.text_view_address);
            mTextViewPhone = (TextView) itemView.findViewById(R.id.text_view_phone);
            itemView.setTag(this);
        }

        public void bindData(Contact contact) {
            if (contact == null) return;
            mTextViewName.setText(contact.getName());
            mTextViewPhone.setText(contact.getPhone());
            mTextViewAddress.setText(contact.getAddress());
        }
    }
}
