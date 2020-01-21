package com.wishakhn.nfc_tag_reader;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
NfcAdapter nfcAdapter;
String tagOInfo_str ="";
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
                resolveIntent(getIntent());
                break;
            case R.id.stopbtn:
                finish();
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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        System.err.println("Gave Intent is "+tag);
        System.err.println("Intent Action is "+intent.getAction());
        resolveIntent(intent);
    }
    private void resolveIntent(Intent intent) {
        String action = intent.getAction();

        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] msgs;

            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];

                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }

            } else {
                Tag tag = (Tag) intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                Ndef ndef = Ndef.get(tag);
               tagOInfo_str=readFromNFC(ndef);
            }
            Intent move = new Intent(MainActivity.this, TextViewer.class);
            move.putExtra("msgfromNfc",tagOInfo_str);
            startActivity(move);
        }
    }
    private String readFromNFC(Ndef ndef) {

        try {
            ndef.connect();
            NdefMessage ndefMessage = ndef.getNdefMessage();
            String message = new String(ndefMessage.getRecords()[0].getPayload());
            Log.d("TAG", "readFromNFC: "+message);
            ndef.close();
            return message;
        } catch (IOException | FormatException e) {
            e.printStackTrace();

        }
        return null;
    }

}
