package com.example.intentdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_number, et_name, et_major, et_class;
    private String sex;
    private String academy;
    private final String[] data = {"信息技术学院", "外国语学院", "机电学院", "商学院", "艺术设计学院", "珠宝学院", "新闻传播学院"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_class = findViewById(R.id.et_class);
        et_number = findViewById(R.id.et_number);
        et_name = findViewById(R.id.et_name);
        et_major = findViewById(R.id.et_major);
        Spinner sp_academy = findViewById(R.id.sp_academy);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, data);
        sp_academy.setAdapter(adapter);
        sp_academy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                academy = data[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        RadioGroup rg_sex = findViewById(R.id.rg_sex);
        rg_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.sex_boy) {
                    sex = "男";
                } else if (checkedId == R.id.sex_girl) {
                    sex = "女";
                }
            }
        });
        Button btn_explicit = findViewById(R.id.btn_explicit);
        btn_explicit.setOnClickListener(this);
        Button btn_implicit = findViewById(R.id.btn_implicit);
        btn_implicit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_implicit) {
            Intent intent = new Intent("com.example.test.ACTION_START");
            Bundle bundle = getInfo();
            intent.putExtra("bundle", bundle);
            startActivity(intent);
            finish();
        } else if (id == R.id.btn_explicit) {
            Intent intent = new Intent(MainActivity.this, NextActivity.class);
            Bundle bundle = getInfo();
            intent.putExtra("bundle", bundle);
            startActivity(intent);
            finish();
        }
    }

    private Bundle getInfo() {
        Bundle bundle = new Bundle();
        String classes = et_class.getText().toString().trim();
        String number = et_number.getText().toString().trim();
        String name = et_name.getText().toString().trim();
        String major = et_major.getText().toString().trim();
        bundle.putString("class", classes);
        bundle.putString("number", number);
        bundle.putString("name", name);
        bundle.putString("major", major);
        bundle.putString("sex", sex);
        bundle.putString("academy", academy);
        return bundle;
    }
}