package com.tian.numberkeyboard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.tian.numberkeyboard.dialog.KeyBoardDialog;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void doClick (View view) {
        KeyBoardDialog dialog = new KeyBoardDialog(this);
        dialog.show();
        dialog.setOnDialogResultListener(new KeyBoardDialog.OnDialogResultListener() {
            @Override
            public void onResult(String result) {
                Log.e("onDissMiss",result);
            }
        });
    }
}
