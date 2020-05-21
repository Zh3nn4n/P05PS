
package com.myapplicationdev.android.p05ps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ShowActivity extends AppCompatActivity {

    ListView lv;
    Button filterByStars;
    CustomAdapter ca;
    ArrayList<Song> al;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("P05-NDPSongs ~ Show Song");
        setContentView(R.layout.activity_show);

        lv = (ListView) this.findViewById(R.id.lv);
        filterByStars = findViewById(R.id.btnStarFilter);

        DBHelper dbh = new DBHelper(this);
        al = dbh.getAllSongs();

        ca = new CustomAdapter(this, R.layout.row, al);
        lv.setAdapter(ca);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int
                    position, long identity) {
                Song data = al.get(position);
                Intent i = new Intent(ShowActivity.this, EditActivity.class);
                i.putExtra("data", data);
                startActivityForResult(i, 9);
            }
        });

        filterByStars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbh = new DBHelper(ShowActivity.this);
                al.clear();
                al.addAll(dbh.getAllSong("5"));
                dbh.close();
                ca.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 9) {
            DBHelper dbh = new DBHelper(ShowActivity.this);
            al.clear();
            al.addAll(dbh.getAllSongs());
            dbh.close();
            ca = new CustomAdapter(this, R.layout.row, al);
            lv.setAdapter(ca);
        }
    }
}