
package com.myapplicationdev.android.p05ps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

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

        final Spinner dynamicSpinner = (Spinner) findViewById(R.id.spinnerYear);
        ArrayList<String> alYear = new ArrayList<String>();
        alYear.add("Show all");
        for (int i = 0; i < al.size(); i++){
            alYear.add(al.get(i).getYear()+"");
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, alYear);

        dynamicSpinner.setAdapter(adapter);

        dynamicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String year = dynamicSpinner.getSelectedItem().toString();
                if(!year.equals("Show all")) {

                    DBHelper dbh = new DBHelper(ShowActivity.this);
                    al.clear();
                    al.addAll(dbh.getAllSongByYear(year));
                    dbh.close();
                    ca.notifyDataSetChanged();
                }else{
                    DBHelper dbh = new DBHelper(ShowActivity.this);
                    al.clear();
                    al.addAll(dbh.getAllSongs());
                    dbh.close();
                    ca.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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