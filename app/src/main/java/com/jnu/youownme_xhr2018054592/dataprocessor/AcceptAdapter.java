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

public class AcceptAdapter extends ArrayAdapter<Accept> {
    private int resourceId;

    public AcceptAdapter(@NonNull Context context, int resource, @NonNull List<Accept> objects) {
        super(context,resource,objects);
        this.resourceId=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Accept accept = getItem(position);//获取当前项的实例

        String day = accept.getYear() +"/"+ accept.getMonth() +"/"+ accept.getDay();
        String money = "¥"+accept.getMoney();
        View view;
        if (null == convertView)
            view = LayoutInflater.from(getContext()).inflate(this.resourceId, parent, false);
        else
            view = convertView;
        ((TextView) view.findViewById(R.id.text_accept_name)).setText(accept.getName());
        ((TextView) view.findViewById(R.id.text_accept_why)).setText(accept.getWhy());
        ((TextView) view.findViewById(R.id.text_accept_day)).setText(day);
        ((TextView) view.findViewById(R.id.text_accept_money)).setText(money);
        return view;
    }
}

