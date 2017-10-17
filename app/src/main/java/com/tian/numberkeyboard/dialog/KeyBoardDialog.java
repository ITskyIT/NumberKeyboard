package com.tian.numberkeyboard.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private EditText tvPhone,board_verify;
    private ImageView ivRight,iv_back;
    private LinearLayout llOne,llTwo;
    private OnDialogResultListener onDialogResultListener;
    public KeyBoardDialog(Context context) {
        super(context, R.style.Theme_Dialog_From_Bottom);
        this.context=context;
        setContentView(R.layout.key_board_layout);
        initValueList();
        initComponent();
        adapter = new KeyBoardAdapter(context,valueList);
        mRecyclerView.setAdapter(adapter);
        init();
        initListener();
    }

    public void setOnDialogResultListener(OnDialogResultListener onDialogResultListener) {
        this.onDialogResultListener = onDialogResultListener;
    }

    private void initListener() {
        adapter.setmOnItemClickListener(new KeyBoardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position != 9 && position != 11) {
                    if (llOne.getVisibility() == View.VISIBLE) {
                        String num = valueList.get(position).get("name");
                        tvPhone.append(num);
                        if (tvPhone.getText().toString().length() == 11) {
                            ivRight.setVisibility(View.VISIBLE);
                        }
                    } else {
                        String num = valueList.get(position).get("name");
                        board_verify.append(num);
                    }
                }
            }
        });
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!"".equals(tvPhone.getText().toString())) {
                    tvPhone.setText(tvPhone.getText().toString().substring(0, tvPhone.getText().toString().length() - 1));
                    ivRight.setVisibility(View.GONE);
                }
            }
        });
        mClose.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                tvPhone.setText("");
                ivRight.setVisibility(View.GONE);
                return true;
            }
        });
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llOne.setVisibility(View.GONE);
                llTwo.setVisibility(View.VISIBLE);
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llOne.setVisibility(View.VISIBLE);
                llTwo.setVisibility(View.GONE);
                board_verify.setText("");
            }
        });
    }

    private void initComponent() {
        mRecyclerView = (RecyclerView) findViewById(R.id.key_board_rv);
        mRecyclerView.setLayoutManager(new GridLayoutManager(context,3));
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(context,1,R.color.divider));
        mClose = (RelativeLayout) findViewById(R.id.key_board_close);
        tvSure = (TextView) findViewById(R.id.key_board_sure);
        tvPhone = (EditText) findViewById(R.id.board_phone);
        board_verify = (EditText) findViewById(R.id.board_verify);
        ivRight = (ImageView) findViewById(R.id.iv_verify_phone);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        llOne = (LinearLayout) findViewById(R.id.dialog_ll_one);
        llTwo = (LinearLayout) findViewById(R.id.dialog_ll_two);
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

    @Override
    public void dismiss() {
        super.dismiss();
        onDialogResultListener.onResult("6");
    }
    public interface OnDialogResultListener {
        void onResult(String result);
    }
}
