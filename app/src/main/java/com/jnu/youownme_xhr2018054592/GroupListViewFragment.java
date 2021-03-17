package com.jnu.youownme_xhr2018054592;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.jnu.youownme_xhr2018054592.dataprocessor.DataBankGive;
import com.jnu.youownme_xhr2018054592.dataprocessor.Give;
import com.jnu.youownme_xhr2018054592.dataprocessor.GiveAdapter;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/*
 * A simple {@link Fragment} subclass.
 * Use the {@link GroupListViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupListViewFragment extends Fragment {

    /** Called when the activity is first created. */
    private ArrayList<Give> output_list = null;
    private ArrayList<Give> aList = null;
    private ArrayList<Give> bList = null;
    private ArrayList<Give> cList = null;
    private ArrayList<Give> DataBankList = new ArrayList<>();
    private int DataBank_size;
    private int count=0;

    private DataBankGive dataBankGive;
    private GiveAdapter adapter_give;

    private static final int CONTEXT_MENU_ITEM_DELETE = 10;
    private static final int CONTEXT_MENU_ITEM_NEW = CONTEXT_MENU_ITEM_DELETE + 1;
    private static final int CONTEXT_MENU_ITEM_UPDATE = CONTEXT_MENU_ITEM_NEW + 1;
    private static final int CONTEXT_MENU_ITEM_APPEND = CONTEXT_MENU_ITEM_UPDATE + 1;
    private static final int CONTEXT_MENU_ITEM_ABOUT = CONTEXT_MENU_ITEM_APPEND + 1;

    private static final int REQUEST_CODE_ADD_ACCEPT = 102;
    private static final int REQUEST_CODE_UPDATE_ACCEPT = 103;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    //将布局文件加载进来
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group_list_view, container, false);
        initData();
        initView(view);

        return view;
    }


    private void initView(View view) {
        adapter_give = new GiveAdapter(this.getContext(),R.layout.item_give_group,R.layout.item_give,output_list);

        ListView listViewBooks = view.findViewById(R.id.listview_group);
        listViewBooks.setAdapter(adapter_give);
        this.registerForContextMenu(listViewBooks);
    }

    public void initData(){
        dataBankGive = new DataBankGive(this.getContext());
        dataBankGive.Load();//将代码读取进来
        //防止没有内容时上下文菜单无法激活
        if (0 == dataBankGive.getGives().size()) {
            dataBankGive.getGives().add(new Give("请添加项", 2021, 0, 0, 0, "随礼理由"));
        }

        output_list = new ArrayList<>();
        aList = new ArrayList<>();
        bList = new ArrayList<>();
        cList = new ArrayList<>();

        DataBankList = dataBankGive.getGives();
        DataBank_size = DataBankList.size();

        for(int i_2021=0; i_2021<DataBank_size; i_2021++){
            if(dataBankGive.getGives().get(i_2021).getYear()==2021) {
                aList.add(dataBankGive.getGives().get(i_2021));
                count++;
            }
        }
        output_list.add(new Give("Group", 2021, 0, 0, count, "组别"));
        count=0;
        output_list.addAll(aList);

        for(int i_2020=0; i_2020<DataBank_size; i_2020++){
            if(dataBankGive.getGives().get(i_2020).getYear()==2020){
                bList.add(dataBankGive.getGives().get(i_2020));
                count++;
            }
        }
        output_list.add(new Give("Group", 2020, 0, 0, count, "组别"));
        count=0;
        output_list.addAll(bList);

        for(int i_2019=0; i_2019<DataBank_size; i_2019++){
            if(dataBankGive.getGives().get(i_2019).getYear()==2019){
                cList.add(dataBankGive.getGives().get(i_2019));
                count++;
            }
        }
        output_list.add(new Give("Group", 2019, 0, 0, count, "组别"));
        count=0;
        output_list.addAll(cList);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //获取适配器
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        if (v == this.getActivity().findViewById(R.id.listview_group)) {
            menu.setHeaderTitle(dataBankGive.getGives().get(info.position-1).getName());
            menu.add(1, CONTEXT_MENU_ITEM_DELETE, 1, "删除");
            menu.add(1, CONTEXT_MENU_ITEM_NEW, 1, "添加");
            menu.add(1, CONTEXT_MENU_ITEM_UPDATE, 1, "修改");
            menu.add(1, CONTEXT_MENU_ITEM_APPEND, 1, "新建");
            menu.add(1, CONTEXT_MENU_ITEM_ABOUT, 1, "关于……");
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int position = menuInfo.position;
        switch (item.getItemId()) {
            case CONTEXT_MENU_ITEM_DELETE:
                AlertDialog.Builder builder_delete = new AlertDialog.Builder(this.getContext());
                builder_delete.setTitle("询问");
                builder_delete.setMessage("你确定要删除\"" + dataBankGive.getGives().get(position).getName() + "\"？");
                builder_delete.setCancelable(true);
                builder_delete.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dataBankGive.getGives().remove(position);
                        dataBankGive.Save();
                        onResume();
                        adapter_give.notifyDataSetChanged();
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
                dataBankGive.getGives().add(position, new Give("XXX", 2020, 1, 1, 0, ""));
                dataBankGive.Save();
                adapter_give.notifyDataSetChanged();
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
                intent.putExtra("name", dataBankGive.getGives().get(position).getName());

                year = dataBankGive.getGives().get(position).getYear();
                month = dataBankGive.getGives().get(position).getMonth();
                day = dataBankGive.getGives().get(position).getDay();

                date = year + "-" + month + "-" + day;
                intent.putExtra("day", date);
                intent.putExtra("money", dataBankGive.getGives().get(position).getMoney());
                intent.putExtra("reason", dataBankGive.getGives().get(position).getWhy());
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {

            case REQUEST_CODE_ADD_ACCEPT:
                if (resultCode == RESULT_OK) {
                    if(data.getStringExtra("Yes_or_No").equals("Yes")) {
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

                        dataBankGive.getGives().add(position_add, new Give(name_add, year_add, month_add, day_add, money_add, why_add));
                        dataBankGive.Save();
                        adapter_give.notifyDataSetChanged();
                    }

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

                    dataBankGive.getGives().get(position_update).setName(name_update);
                    dataBankGive.getGives().get(position_update).setYear(year_update);
                    dataBankGive.getGives().get(position_update).setMonth(month_update);
                    dataBankGive.getGives().get(position_update).setDay(day_update);
                    dataBankGive.getGives().get(position_update).setMoney(money_update);
                    dataBankGive.getGives().get(position_update).setWhy(why_update);

                    dataBankGive.Save();
                    adapter_give.notifyDataSetChanged();

                }

                break;


            default:

        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}