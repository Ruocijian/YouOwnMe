package com.jnu.youownme_xhr2018054592;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jnu.youownme_xhr2018054592.dataprocessor.AcceptAdapter;
import com.jnu.youownme_xhr2018054592.dataprocessor.DataBankAccept;
import com.jnu.youownme_xhr2018054592.dataprocessor.Accept;


import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AcceptFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AcceptFragment extends Fragment {

    private static final int CONTEXT_MENU_ITEM_DELETE = 1;
    private static final int CONTEXT_MENU_ITEM_NEW = CONTEXT_MENU_ITEM_DELETE + 1;
    private static final int CONTEXT_MENU_ITEM_UPDATE = CONTEXT_MENU_ITEM_NEW + 1;
    private static final int CONTEXT_MENU_ITEM_APPEND = CONTEXT_MENU_ITEM_UPDATE + 1;
    private static final int CONTEXT_MENU_ITEM_ABOUT = CONTEXT_MENU_ITEM_APPEND + 1;
    private static final int fresh=9;

    private static final int REQUEST_CODE_ADD_ACCEPT = 100;
    private static final int REQUEST_CODE_UPDATE_ACCEPT = 101;

    private DataBankAccept dataBankAccept;
    private AcceptAdapter adapter;

    public AcceptFragment() {
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //获取适配器
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        if (v == this.getActivity().findViewById(R.id.listview_books)) {
            menu.setHeaderTitle(dataBankAccept.getAccepts().get(info.position).getName());
            menu.add(1, CONTEXT_MENU_ITEM_DELETE, 1, "删除");
            menu.add(1, CONTEXT_MENU_ITEM_NEW, 1, "添加");
            menu.add(1, CONTEXT_MENU_ITEM_UPDATE, 1, "修改");
            menu.add(1, CONTEXT_MENU_ITEM_APPEND, 1, "新建");
            menu.add(1, CONTEXT_MENU_ITEM_ABOUT, 1, "关于……");
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {

            case REQUEST_CODE_ADD_ACCEPT:
                if (resultCode == RESULT_OK) {
                    int position_add = data.getIntExtra("position", 0);
                    String type_add = data.getStringExtra("type");
                    String name_add = data.getStringExtra("name");
                    String day_string_add = data.getStringExtra("day");
                    String money_str_add = data.getStringExtra("money");
                    String why_add = data.getStringExtra("reason");

                    String[] firstArray_add = day_string_add.split("-");
                    int year_add = Integer.parseInt(firstArray_add[0]);
                    int month_add = Integer.parseInt(firstArray_add[1]);
                    int day_add = Integer.parseInt(firstArray_add[2]);
                    int money_add = Integer.parseInt(money_str_add);

                    dataBankAccept.getAccepts().add(position_add, new Accept(name_add, year_add, month_add, day_add, money_add, why_add));
                    dataBankAccept.Save();
                    adapter.notifyDataSetChanged();
                }
                break;

            case REQUEST_CODE_UPDATE_ACCEPT:
                if (resultCode == RESULT_OK) {
                    int position_update = data.getIntExtra("position", 0);
                    String type_update = data.getStringExtra("type");
                    String name_update = data.getStringExtra("name");
                    String day_string_update = data.getStringExtra("day");
                    String[] firstArray_update = day_string_update.split("-");
                    String money_str_update = data.getStringExtra("money");
                    String why_update = data.getStringExtra("reason");

                    int year_update = Integer.parseInt(firstArray_update[0]);
                    int month_update = Integer.parseInt(firstArray_update[1]);
                    int day_update = Integer.parseInt(firstArray_update[2]);
                    int money_update = Integer.parseInt(money_str_update);

                    dataBankAccept.getAccepts().get(position_update).setName(name_update);
                    dataBankAccept.getAccepts().get(position_update).setYear(year_update);
                    dataBankAccept.getAccepts().get(position_update).setMonth(month_update);
                    dataBankAccept.getAccepts().get(position_update).setDay(day_update);
                    dataBankAccept.getAccepts().get(position_update).setMoney(money_update);
                    dataBankAccept.getAccepts().get(position_update).setWhy(why_update);

                    dataBankAccept.Save();
                    adapter.notifyDataSetChanged();

                }

                break;

            default:

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int position = menuInfo.position;
        switch (item.getItemId()) {
            case CONTEXT_MENU_ITEM_DELETE:
                AlertDialog.Builder builder_delete = new AlertDialog.Builder(this.getContext());
                builder_delete.setTitle("询问");
                builder_delete.setMessage("你确定要删除\"" + dataBankAccept.getAccepts().get(position).getName() + "\"？");
                builder_delete.setCancelable(true);
                builder_delete.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dataBankAccept.getAccepts().remove(position);
                        dataBankAccept.Save();
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "删除成功", Toast.LENGTH_LONG).show();
                    }
                });  //正面的按钮（肯定）
                builder_delete.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }); //反面的按钮（否定)
                builder_delete.create().show();
                break;

            case CONTEXT_MENU_ITEM_NEW:
                dataBankAccept.getAccepts().add(position, new Accept("XXX", 2020, 1, 1, 0, "收礼理由"));
                dataBankAccept.Save();
                adapter.notifyDataSetChanged();
                break;

            case CONTEXT_MENU_ITEM_APPEND:
                Intent intent = new Intent(this.getContext(), EditYouOwnMeActivity.class);
                intent.putExtra("position", position);
                startActivityForResult(intent, REQUEST_CODE_ADD_ACCEPT);
                break;

            case CONTEXT_MENU_ITEM_UPDATE:
                intent = new Intent(this.getContext(), EditYouOwnMeActivity.class);
                String date;
                int year, month, day;
                intent.putExtra("position", position);
                intent.putExtra("type", "收礼");
                intent.putExtra("name", dataBankAccept.getAccepts().get(position).getName());

                year = dataBankAccept.getAccepts().get(position).getYear();
                month = dataBankAccept.getAccepts().get(position).getMonth();
                day = dataBankAccept.getAccepts().get(position).getDay();

                date = year + "-" + month + "-" + day;
                intent.putExtra("day", date);
                intent.putExtra("money", dataBankAccept.getAccepts().get(position).getMoney());
                intent.putExtra("reason", dataBankAccept.getAccepts().get(position).getWhy());
                startActivityForResult(intent, REQUEST_CODE_UPDATE_ACCEPT);
                break;

            case CONTEXT_MENU_ITEM_ABOUT:
                AlertDialog.Builder builder_about = new AlertDialog.Builder(this.getContext());
                builder_about.setTitle("关于");
                builder_about.setMessage("2018054592辛汉睿");
                builder_about.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }); //反面的按钮（否定)
                builder_about.create().show();
                break;

            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AcceptFragment newInstance(String param1, String param2) {
        AcceptFragment fragment = new AcceptFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    //将布局文件加载进来
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_accept_list, container, false);
        initData();
        initView(view);

        return view;
    }


    private void initView(View view) {
        adapter = new AcceptAdapter(this.getContext(), R.layout.item_accept, dataBankAccept.getAccepts());

        ListView listViewBooks = view.findViewById(R.id.listview_books);
        listViewBooks.setAdapter(adapter);
        this.registerForContextMenu(listViewBooks);
    }

    private void initData() {
        dataBankAccept = new DataBankAccept(this.getContext());
        dataBankAccept.Load();//将代码读取进来
        //防止没有内容时上下文菜单无法激活
        if (0 == dataBankAccept.getAccepts().size()) {
            dataBankAccept.getAccepts().add(new Accept("请添加", 0, 0, 0, 0, "收礼理由"));
        }
    }

}


