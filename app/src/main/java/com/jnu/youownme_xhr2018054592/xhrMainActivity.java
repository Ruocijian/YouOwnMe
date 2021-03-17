package com.jnu.youownme_xhr2018054592;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;

import android.view.View;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jnu.youownme_xhr2018054592.dataprocessor.Accept;
import com.jnu.youownme_xhr2018054592.solay.CalendarActivity;
import com.jnu.youownme_xhr2018054592.dataprocessor.DataBankAccept;

import com.jnu.youownme_xhr2018054592.dataprocessor.Give;
import com.jnu.youownme_xhr2018054592.dataprocessor.DataBankGive;


public class xhrMainActivity extends AppCompatActivity {

    private RadioGroup mTabRadioGroup;
    private SparseArray<Fragment> mFragmentSparseArray;
    private static final int REQUEST_ADD = 200;
    public DataBankAccept dataBankAccept;
    public DataBankGive dataBankGive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initData();
        initView();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(xhrMainActivity.this, EditYouOwnMeActivity.class);
                intent.putExtra("position",0);
                startActivityForResult(intent, REQUEST_ADD);
            }
        });
    }

    private void initView() {
        mTabRadioGroup = findViewById(R.id.tabs_rg);
        mFragmentSparseArray = new SparseArray<>();
        mFragmentSparseArray.append(R.id.Accept_tab, new AcceptFragment());
        mFragmentSparseArray.append(R.id.Calendar_tab, new CalendarActivity());
        mFragmentSparseArray.append(R.id.give_tab, new GroupListViewFragment());

        // 默认显示日历
        mTabRadioGroup.check(R.id.Calendar_tab);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                mFragmentSparseArray.get(R.id.Calendar_tab)).commit();
        mTabRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // 具体的fragment切换逻辑可以根据应用调整，例如使用show()/hide()
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        mFragmentSparseArray.get(checkedId)).commit();
                //Log.v("ID_checkedId",""+checkedId);
            }
        });

    }

    private void initData() {
        dataBankAccept = new DataBankAccept(this);
        dataBankAccept.Load();//将代码读取进来
        dataBankGive = new DataBankGive(this);
        dataBankGive.Load();//将代码读取进来
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_ADD){
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

                if (type_add.equals("随礼")) {
                    dataBankGive.getGives().add(position_add, new Give(name_add, year_add, month_add, day_add, money_add, why_add));
                    dataBankGive.Save();

                } else {
                    dataBankAccept.getAccepts().add(position_add, new Accept(name_add, year_add, month_add, day_add, money_add, why_add));
                    dataBankAccept.Save();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
