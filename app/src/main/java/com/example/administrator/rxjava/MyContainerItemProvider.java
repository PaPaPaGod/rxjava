package com.example.administrator.rxjava;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.Message;

public class MyContainerItemProvider extends IContainerItemProvider.MessageProvider<MyMsg> {
    @Override
    public void bindView(View view, int i, MyMsg myMsg, UIMessage uiMessage) {
        ViewHolder holder = (ViewHolder) view.getTag();
        if(uiMessage.getMessageDirection()== Message.MessageDirection.SEND){
            holder.message.setBackgroundResource(io.rong.imkit.R.drawable.rc_ic_bubble_right);
        }else{
            holder.message.setBackgroundResource(io.rong.imkit.R.drawable.rc_ic_bubble_left);
        }
        holder.message.setText(myMsg.getContent());

    }

    @Override
    public Spannable getContentSummary(MyMsg myMsg) {
        return new SpannableString("这是一条自定义消息CustomizeMessage");
    }

    @Override
    public void onItemClick(View view, int i, MyMsg myMsg, UIMessage uiMessage) {

    }

    @Override
    public View newView(Context context, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(io.rong.imkit.R.layout.rc_item_message,null);
        ViewHolder holder = new ViewHolder();
        holder.message = view.findViewById(android.R.id.text1);
        view.setTag(holder);
        return view;
    }

    class ViewHolder{
        TextView message;
    }
}
