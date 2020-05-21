package com.myapplicationdev.android.p05ps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnAdd, btnShow;
    EditText etTitle, etSingers, etYear;
    ArrayList<Song> al;
    RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("P05-NDPSongs ~ Insert Song");
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnInsert);
        btnShow = findViewById(R.id.btnShow);

        etTitle = findViewById(R.id.etTitle);
        etSingers = findViewById(R.id.etSingers);
        etYear = findViewById(R.id.etYear);

        rg = (RadioGroup) findViewById(R.id.radioGroupStars);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etTitle.getText().toString().length() == 0 ||
                        etSingers.getText().toString().length() == 0 ||
                        etYear.getText().toString().length() == 0 ||
                        rg.getCheckedRadioButtonId() == -1 ) {
                    Toast.makeText(MainActivity.this, "Incomplete data", Toast.LENGTH_SHORT).show();
                }else {

                    DBHelper dbh = new DBHelper(MainActivity.this);

                    if (dbh.isExistingSong(etTitle.getText().toString())) {
                        Toast.makeText(MainActivity.this, "Song name already exists", Toast.LENGTH_LONG).show();
                    } else {
                        String title = etTitle.getText().toString();
                        String singers = etSingers.getText().toString();
                        int year = Integer.parseInt(etYear.getText().toString());
                        int stars = getStars();

                        long inserted_id = dbh.insertSong(title, singers, year, stars);
                        dbh.close();
                        if (inserted_id != -1) {
                            Toast.makeText(MainActivity.this, "Insert successful",
                                    Toast.LENGTH_SHORT).show();
                            etTitle.setText("");
                            etSingers.setText("");
                            etYear.setText("");
                        }
                    }
                }
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ShowActivity.class);
                startActivity(i);
            }
        });
    }
    private int getStars() {
        int stars = 1;
        switch (rg.getCheckedRadioButtonId()) {
            case R.id.rb1:
                stars = 1;
                break;
            case R.id.rb2:
                stars = 2;
                break;
            case R.id.rb3:
                stars = 3;
                break;
            case R.id.rb4:
                stars = 4;
                break;
            case R.id.rb5:
                stars = 5;
                break;
        }
        return stars;
    }
}
