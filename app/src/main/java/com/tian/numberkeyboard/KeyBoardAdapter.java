package com.tian.numberkeyboard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import static com.tian.numberkeyboard.R.id.text_view;

/**
 * 键盘的适配器
 */

public class KeyBoardAdapter extends RecyclerView.Adapter<KeyBoardAdapter.BoardViewHolder> {
    private Context mContext;
    private ArrayList<Map<String, String>> valueList;
    private OnItemClickListener mOnItemClickListener;
    public KeyBoardAdapter(Context mContext, ArrayList<Map<String, String>> valueList) {
        this.mContext = mContext;
        this.valueList = valueList;
    }

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public BoardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext,R.layout.item_board_layout,null);
        return new BoardViewHolder(view);
}
    @Override
    public void onBindViewHolder(final BoardViewHolder holder, final int position) {
        if (position == 11) {
            holder.iv.setVisibility(View.VISIBLE);
        } else {
            holder.numberText.setText(valueList.get(position).get("name") + "");
        }
        if(mOnItemClickListener != null){
            //为ItemView设置监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView,position);
                }
            });
        }

        holder.itemView.setTag(position);
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
            numberText = (TextView) itemView.findViewById(text_view);
            iv = (ImageView) itemView.findViewById(R.id.image_board);
        }
    }
    public interface OnItemClickListener {
        void onItemClick(View view,int position);
    }
}
