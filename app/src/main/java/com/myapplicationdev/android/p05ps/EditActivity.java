package com.myapplicationdev.android.p05ps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {
    TextView tvID;
    EditText etTitle, etSingers, etYear;
    Button btnUpdate, btnDelete, btnCancel;
    Song data;
    RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("P05-NDPSongs ~ Modify Song");
        setContentView(R.layout.activity_edit);

        tvID = findViewById(R.id.tvID);
        etTitle = findViewById(R.id.etTitleEdit);
        etSingers = findViewById(R.id.etSingerEdit);
        etYear = findViewById(R.id.etEditYear);

        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnCancel = findViewById(R.id.btnCancel);

        Intent i = getIntent();
        data = (Song) i.getSerializableExtra("data");

        tvID.setText("ID: " + data.get_id());
        etTitle.setText(data.getTitle());
        etSingers.setText(data.getSingers());
        etYear.setText(data.getYear()+"");

        int star = data.getStars();
        rg = (RadioGroup) findViewById(R.id.radioGroupStars);

        if (star == 1){
            rg.check(R.id.rb1);
        } else if (star == 2){
            rg.check(R.id.rb2);
        } else if (star == 3){
            rg.check(R.id.rb3);
        } else if (star == 4){
            rg.check(R.id.rb4);
        }else if (star >= 5){
            rg.check(R.id.rb5);
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(EditActivity.this);
                data.setTitle(etTitle.getText().toString());
                data.setSingers(etSingers.getText().toString());
                data.setYear(Integer.parseInt(etYear.getText().toString()));
                data.setStars(getStars());
                dbh.updateSong(data);
                dbh.close();

                Intent i = new Intent();
                i.putExtra("data", data);
                setResult(RESULT_OK, i);
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(EditActivity.this);
                dbh.deleteSong(data.get_id());
                dbh.close();

                Intent i = new Intent();
                i.putExtra("data", data);
                setResult(RESULT_OK, i);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
