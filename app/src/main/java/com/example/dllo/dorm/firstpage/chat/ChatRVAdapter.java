package com.example.dllo.dorm.firstpage.chat;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dllo.dorm.R;

import java.util.ArrayList;

/**
 * Created by SongQingJun on 16/11/30.
 */

public class ChatRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    ArrayList<ChatBean> arrayList;
    private final int CHAT_SELF = 0;
    private final int CHAT_OTHERS = 1;



    public void setArrayList(ArrayList<ChatBean> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged();
        Log.d("ChatRVAdapter", "zoule");
        notifyItemInserted(arrayList.size());
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == CHAT_SELF){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_self,parent,false);
            ViewHolderSelf viewHolder = new ViewHolderSelf(v);
            return viewHolder;
        }else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_others,parent,false);
            ViewHolderOthers viewHolder = new ViewHolderOthers(v);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == CHAT_SELF){
            ((ViewHolderSelf)holder).chatSelfTV.setText(arrayList.get(position).getMsg());
        }else {
            ((ViewHolderOthers)holder).chatOthersTV.setText(arrayList.get(position).getMsg());
        }
    }

    @Override
    public int getItemCount() {
        return arrayList == null ? 0 : arrayList.size();

    }

    @Override
    public int getItemViewType(int position) {
        if (arrayList.get(position).getType() == CHAT_SELF){
            return CHAT_SELF;
        }else {
            return CHAT_OTHERS;
        }
    }

    class ViewHolderOthers extends RecyclerView.ViewHolder {

        private final TextView chatOthersTV;

        public ViewHolderOthers(View itemView) {
            super(itemView);
            chatOthersTV = (TextView) itemView.findViewById(R.id.chat_others);
        }
    }

    class ViewHolderSelf extends RecyclerView.ViewHolder {

        private final TextView chatSelfTV;

        public ViewHolderSelf(View itemView) {
            super(itemView);
            chatSelfTV = (TextView) itemView.findViewById(R.id.chat_self);
        }
    }
}
