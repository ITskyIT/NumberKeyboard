package com.tian.numberkeyboard.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tian.numberkeyboard.DividerGridItemDecoration;
import com.tian.numberkeyboard.KeyBoardAdapter;
import com.tian.numberkeyboard.R;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义面积 租金 dialog
 */

public class InputBoardDialog extends Dialog{

    private Context context;
    private RecyclerView mRecyclerView;
    private RelativeLayout mClose;
    private TextView sure,board_hint_tv;
    private ArrayList<Map<String, String>> valueList = new ArrayList<>();
    private KeyBoardAdapter adapter;
    private EditText etContent;
    private OnDialogResultListener onDialogResultListener;
    private String initialValue = ""; //  初始值
    private String unit = "元";    //  rent 租金 area 面积
    private int MAX_LENGTH = 10;
    public InputBoardDialog(Context context, @NonNull String value, String tag) {
        super(context, R.style.Theme_Dialog_From_Bottom);
        this.context=context;
        initialValue = value;
        if (tag != null) {
            unit = tag;
            if (tag.equals("㎡")) {
                MAX_LENGTH = 6;
            }
        }
        setContentView(R.layout.input_board_layout);
        initValueList();
        initComponent();
        adapter = new KeyBoardAdapter(context,valueList);
        mRecyclerView.setAdapter(adapter);
        initDialog();
        initListener();
    }

    public void setOnDialogResultListener(OnDialogResultListener onDialogResultListener) {
        this.onDialogResultListener = onDialogResultListener;
    }

    private void initListener() {
        adapter.setmOnItemClickListener(new KeyBoardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (etContent.getText().toString().length() <= 0 && position ==10) {
                    return;
                }
                if (etContent.getText().toString().length() >= MAX_LENGTH-1) {
                    return;
                }
                if (position != 9 && position != 11) {
                   if (etContent.getText().toString().length() > 0) {
                       etContent.setText(
                               etContent.getText().toString().
                                       substring(0,etContent.getText().toString().length()-1)+valueList.get(position).get("name")
                                       + unit);
                   } else {
                       etContent.setText(valueList.get(position).get("name") + unit);
                   }
                    etContent.setSelection(etContent.getText().toString().length() - 1);
                } else if (position == 11) {
                    dismiss();
                    onDialogResultListener.onResult("cancel");
                }
            }
        });
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (!"".equals(etContent.getText().toString())) {
                etContent.setText(etContent.getText().toString().substring(0, etContent.getText().toString().length() - 2) +unit);
                etContent.setSelection(etContent.getText().toString().length() - 1);
                if (etContent.getText().toString().length() == 1) {
                    etContent.setText("");
                }

            }
            }
        });
        mClose.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                etContent.setText("");
                return true;
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newContent = etContent.getText().toString();
                if ("㎡".equals(unit) && TextUtils.isEmpty(newContent)) {
                    Toast.makeText(context, "面积不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                dismiss();
                onDialogResultListener.onResult(newContent);
            }
        });
        etContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        //禁止光标移动
                        if (etContent.getText().toString().length() >0) {
                            etContent.setSelection(etContent.getText().toString().length() - 1);
                        }
                        break;
                }
                return true;
            }
        });
    }
    /**
     * 初始化控件
     */
    private void initComponent() {
        mRecyclerView = (RecyclerView) findViewById(R.id.key_board_rv);
        mRecyclerView.setLayoutManager(new GridLayoutManager(context,3));
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(context,1,R.color.text_hint));
        mClose = (RelativeLayout) findViewById(R.id.key_board_close);
        sure = (TextView) findViewById(R.id.key_board_sure);
        etContent = (EditText) findViewById(R.id.board_phone);
        board_hint_tv = (TextView) findViewById(R.id.board_hint_tv);
        if (unit.equals("㎡")) {
            board_hint_tv.setText("请输入面积");
        }
        forbidKeyBoard();
        configFocusable();
        if (initialValue.length() > 0) {
            etContent.setText(initialValue + unit);
            etContent.setSelection(etContent.getText().toString().length()-1);
        }
    }
    /**
     * 配置dialog
     */
    private void initDialog() {
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
    /**
     * 初始化键盘数字
     */
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
    /**
     * 结果监听回调
     */
    public interface OnDialogResultListener {
        void onResult(String result);
    }
    /**
     * 禁止键盘弹出
     */
    private void forbidKeyBoard () {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        try {
            Class<EditText> cls = EditText.class;
            Method setSoftInputShownOnFocus;
            setSoftInputShownOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
            setSoftInputShownOnFocus.setAccessible(true);
            setSoftInputShownOnFocus.invoke(etContent, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 初始化光标
     */
    private void configFocusable () {
        etContent.setFocusable(true);
        etContent.setFocusableInTouchMode(true);
        etContent.requestFocus();//获取焦点 光标出现
    }
}
