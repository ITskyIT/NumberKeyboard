package com.tian.numberkeyboard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

/**
 * 键盘的适配器
 */

public class KeyBoardAdapter extends RecyclerView.Adapter<KeyBoardAdapter.BoardViewHolder> {
    private Context mContext;
    private ArrayList<Map<String, String>> valueList;

    public KeyBoardAdapter(Context mContext, ArrayList<Map<String, String>> valueList) {
        this.mContext = mContext;
        this.valueList = valueList;
    }

    @Override
    public BoardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BoardViewHolder(View.inflate(mContext,R.layout.item_board_layout,null));
}
    @Override
    public void onBindViewHolder(BoardViewHolder holder, int position) {
        if (position == 11) {
            holder.iv.setVisibility(View.VISIBLE);
        } else {
            holder.numberText.setText(valueList.get(position).get("name") + "");
        }
    }

    @Override
    public int getItemCount() {
        return valueList.size();
    }
    public class BoardViewHolder extends RecyclerView.ViewHolder {
        TextView numberText;
        ImageView iv;
        public BoardViewHolder(View itemView) {
            super(itemView);
            numberText = (TextView) itemView.findViewById(R.id.text_view);
            iv = (ImageView) itemView.findViewById(R.id.image_board);
        }
    }
}
