package com.cuong.android.android_12_menu_searchview_dialog;

import android.content.DialogInterface;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerViewContact;
    ContactAdapter adapter;
    private Button mButtonCheckContextMenu;
    private DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerViewContact = (RecyclerView) findViewById(R.id.recycler_view_contact);
        mRecyclerViewContact.setLayoutManager(new LinearLayoutManager(this));

        mDatabaseHelper = new DatabaseHelper(this);

        List<Contact> contactList = mDatabaseHelper.getAllContacts();

        adapter = new ContactAdapter(contactList, this);
        mRecyclerViewContact.setAdapter(adapter);

//        mButtonCheckContextMenu = (Button) findViewById(R.id.button_check_context_menu);
//        registerForContextMenu(mButtonCheckContextMenu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contact, menu);
        MenuItem itemSearch = menu.findItem(R.id.item_menu_search);
        SearchView searchView = (SearchView) itemSearch.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_menu_add_contact:
                openDialogAddContact();
                Toast.makeText(this, "Menu Add Click", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openDialogAddContact() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View v = getLayoutInflater().inflate(R.layout.dialog_insert_contact, null);

        final EditText mEditTextName = (EditText) v.findViewById(R.id.edit_text_name);
        final EditText mEditTextPhone = (EditText) v.findViewById(R.id.edit_text_phone);
        final EditText mEditTextAddress = (EditText) v.findViewById(R.id.edit_text_address);


        builder.setTitle("Add");
        builder.setMessage("Input information");
        builder.setView(v);
        builder.setNegativeButton(android.R.string.no, null);
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                int id = databaseHelper.getNewId();
                Contact contact = new Contact(id, mEditTextName.getText().toString(),
                        mEditTextPhone.getText().toString(),
                        mEditTextAddress.getText().toString());
                if (databaseHelper.insertContact(contact)) {
                    adapter.addUser(contact);
                    mRecyclerViewContact.scrollToPosition(0);
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu_del_edit, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_menu_del:
                Toast.makeText(this, "Delete Item Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_menu_edit:
                Toast.makeText(this, "Edit Item Selected", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        ContactAdapter.ViewHolder holder = (ContactAdapter.ViewHolder) view.getTag();
        final int pos = holder.getAdapterPosition();
        PopupMenu popupMenu = new PopupMenu(this, view);
        getMenuInflater().inflate(R.menu.context_menu_del_edit, popupMenu.getMenu());
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_menu_del:
                        ConfirmDelDialog dialog = new ConfirmDelDialog();
                        dialog.setup(adapter, pos);
                        dialog.show(MainActivity.this.getSupportFragmentManager(), "");
                        break;
                    case R.id.item_menu_edit:
                        openUpdateContactDialog(pos);
                        Toast.makeText(MainActivity.this, "Edit Item Selected in Context Menu", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    private void openUpdateContactDialog(final int pos) {
        final Contact contact = adapter.getContact(pos);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View v = getLayoutInflater().inflate(R.layout.dialog_insert_contact, null);

        final EditText mEditTextName = (EditText) v.findViewById(R.id.edit_text_name);
        final EditText mEditTextPhone = (EditText) v.findViewById(R.id.edit_text_phone);
        final EditText mEditTextAddress = (EditText) v.findViewById(R.id.edit_text_address);

        mEditTextName.setText(contact.getName());
        mEditTextPhone.setText(contact.getPhone());
        mEditTextAddress.setText(contact.getAddress());


        builder.setTitle("Update");
        builder.setMessage("Change information");
        builder.setView(v);
        builder.setNegativeButton(android.R.string.no, null);
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                contact.setName(mEditTextName.getText().toString());
                contact.setPhone(mEditTextPhone.getText().toString());
                contact.setAddress(mEditTextAddress.getText().toString());
                if (databaseHelper.updateContact(contact)) {
                    adapter.notifyItemChanged(pos);
                    mRecyclerViewContact.scrollToPosition(pos);
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Back")
                .setMessage("Bạn có muốn back không?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity.super.onBackPressed();
                    }
                })
                .create().show();
    }
}
