package com.jnu.youownme_xhr2018054592.solay;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.jnu.youownme_xhr2018054592.DateApplication;
import com.jnu.youownme_xhr2018054592.R;
import com.jnu.youownme_xhr2018054592.base.activity.BaseActivity;
import com.jnu.youownme_xhr2018054592.dataprocessor.Accept;
import com.jnu.youownme_xhr2018054592.dataprocessor.AcceptAdapter;
import com.jnu.youownme_xhr2018054592.dataprocessor.DataBankAccept;
import com.jnu.youownme_xhr2018054592.dataprocessor.DataBankGive;
import com.jnu.youownme_xhr2018054592.dataprocessor.Give;
import com.jnu.youownme_xhr2018054592.dataprocessor.GiveAdapter;
import com.jnu.youownme_xhr2018054592.group.GroupRecyclerView;

import java.util.ArrayList;

public class CalendarActivity extends BaseActivity implements
        com.haibin.calendarview.CalendarView.OnCalendarSelectListener,
        com.haibin.calendarview.CalendarView.OnYearChangeListener,
        View.OnClickListener {

    TextView mTextMonthDay;
    TextView mTextYear;
    TextView mTextLunar;
    TextView mTextCurrentDay;
    TextView text_show_give;
    TextView text_show_accept;

    public com.haibin.calendarview.CalendarView mCalendarView;

    RelativeLayout mRelativeTool;
    private int mYear;
    CalendarLayout mCalendarLayout;

    private DataBankAccept dataBank_show_Accept;
    private AcceptAdapter adapter_show_accept;
    private DataBankGive dataBank_show_Give;
    private GiveAdapter adapter_show_give;
    private ArrayList<Give> list_show_give;
    private ArrayList<Accept> list_show_accept;

    private String show_accept="";
    private String show_give="";

    private int accept_show_size;
    private int give_show_size;

    private int accept_year;
    private int accept_month;
    private int accept_day;
    private int accept_money;
    private String accept_Name;
    private String accept_reason;
    private String accept_date;

    private int give_year;
    private int give_month;
    private int give_day;
    private int give_money;
    private String give_Name;
    private String give_reason;
    private String give_date;

    private String choose_date;

    private DateApplication date_choose=DateApplication.getInstance();

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        View view=inflater.inflate(R.layout.fragment_solay,container,false);
        initWindow();
        initView(view);
        initData(view);


        return view;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_solay;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initView(View view) {
        if (Build.VERSION.SDK_INT >= 21) {
            getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.solar_background));
        }
        mTextMonthDay = view.findViewById(R.id.tv_month_day);
        mTextYear = view.findViewById(R.id.tv_year);
        mTextLunar = view.findViewById(R.id.tv_lunar);
        mRelativeTool = view.findViewById(R.id.rl_tool);
        mCalendarView = view.findViewById(R.id.calendarView);
        mTextCurrentDay = view.findViewById(R.id.tv_current_day);
        mCalendarLayout = view.findViewById(R.id.calendarLayout);
        text_show_give=view.findViewById(R.id.text_show_give);
        text_show_accept=view.findViewById(R.id.text_show_accept);
        mCalendarView.setOnCalendarSelectListener(this);
        mCalendarView.setOnYearChangeListener(this);
        mTextYear.setText(String.valueOf(mCalendarView.getCurYear()));
        mYear = mCalendarView.getCurYear();
        mTextMonthDay.setText(mCalendarView.getCurMonth() + "月" + mCalendarView.getCurDay() + "日");
        mTextLunar.setText("今日");
        mTextCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));

        mTextMonthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mCalendarLayout.isExpand()) {
                    mCalendarLayout.expand();
                    return;
                }
                mCalendarView.showYearSelectLayout(mYear);
                mTextLunar.setVisibility(View.GONE);
                mTextYear.setVisibility(View.GONE);
                mTextMonthDay.setText(String.valueOf(mYear));

            }
        });
        view.findViewById(R.id.fl_current).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarView.scrollToCurrent();
            }
        });

    }

    @Override
    protected void initData(View view) {
        int year = mCalendarView.getCurYear();
        int month = mCalendarView.getCurMonth();

        /*Map<String, Calendar> map = new HashMap<>();
        map.put(getSchemeCalendar(year, month, 3, "假").toString(),
                getSchemeCalendar(year, month, 3, "假"));
        map.put(getSchemeCalendar(year, month, 6, "事").toString(),
                getSchemeCalendar(year, month, 6, "事"));
        map.put(getSchemeCalendar(year, month, 9, "议").toString(),
                getSchemeCalendar(year, month, 9, "议"));
        map.put(getSchemeCalendar(year, month, 13, "记").toString(),
                getSchemeCalendar(year, month, 13, "记"));
        map.put(getSchemeCalendar(year, month, 14, "记").toString(),
                getSchemeCalendar(year, month, 14, "记"));
        map.put(getSchemeCalendar(year, month, 15, "假").toString(),
                getSchemeCalendar(year, month, 15, "假"));
        map.put(getSchemeCalendar(year, month, 18, "记").toString(),
                getSchemeCalendar(year, month, 18, "记"));
        map.put(getSchemeCalendar(year, month, 25, "假").toString(),
                getSchemeCalendar(year, month, 25, "假"));
        map.put(getSchemeCalendar(year, month, 27, "多").toString(),
                getSchemeCalendar(year, month, 27, "多"));
        //此方法在巨大的数据量上不影响遍历性能，推荐使用
        mCalendarView.setSchemeDate(map);*/

    }


    @Override
    public void onClick(View v) {

    }

    private Calendar getSchemeCalendar(int year, int month, int day, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(Color.WHITE);
        calendar.setScheme(text);
        calendar.addScheme(0xFFa8b015, "rightTop");
        calendar.addScheme(0xFF423cb0, "leftTop");
        calendar.addScheme(0xFF643c8c, "bottom");

        return calendar;
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        mTextLunar.setVisibility(View.VISIBLE);
        mTextYear.setVisibility(View.VISIBLE);
        mTextMonthDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
        mTextYear.setText(String.valueOf(calendar.getYear()));
        mTextLunar.setText(calendar.getLunar());
        mYear = calendar.getYear();
        choose_date=calendar.getYear()+"-"+calendar.getMonth() + "-" + calendar.getDay();
        date_choose.setDay(calendar.getDay());
        date_choose.setMonth(calendar.getMonth());
        date_choose.setYear(calendar.getYear());
        initshow();
    }

    @Override
    public void onYearChange(int year) {
        mTextMonthDay.setText(String.valueOf(year));
    }
    public void initshow(){

        dataBank_show_Accept = new DataBankAccept(this.getContext());
        dataBank_show_Accept.Load();//将代码读取进来
        dataBank_show_Give = new DataBankGive(this.getContext());
        dataBank_show_Give.Load();//将代码读取进来
        list_show_accept = dataBank_show_Accept.getAccepts();
        list_show_give = dataBank_show_Give.getGives();
        accept_show_size = list_show_accept.size();
        give_show_size = list_show_give.size();

        adapter_show_accept = new AcceptAdapter(this.getContext(), R.layout.item_accept, dataBank_show_Accept.getAccepts());
        adapter_show_give = new GiveAdapter(this.getContext(),R.layout.item_give_group,R.layout.item_give,dataBank_show_Give.getGives());

        for(int i=0;i<accept_show_size;i++) {

            accept_year=dataBank_show_Accept.getAccepts().get(i).getYear();
            accept_month=dataBank_show_Accept.getAccepts().get(i).getMonth();
            accept_day=dataBank_show_Accept.getAccepts().get(i).getDay();
            accept_Name=dataBank_show_Accept.getAccepts().get(i).getName();
            accept_money=dataBank_show_Accept.getAccepts().get(i).getMoney();
            accept_reason=dataBank_show_Accept.getAccepts().get(i).getWhy();
            accept_date=accept_year+"-"+accept_month+"-"+accept_day;
            if(accept_date.equals(choose_date)){
                show_accept = show_accept+"收礼:"+accept_date+"   "+accept_Name+"    "+accept_money+"    "+accept_reason+"\n";
            }
        }
        if(show_accept.equals("")) text_show_accept.setText("无记录");
        else text_show_accept.setText(show_accept);
        show_accept = "";

        for(int i=0;i<give_show_size;i++) {

            give_year=dataBank_show_Give.getGives().get(i).getYear();
            give_month=dataBank_show_Give.getGives().get(i).getMonth();
            give_day=dataBank_show_Give.getGives().get(i).getDay();
            give_Name=dataBank_show_Give.getGives().get(i).getName();
            give_money=dataBank_show_Give.getGives().get(i).getMoney();
            give_reason=dataBank_show_Give.getGives().get(i).getWhy();
            give_date=give_year+"-"+give_month+"-"+give_day;
            if(give_date.equals(choose_date)){
                show_give = show_give+"随礼:"+give_date+"   "+give_Name+"    "+give_money+"    "+give_reason+"\n";
            }
        }
        if(show_give.equals("")) text_show_give.setText("无记录");
        else text_show_give.setText(show_give);
        show_give = "";
    }

    public interface OnCalendarSelectListener extends com.haibin.calendarview.CalendarView.OnCalendarSelectListener{

        /**
         * 超出范围越界
         *
         * @param calendar calendar
         */
        void onCalendarOutOfRange(Calendar calendar);

        /**
         * 日期选择事件
         *
         * @param calendar calendar
         * @param isClick  isClick
         */
        void onCalendarSelect(Calendar calendar, boolean isClick);
    }

}

