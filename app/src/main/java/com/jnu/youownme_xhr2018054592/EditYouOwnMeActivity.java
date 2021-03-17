package com.jnu.youownme_xhr2018054592;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class EditYouOwnMeActivity extends AppCompatActivity {

    private int position;

    private String type=null;
    private String name=null;
    private String day=null;
    private int money=0;
    private String reason=null;

    private Spinner spinner_type;
    private EditText edit_name;
    private EditText edit_day;
    private EditText edit_money;
    private Spinner spinner_reason;

    private int CurDay;
    private int CurMonth;
    private int CurYear;

    Calendar calendars;

    DateApplication date_choose = DateApplication.getInstance();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


        position = getIntent().getIntExtra("position",0);//0表示缺省值
        type = getIntent().getStringExtra("type");
        name = getIntent().getStringExtra("name");
        day = getIntent().getStringExtra("day");
        money = getIntent().getIntExtra("money",0);
        reason = getIntent().getStringExtra("reason");

        spinner_type = EditYouOwnMeActivity.this.findViewById(R.id.spinner_type);
        edit_name = EditYouOwnMeActivity.this.findViewById(R.id.edit_name);
        edit_day = EditYouOwnMeActivity.this.findViewById(R.id.edit_day);
        edit_money = EditYouOwnMeActivity.this.findViewById(R.id.edit_money);
        spinner_reason = EditYouOwnMeActivity.this.findViewById(R.id.spinner_reason);
        //有可能是NULL

        calendars = Calendar.getInstance();
        CurDay = Integer.valueOf(calendars.get(Calendar.DATE));
        CurMonth = Integer.valueOf(calendars.get(Calendar.MONTH));
        CurYear = Integer.valueOf(calendars.get(Calendar.YEAR));

        DateApplication datechoose=(DateApplication) getApplication();

        if(null!=name) {
            edit_name.setText(name);
            edit_day.setText(day);
            edit_money.setText(Integer.toString(money));

            if(type.equals("收礼")){
                spinner_type.setSelection(0,true);}
            else {spinner_type.setSelection(1,true);}

            if(reason.equals("结婚大喜")){spinner_reason.setSelection(0,true);}
            else if(reason.equals("新造华堂")){spinner_reason.setSelection(1,true);}
            else {spinner_reason.setSelection(2,true);}
        }
        else {
            CurDay=date_choose.getDay();
            CurMonth=date_choose.getMonth();
            CurYear=date_choose.getYear();
            if(CurYear==0)
            {
                CurDay = Integer.valueOf(calendars.get(Calendar.DATE));
                CurMonth = Integer.valueOf(calendars.get(Calendar.MONTH));
                CurYear = Integer.valueOf(calendars.get(Calendar.YEAR));
            }
            edit_day.setText(CurYear + "-" + (CurMonth) + "-" + CurDay);
        }

        spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                String[] types = getResources().getStringArray(R.array.type);
                type = types[pos];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        edit_day.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    hideInput();//隐藏输入框
                    showDatePickDialog(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            //选择日期过后执行的事件
                            edit_day.setText(year + "-" + (month + 1) + "-" + day);
                        }
                        }, edit_day.getText().toString());
                }
                return false;
            }
        });

        spinner_reason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                String[] reasons = getResources().getStringArray(R.array.reason);
                reason = reasons[pos];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        Button buttonOk = (Button) findViewById(R.id.button_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent();
                intent.putExtra("type", type);
                intent.putExtra("name", edit_name.getText().toString());
                intent.putExtra("day", edit_day.getText().toString());
                intent.putExtra("money", edit_money.getText().toString());
                intent.putExtra("reason", reason);
                intent.putExtra("position",position);
                intent.putExtra("Yes_or_No","Yes");
                setResult(RESULT_OK, intent);

                finish();
            }
        });

        Button buttoncancel = (Button) findViewById(R.id.button_cancel);
        buttoncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent();
                intent.putExtra("Yes_or_No","No");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            //返回处理
            Intent intent_keyback = new Intent();
            intent_keyback.putExtra("type", type);
            intent_keyback.putExtra("name", edit_name.getText().toString());
            intent_keyback.putExtra("day", edit_day.getText().toString());
            intent_keyback.putExtra("money", edit_money.getText().toString());
            intent_keyback.putExtra("position",position);
            intent_keyback.putExtra("reason", reason);
            intent_keyback.putExtra("Yes_or_No","No");
            setResult(RESULT_OK, intent_keyback);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void showDatePickDialog(DatePickerDialog.OnDateSetListener listener, String curDate) {
        Calendar calendar = Calendar.getInstance();
        int year = 0,month = 0,day = 0;
        try {
            year =Integer.parseInt(curDate.substring(0,curDate.indexOf("-"))) ;
            month =Integer.parseInt(curDate.substring(curDate.indexOf("-")+1,curDate.lastIndexOf("-")))-1 ;
            day =Integer.parseInt(curDate.substring(curDate.lastIndexOf("-")+1,curDate.length())) ;
        } catch (Exception e) {
            e.printStackTrace();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day=calendar.get(Calendar.DAY_OF_MONTH);
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,DatePickerDialog.THEME_HOLO_LIGHT,listener, year,month , day);
        datePickerDialog.show();
    }



    /**
     * 隐藏键盘
     */
    protected void hideInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}