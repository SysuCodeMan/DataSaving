package com.example.chen.exp8;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Chen on 2016/11/20.
 */

public class addActivity extends AppCompatActivity{
    private EditText name, birthday, gift;
    private Button submitAdd;
    private ArrayList<People> peoples;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_info);
        peoples = (ArrayList<People>) getIntent().getExtras().getSerializable("peoples");
        FindViews();
        AddClickListener();
    }

    private void FindViews() {
        name = (EditText) findViewById(R.id.NameInput);
        birthday = (EditText) findViewById(R.id.BirthdayInput);
        gift = (EditText) findViewById(R.id.GiftInput);
        submitAdd = (Button) findViewById(R.id.submitAdd);
    }

    private void AddClickListener() {
        submitAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameText = name.getText().toString();
                String birthdayText =  birthday.getText().toString();
                String giftText = gift.getText().toString();
                if (nameText.equals("")) {
                    Toast.makeText(addActivity.this, "名字为空，请完善",
                            Toast.LENGTH_SHORT).show();
                } else {
                    if (isValid(nameText)) {
                        Intent intent = new Intent();
                        intent.setClass(addActivity.this, MainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("people", new People(nameText, birthdayText,
                                giftText));
                        intent.putExtras(bundle);
                        setResult(0, intent);
                        finish();
                    } else {
                        Toast.makeText(addActivity.this, "名字重复啦，请核查",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean isValid(String nameText) {
        for (int i = 0; i < peoples.size(); i++) {
            if (peoples.get(i).getName().equals(nameText)) {
                return false;
            }
        }
        return true;
    }


}
