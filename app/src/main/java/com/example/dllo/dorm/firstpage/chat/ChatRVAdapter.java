package com.example.dllo.dorm.firstpage.chat;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dllo.dorm.R;
import com.example.dllo.dorm.base.Values;

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
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == CHAT_SELF){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_self,parent,false);
            ViewHolderSelf viewHolder = new ViewHolderSelf(v);
            return viewHolder;
        }else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_others,parent,false);
            ViewHolderOthers viewHolder = new ViewHolderOthers(v);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == CHAT_SELF){
            ((ViewHolderSelf)holder).chatSelfTV.setText(arrayList.get(position).getMsg());
            ((ViewHolderSelf)holder).chatSelfNickname.setText(arrayList.get(position).getNickname());
            Glide.with(((ViewHolderSelf)holder).itemView.getContext()).load(Values.ICON_URL).into(((ViewHolderSelf)holder).chatSelfIcon);
        }else {
            ((ViewHolderOthers)holder).chatOthersTV.setText(arrayList.get(position).getMsg());
            ((ViewHolderOthers)holder).chatOthersNickname.setText(arrayList.get(position).getNickname());
            Glide.with(((ViewHolderOthers)holder).chatOthersIcon.getContext()).load(arrayList.get(position).getIconUrl()).into(((ViewHolderOthers)holder).chatOthersIcon);

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

        private TextView chatOthersTV;
        private ImageView chatOthersIcon;
        private TextView chatOthersNickname;

        public ViewHolderOthers(View itemView) {
            super(itemView);
            chatOthersTV = (TextView) itemView.findViewById(R.id.chat_others);
            chatOthersNickname = (TextView) itemView.findViewById(R.id.chat_nickname_others);
            chatOthersIcon = (ImageView) itemView.findViewById(R.id.chat_others_icon);

        }
    }

    class ViewHolderSelf extends RecyclerView.ViewHolder {

        private TextView chatSelfTV;
        private TextView chatSelfNickname;
        private ImageView chatSelfIcon;

        public ViewHolderSelf(View itemView) {
            super(itemView);
            chatSelfTV = (TextView) itemView.findViewById(R.id.chat_self);
            chatSelfNickname = (TextView) itemView.findViewById(R.id.chat_nickname_self);
            chatSelfIcon = (ImageView) itemView.findViewById(R.id.chat_self_icon);
        }
    }
}
