package com.tian.numberkeyboard.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
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
 * 更换手机号验证
 * @author tian
 */

public class PhoneBoardDialog extends Dialog{
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
    private String oldPhone = "";
    private TextView tvTwoHint;

    public PhoneBoardDialog(Context context,@NonNull String phone) {
        super(context, R.style.Theme_Dialog_From_Bottom);
        this.context=context;
        oldPhone = phone;
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
                            tvSure.setEnabled(true);
                        }
                    } else {
                        String num = valueList.get(position).get("name");
                        board_verify.append(num);
                        if (board_verify.getText().toString().length() == 6) {
                            dismiss();
                            onDialogResultListener.onResult("验证成功"+tvPhone.getText().toString());
                        }
                    }
                } else if (position == 11) {
                    dismiss();
                    onDialogResultListener.onResult("cancel");
                }
            }
        });
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (llOne.getVisibility() == View.VISIBLE) {
                    if (!"".equals(tvPhone.getText().toString())) {
                        tvPhone.setText(tvPhone.getText().toString().substring(0, tvPhone.getText().toString().length() - 1));
                        ivRight.setVisibility(View.GONE);
                        tvSure.setEnabled(false);
                    }
                } else {
                    if (!"".equals(board_verify.getText().toString())) {
                        board_verify.setText(board_verify.getText().toString().substring(0, board_verify.getText().toString().length() - 1));
                    }
                }
            }
        });
        mClose.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (llOne.getVisibility() == View.VISIBLE) {
                    tvPhone.setText("");
                    ivRight.setVisibility(View.GONE);
                } else {
                    board_verify.setText("");
                }
                return true;
            }
        });
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPhone = tvPhone.getText().toString();
                if (newPhone.equals(oldPhone)) {
                    dismiss();
                    onDialogResultListener.onResult("some");
                    return;
                } else {
                    // 模拟 发送验证码
                    simulationVerify();
                }
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llOne.setVisibility(View.VISIBLE);
                llTwo.setVisibility(View.GONE);
                tvSure.setEnabled(true);
                board_verify.setText("");
            }
        });
    }

    private void simulationVerify() {
        //  成功  失败自行处理
        llOne.setVisibility(View.GONE);
        llTwo.setVisibility(View.VISIBLE);
        tvSure.setEnabled(false);
        tvTwoHint.setText("验证码已发  " + tvPhone.getText().toString());
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
        tvPhone.setText(oldPhone);
        ivRight.setVisibility(View.VISIBLE);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        llOne = (LinearLayout) findViewById(R.id.dialog_ll_one);
        llTwo = (LinearLayout) findViewById(R.id.dialog_ll_two);
        tvTwoHint = (TextView) findViewById(R.id.board_two_hint);
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

    public interface OnDialogResultListener {
        void onResult(String result);
    }
}
