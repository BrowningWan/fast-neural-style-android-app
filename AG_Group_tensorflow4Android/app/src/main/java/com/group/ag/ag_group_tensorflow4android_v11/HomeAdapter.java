package com.group.ag.ag_group_tensorflow4android_v11;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/11.
 */
class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<MainActivity.Model> list;
    private ButtonInterface buttonInterface;

    public HomeAdapter(Context context, ArrayList<MainActivity.Model> list) {
        this.context = context;
        this.list = list;
    }

    /**
     *按钮点击事件需要的方法
     */
    public void buttonSetOnclick(ButtonInterface buttonInterface){
        this.buttonInterface=buttonInterface;
    }

    /**
     * 按钮点击事件对应的接口
     */
    public interface ButtonInterface{
        public void onclick( View view,MainActivity.Model model);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_home, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.id_button.setImageResource(list.get(position).resId);
        holder.id_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonInterface!=null) {
//                  接口实例化后的而对象，调用重写后的方法
                    buttonInterface.onclick(v,list.get(position));
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * ViewHolder的类，用于缓存控件
     */
    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageButton id_button;
        public MyViewHolder(View itemView) {
            super(itemView);
            id_button = (ImageButton) itemView.findViewById(R.id.imageButton);

        }


    }
}
