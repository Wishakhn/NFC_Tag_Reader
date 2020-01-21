package com.wishakhn.nfc_tag_reader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void NFCHandler(View view) {
        switch (view.getId()){
            case R.id.startbtn:
                break;
            case R.id.stopbtn:
                break;
        }
    }
}
