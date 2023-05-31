package com.example.buoi6contact;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private final int CONTACT_LOADER = 0;

    Boolean isASC = true;
    DanhBaAdapter adapter;
    ListView lvDB;
    public final int READ_CONTACTS_REQUEST_CODE = 0;
    public final int WRITE_CONTACTS_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_CONTACTS
        }, READ_CONTACTS_REQUEST_CODE);
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_CONTACTS
        }, WRITE_CONTACTS_REQUEST_CODE);

        lvDB = (ListView) findViewById(R.id.mainlist);
        List<DanhBa> list = new ArrayList<>();
        adapter = new DanhBaAdapter(getApplicationContext(), R.layout.danhbaitem, list);
        lvDB.setAdapter(adapter);

        LoaderManager.getInstance(this).restartLoader(this.CONTACT_LOADER,null,this);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permission, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permission, grantResults);
        if (requestCode == READ_CONTACTS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //
                // doc contact
            }
            else {
                // permission denied
            }
        }
        if (requestCode == WRITE_CONTACTS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //
                //save contact
            }
            else {
                // permission denied
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.itemAsc:
                isASC = true;
                break;
            case R.id.itemDesc:
                isASC = false;
                break;
            case R.id.itemAdd:
                Intent intent = new Intent(this, NewDanhBa.class);
                startActivity(intent);
                break;
        }
        LoaderManager.getInstance(this).restartLoader(CONTACT_LOADER, null, this);

        return super.onOptionsItemSelected(item);
    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        if (id == CONTACT_LOADER) {
            String[] SELECTED_FIELDS = new String[]
                    {
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                            ContactsContract.CommonDataKinds.Phone.NUMBER,
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    };
            return new CursorLoader(this, ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    SELECTED_FIELDS,
                    null,
                    null,
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " " + (isASC ? "ASC" : "DESC"));
        }
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if (loader.getId() == CONTACT_LOADER) {
            List<DanhBa> danhBaList = new ArrayList<>();
            if (data != null) {
                while (!data.isClosed() && data.moveToNext()) {
                    String phone = data.getString(1);
                    String name = data.getString(2);
                    danhBaList.add(new DanhBa(name, phone));
                }
                adapter.clear();
                adapter.addAll(danhBaList);
                adapter.notifyDataSetChanged();
                data.close();
            }
        }
    }
    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        loader = null;
    }

}