package com.tian.numberkeyboard.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tian.numberkeyboard.DividerGridItemDecoration;
import com.tian.numberkeyboard.KeyBoardAdapter;
import com.tian.numberkeyboard.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * 自定义弹框dialog
 */

public class KeyBoardDialog extends Dialog{

    private Context context;
    private RecyclerView mRecyclerView;
    private RelativeLayout mClose;
    private TextView tvSure;
    private ArrayList<Map<String, String>> valueList = new ArrayList<>();
    private KeyBoardAdapter adapter;
    public KeyBoardDialog(Context context) {
        super(context, R.style.Theme_Dialog_From_Bottom);
        this.context=context;
        setContentView(R.layout.key_board_layout);
        initValueList();
        mRecyclerView = (RecyclerView) findViewById(R.id.key_board_rv);
        mRecyclerView.setLayoutManager(new GridLayoutManager(context,3));
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(context,1,R.color.divider));
        adapter = new KeyBoardAdapter(context,valueList);
        mRecyclerView.setAdapter(adapter);
        mClose = (RelativeLayout) findViewById(R.id.key_board_close);
        tvSure = (TextView) findViewById(R.id.key_board_sure);
        init();
    }

    private void init() {
        this.setCanceledOnTouchOutside(true);
        this.setCancelable(true);
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.ios_bottom_dialog_anim);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
    }
    private void initValueList() {
        // 初始化按钮上应该显示的数字
        for (int i = 1; i < 13; i++) {
            Map<String, String> map = new HashMap<>();
            if (i < 10) {
                map.put("name", String.valueOf(i));
            } else if (i == 10) {
                map.put("name", "");
            } else if (i == 11) {
                map.put("name", String.valueOf(0));
            } else if (i == 12) {
                map.put("name", "");
            }
            valueList.add(map);
        }
    }

    @Override
    public void show() {
        super.show();
    }


}
