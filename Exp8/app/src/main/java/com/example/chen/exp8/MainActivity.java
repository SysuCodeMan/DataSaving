package com.example.chen.exp8;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Console;
import java.util.ArrayList;
import java.util.jar.Manifest;

/**
 * Created by Chen on 2016/11/20.
 */

public class MainActivity extends AppCompatActivity {
    private Button addButton;
    private ListView List;
    public MyAdapter myadapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.v("debugging", "isCreating");
        super.onCreate(savedInstanceState);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.READ_CONTACTS}, 0);
        }
        setContentView(R.layout.activity_main);
        FindViews();
        SetListener();
        SetAdapter();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null || data.getExtras() == null)
            return;
        People people = (People)data.getExtras().getSerializable("people");
        myadapter.addPeople(people);
    }

    private void SetListener() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("peoples", myadapter.getPeoples());
                intent.putExtras(bundle);
                intent.setClass(MainActivity.this, addActivity.class);
                startActivityForResult(intent, 0);
            }
        });
        List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final People target = (People)myadapter.getItem(position);
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater factory = LayoutInflater.from(MainActivity.this);
                View DialogView = factory.inflate(R.layout.dialoglayout, null);
                alert.setView(DialogView);
                alert.setTitle("(*^__^*)");
                TextView DialogName = (TextView) DialogView.findViewById(R.id.NameDialog);
                final EditText DialogBirthday = (EditText) DialogView.findViewById(R.id.BirthdayDialog);
                final EditText DialogGift = (EditText) DialogView.findViewById(R.id.GiftDialog);
                TextView phone = (TextView)DialogView.findViewById(R.id.Phone);
                phone.setText(getPhone(target.getName()));
                DialogName.setText(target.getName());
                DialogBirthday.setText(target.getBirthday());
                DialogGift.setText(target.getGift());
                alert.setNegativeButton("放弃修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                alert.setPositiveButton("保存修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            myadapter.modifyPeople(position, target.getName(),
                                    DialogBirthday.getText().toString(), DialogGift.getText().toString());
                    }
                });
                alert.create();
                alert.show();
            }
        });

        List.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setMessage("是否删除?");
                alert.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                alert.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myadapter.removePeople(position);
                    }
                });
                alert.create();
                alert.show();
                return true;
            }
        });
    }

    private String getPhone(String name) {
        String result = "无";
        Cursor cursor = getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            String contractName = cursor.getString(cursor.getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            Log.v("Name:", contractName);
            if (contractName.equals(name)) {
                if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER))) > 0) {
                    Cursor cursor1 = getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+"=\""
                            +contractName+"\"", null, null);
                    result = "";
                    while(cursor1.moveToNext()) {
                        result += cursor1.getString(cursor1.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER))+" ";
                    }
                }
                break;
            }
        }
        return result;
    }

    private void SetAdapter() {
        if (myadapter == null) {
            Log.v("debugging", "The adapter has been killed");
            myadapter = new MyAdapter(this);
            List.setAdapter(myadapter);
        }
    }

    private void FindViews() {
        addButton = (Button) findViewById(R.id.addButton);
        List = (ListView) findViewById(R.id.List);
        return;
    }
}
