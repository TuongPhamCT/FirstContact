package com.example.buoi6contact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class NewDanhBa extends AppCompatActivity {
    EditText editName;
    EditText editPhone;
    Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_danh_ba_screen);

        editName = (EditText) findViewById(R.id.nameinput);
        editPhone = (EditText) findViewById(R.id.phoneinput);
        btnSave = (Button) findViewById(R.id.savebtn);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editName.getText().toString();
                String phone = editPhone.getText().toString();
                addContact(name, phone);
                startActivity(new Intent(NewDanhBa.this, MainActivity.class));
            }
        });
    }

    private void addContact(String name, String phone) {
        // Create a new contact insert operation
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        // Add the raw contact
        ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());

        // Add the contact display name
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name)
                .build());

        // Add the contact phone number
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phone)
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                .build());

        try {
            // Apply the batch operation
            ContentProviderResult[] results = getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);

            // Retrieve the contact ID
            long contactId = ContentUris.parseId(results[0].uri);

            // Display success message or handle further actions
            // ...
        } catch (Exception e) {
            // Handle the exception or display error message
            // ...
        }
    }
}