package com.example.myapplication1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView text;
    TextView text2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.text);
        text2 = findViewById(R.id.text2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void onClickStart(View v) {
        Intent intent = new Intent(this, TwoActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String Contacts = data.getStringExtra("key");
        if (Contacts.equals("[]")) {
            text.setText("Контакты не найдены");
        } else {
            text2.setText("Список контактов:");
            text.setText(Contacts);

        }
    }
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        text.setText(savedInstanceState.getString("txt1"));
        text2.setText(savedInstanceState.getString("txt2"));
    }

    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("txt1",text.getText().toString());
        outState.putString("txt2",text2.getText().toString());
        super.onSaveInstanceState(outState);
    }
}