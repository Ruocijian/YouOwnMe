package com.jnu.youownme_xhr2018054592.dataprocessor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jnu.youownme_xhr2018054592.R;

import java.util.List;

public class GiveAdapter extends ArrayAdapter<Give> {
    private int resource_parent;
    private int resource_child;


    public GiveAdapter(@NonNull Context context, int resource_parent, int resource_child,@NonNull List<Give> objects) {
        super(context,resource_parent,resource_child,objects);
        this.resource_parent=resource_parent;
        this.resource_child=resource_child;
    }

    @NonNull

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View view;
        Give give = getItem(position);//获取当前项的实例
        String day = give.getYear() +"/"+ give.getMonth() +"/"+ give.getDay();
        String money = "¥"+give.getMoney();

        //如果获得是group，则加进group布局，否则加进子布局
        if(give.getName().equals("Group")){
            view=LayoutInflater.from(getContext()).inflate(this.resource_parent, parent, false);
            ((TextView) view.findViewById(R.id.text_give_group_year)).setText(String.valueOf(give.getYear()));
            ((TextView) view.findViewById(R.id.text_give_group_count)).setText("共" + give.getMoney() + "项");
        }else{
            view=LayoutInflater.from(getContext()).inflate(this.resource_child, parent, false);
            ((TextView) view.findViewById(R.id.text_give_name)).setText(give.getName());
            ((TextView) view.findViewById(R.id.text_give_date)).setText(day);
            ((TextView) view.findViewById(R.id.text_give_money)).setText(money);
        }
        return view;
    }
}

