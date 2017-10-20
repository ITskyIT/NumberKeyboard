package com.tian.numberkeyboard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.tian.numberkeyboard.dialog.InputBoardDialog;
import com.tian.numberkeyboard.dialog.PhoneBoardDialog;

/**
 * @author tian
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void doClick (View view) {
        PhoneBoardDialog dialog = new PhoneBoardDialog(this,"18236123463");
        dialog.show();
        dialog.setOnDialogResultListener(new PhoneBoardDialog.OnDialogResultListener() {
            @Override
            public void onResult(String result) {
                if (result.equals("some")) {
                    Toast.makeText(MainActivity.this, "此手机号无需再次验证", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void doClick1 (View view) {
        InputBoardDialog dialog = new InputBoardDialog(this,"","元");
        dialog.show();
        dialog.setOnDialogResultListener(new InputBoardDialog.OnDialogResultListener() {
            @Override
            public void onResult(String result) {
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void doClick2 (View view) {
        InputBoardDialog dialog = new InputBoardDialog(this,"","㎡");
        dialog.show();
        dialog.setOnDialogResultListener(new InputBoardDialog.OnDialogResultListener() {
            @Override
            public void onResult(String result) {
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
