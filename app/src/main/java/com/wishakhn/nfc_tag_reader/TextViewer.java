package com.wishakhn.nfc_tag_reader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class TextViewer extends AppCompatActivity {
TextView getdatatext;
Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_viewer);
        initView();
        setListner();
    }

    private void initView() {
        intent = getIntent();
        getdatatext = findViewById(R.id.getdatatext);
    }
    private void setListner() {
        if (intent != null){
            String msg_is = intent.getStringExtra("msgfromNfc");
            getdatatext.setText(msg_is);
        }
        else {
            getdatatext.setText("No Text Detected");
        }
    }
}
