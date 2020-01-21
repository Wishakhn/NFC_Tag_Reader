package com.wishakhn.nfc_tag_reader;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
NfcAdapter nfcAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        nfcAdapter = NfcAdapter.getDefaultAdapter(MainActivity.this);
    }

    public void NFCHandler(View view) {
        switch (view.getId()){
            case R.id.startbtn:
                break;
            case R.id.stopbtn:
                break;
        }
    }
    PendingIntent nfcTag_pendingIntent(){
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        return pendingIntent;
    }
    IntentFilter[] nfcTag_intentfilter(){
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        IntentFilter ndefDetected = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        IntentFilter techDetected = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        IntentFilter[] nfcIntentFilter = new IntentFilter[]{techDetected,tagDetected,ndefDetected};

        return nfcIntentFilter;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (nfcAdapter != null){
            nfcAdapter.enableForegroundDispatch(this, nfcTag_pendingIntent(), nfcTag_intentfilter(), null);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (nfcAdapter != null){
            nfcAdapter.disableForegroundDispatch(this);

        }
    }
}
