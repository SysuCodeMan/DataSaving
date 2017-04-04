package com.example.chen.exp8;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Chen on 2016/11/20.
 */

public class MyAdapter extends BaseAdapter implements Serializable{
    private ArrayList<People> peoples;
    private Context context;
    private myDB DbHelper;
    private SQLiteDatabase db;
    private static String DB_TABLE = "BirthdayInfo";
    public ArrayList<People> getPeoples() {
        return peoples;
    }
    MyAdapter(Context context) {
        this.context = context;
        peoples = new ArrayList<People>();
        DbHelper = new myDB(context, "my.db", null, 1);
        db = DbHelper.getWritableDatabase();
        Cursor infos = db.query(DB_TABLE, new String[] {"_id", "name", "birthday", "gift"}, null, null, null, null, null);
        if (infos != null && infos.moveToFirst()) {
            for (int i = 0 ; i < infos.getCount(); i++) {
                String name = infos.getString(infos.getColumnIndex("name"));
                String birth = infos.getString(infos.getColumnIndex("birthday"));
                String gift = infos.getString(infos.getColumnIndex("gift"));
                peoples.add(new People(name, birth, gift));
                infos.moveToNext();
            }
        }
        db.close();
    }

    public void addPeople(People people) {
        db = DbHelper.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put("name", people.getName());
        newValues.put("birthday", people.getBirthday());
        newValues.put("gift", people.getGift());
        db.insert(DB_TABLE, null, newValues);
        peoples.add(people);
        db.close();
        notifyDataSetChanged();
    }

    public void removePeople(int position) {
        db = DbHelper.getWritableDatabase();
        db.delete(DB_TABLE, "name=\""+peoples.get(position).getName()+"\"", null);
        db.close();
        peoples.remove(position);
        notifyDataSetChanged();
    }

    public void modifyPeople(int position, String name, String birthday, String gift) {
        db = DbHelper.getWritableDatabase();
        ContentValues updateValues = new ContentValues();
        updateValues.put("name", name);
        updateValues.put("birthday", birthday);
        updateValues.put("gift", gift);
        db.update(DB_TABLE, updateValues, "name=\""+name+"\"", null);
        db.close();
        peoples.get(position).setName(name);
        peoples.get(position).setBirthday(birthday);
        peoples.get(position).setGift(gift);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return peoples.size();
    }

    @Override
    public Object getItem(int position) {
        return peoples.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item, null);
        }
        TextView name = (TextView) convertView.findViewById(R.id.Name);
        TextView birthday = (TextView) convertView.findViewById(R.id.Birthday);
        TextView gift = (TextView) convertView.findViewById(R.id.Gift);

        name.setText(peoples.get(position).getName());
        birthday.setText(peoples.get(position).getBirthday());
        gift.setText(peoples.get(position).getGift());

        return convertView;
    }
}
