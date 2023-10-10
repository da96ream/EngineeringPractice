package com.example.engineeringpractice;


import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Slide;
import android.view.View;
import android.view.View.OnClickListener;
import android.graphics.drawable.AnimationDrawable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class HomePage extends AppCompatActivity {

    private SlidingDrawer user_slid,temp_slid,humi_slid,smoke_slid,set_slid;
    private Button user_btn,temp_btn,humi_btn,smoke_btn,set_btn;
    private TextView username;

    private TextView livingroom_temperature,bedroom_temperature;
    private TextView livingroom_humidity,bedroom_humidity;
    private TextView livingroom_smoke,bedroom_smoke;

    private EditText livingroom_temp_set,livingroom_humi_set,livingroom_smoke_set;
    int livingroom_temp_set_int,livingroom_humi_set_int,livingroom_smoke_set_int;
    private EditText bedroom_temp_set,bedroom_humi_set,bedroom_smoke_set;
    int bedroom_temp_set_int,bedroom_humi_set_int,bedroom_smoke_set_int;

    SQLiteDatabase db;
    Cursor temperature_cursor,humidity_cursor,smoke_cursor;

    //实例化曲线图
    private LineChart temp_linechart;
    LineData temp_linedata;
    LineDataSet livingroom_temp_line;
    LineDataSet bedroom_temp_line;
    private LineChart humi_linechart;
    LineData humi_linedata;
    LineDataSet livingroom_humi_line;
    LineDataSet bedroom_humi_line;
    private LineChart smoke_linechart;
    LineData smoke_linedata;
    LineDataSet livingroom_smoke_line;
    LineDataSet bedroom_smoke_line;

    //实例化list，用来保存数据
    List<Entry> livingroom_temp_list = new ArrayList<>();//客厅温度
    int livingroom_temp_0_y = 20;
    int livingroom_temp_1_y = 25;
    int livingroom_temp_2_y = 25;
    int livingroom_temp_3_y = 25;
    int livingroom_temp_4_y = 30;
    Entry livingroom_temp0 = new Entry(0,livingroom_temp_0_y);
    Entry livingroom_temp1 = new Entry(1,livingroom_temp_1_y);
    Entry livingroom_temp2 = new Entry(2,livingroom_temp_2_y);
    Entry livingroom_temp3 = new Entry(3,livingroom_temp_3_y);
    Entry livingroom_temp4 = new Entry(4,livingroom_temp_4_y);
    List<Entry> bedroom_temp_list = new ArrayList<>();//卧室温度
    int bedroom_temp_0_y = 20;
    int bedroom_temp_1_y = 25;
    int bedroom_temp_2_y = 25;
    int bedroom_temp_3_y = 25;
    int bedroom_temp_4_y = 30;
    Entry bedroom_temp0 = new Entry(0,bedroom_temp_0_y);
    Entry bedroom_temp1 = new Entry(1,bedroom_temp_1_y);
    Entry bedroom_temp2 = new Entry(2,bedroom_temp_2_y);
    Entry bedroom_temp3 = new Entry(3,bedroom_temp_3_y);
    Entry bedroom_temp4 = new Entry(4,bedroom_temp_4_y);

    List<Entry> livingroom_humi_list = new ArrayList<>();//客厅湿度
    int livingroom_humi_0_y = 50;
    int livingroom_humi_1_y = 53;
    int livingroom_humi_2_y = 56;
    int livingroom_humi_3_y = 59;
    int livingroom_humi_4_y = 62;
    Entry livingroom_humi0 = new Entry(0,livingroom_humi_0_y);
    Entry livingroom_humi1 = new Entry(1,livingroom_humi_1_y);
    Entry livingroom_humi2 = new Entry(2,livingroom_humi_2_y);
    Entry livingroom_humi3 = new Entry(3,livingroom_humi_3_y);
    Entry livingroom_humi4 = new Entry(4,livingroom_humi_4_y);
    List<Entry> bedroom_humi_list = new ArrayList<>();//卧室湿度
    int bedroom_humi_0_y = 50;
    int bedroom_humi_1_y = 53;
    int bedroom_humi_2_y = 56;
    int bedroom_humi_3_y = 59;
    int bedroom_humi_4_y = 62;
    Entry bedroom_humi0 = new Entry(0,bedroom_humi_0_y);
    Entry bedroom_humi1 = new Entry(1,bedroom_humi_1_y);
    Entry bedroom_humi2 = new Entry(2,bedroom_humi_2_y);
    Entry bedroom_humi3 = new Entry(3,bedroom_humi_3_y);
    Entry bedroom_humi4 = new Entry(4,bedroom_humi_4_y);

    List<Entry> livingroom_smoke_list = new ArrayList<>();//客厅烟雾浓度
    int livingroom_smoke_0_y = 1;
    int livingroom_smoke_1_y = 2;
    int livingroom_smoke_2_y = 3;
    int livingroom_smoke_3_y = 4;
    int livingroom_smoke_4_y = 5;
    Entry livingroom_smoke0 = new Entry(0,livingroom_smoke_0_y);
    Entry livingroom_smoke1 = new Entry(1,livingroom_smoke_1_y);
    Entry livingroom_smoke2 = new Entry(2,livingroom_smoke_2_y);
    Entry livingroom_smoke3 = new Entry(3,livingroom_smoke_3_y);
    Entry livingroom_smoke4 = new Entry(4,livingroom_smoke_4_y);
    List<Entry> bedroom_smoke_list = new ArrayList<>();//卧室烟雾浓度
    int bedroom_smoke_0_y = 1;
    int bedroom_smoke_1_y = 2;
    int bedroom_smoke_2_y = 3;
    int bedroom_smoke_3_y = 4;
    int bedroom_smoke_4_y = 5;
    Entry bedroom_smoke0 = new Entry(0,bedroom_smoke_0_y);
    Entry bedroom_smoke1 = new Entry(1,bedroom_smoke_1_y);
    Entry bedroom_smoke2 = new Entry(2,bedroom_smoke_2_y);
    Entry bedroom_smoke3 = new Entry(3,bedroom_smoke_3_y);
    Entry bedroom_smoke4 = new Entry(4,bedroom_smoke_4_y);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        livingroom_temp_set = (EditText)findViewById(R.id.livingroom_temp_set);
        livingroom_humi_set = (EditText)findViewById(R.id.livingroom_humi_set);
        livingroom_smoke_set = (EditText)findViewById(R.id.livingroom_smoke_set);
        bedroom_temp_set = (EditText)findViewById(R.id.bedroom_temp_set);
        bedroom_humi_set = (EditText)findViewById(R.id.bedroom_humi_set);
        bedroom_smoke_set = (EditText)findViewById(R.id.bedroom_smoke_set);
        livingroom_temp_set_int = Integer.parseInt(livingroom_temp_set.getText().toString());
        livingroom_humi_set_int = Integer.parseInt(livingroom_humi_set.getText().toString());
        livingroom_smoke_set_int = Integer.parseInt(livingroom_smoke_set.getText().toString());
        bedroom_temp_set_int = Integer.parseInt(bedroom_temp_set.getText().toString());
        bedroom_humi_set_int = Integer.parseInt(bedroom_humi_set.getText().toString());
        bedroom_smoke_set_int = Integer.parseInt(bedroom_smoke_set.getText().toString());


        username = (TextView) findViewById(R.id.user);
        Bundle bundle = this.getIntent().getExtras();
        String str = bundle.getString("text");
        username.setText(str);

        user_btn = (Button) findViewById(R.id.user_btn);
        user_slid = (SlidingDrawer) findViewById(R.id.user_slid);
        temp_btn = (Button) findViewById(R.id.temp_btn);
        temp_slid = (SlidingDrawer) findViewById(R.id.temp_slid);
        humi_btn = (Button) findViewById(R.id.humi_btn);
        humi_slid = (SlidingDrawer) findViewById(R.id.humi_slid);
        smoke_btn = (Button) findViewById(R.id.smoke_btn);
        smoke_slid = (SlidingDrawer) findViewById(R.id.smoke_slid);
        set_btn = (Button) findViewById(R.id.set_btn);
        set_slid = (SlidingDrawer) findViewById(R.id.set_slid);

        livingroom_temperature = (TextView)findViewById(R.id.livingroomtemperature);
        bedroom_temperature = (TextView)findViewById(R.id.bedroomtemperature);
        livingroom_humidity = (TextView)findViewById(R.id.livingroomhumidity);
        bedroom_humidity = (TextView)findViewById(R.id.bedroomhumidity);
        livingroom_smoke = (TextView)findViewById(R.id.livingroomsmoke);
        bedroom_smoke = (TextView)findViewById(R.id.bedroomsmoke);

        temp_linechart = (LineChart)findViewById(R.id.temp_linechart);
        temp_linechart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        temp_linechart.getAxisRight().setEnabled(false);
        temp_linechart.getXAxis().setDrawGridLines(false);
        livingroom_temp_list.add(livingroom_temp0);
        livingroom_temp_list.add(livingroom_temp1);
        livingroom_temp_list.add(livingroom_temp2);
        livingroom_temp_list.add(livingroom_temp3);
        livingroom_temp_list.add(livingroom_temp4);
        bedroom_temp_list.add(bedroom_temp0);
        bedroom_temp_list.add(bedroom_temp1);
        bedroom_temp_list.add(bedroom_temp2);
        bedroom_temp_list.add(bedroom_temp3);
        bedroom_temp_list.add(bedroom_temp4);
        livingroom_temp_line = new LineDataSet(livingroom_temp_list,"客厅");
        livingroom_temp_line.setLineWidth(5f);
        livingroom_temp_line.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        livingroom_temp_line.setColor(Color.RED);
        bedroom_temp_line = new LineDataSet(bedroom_temp_list,"卧室");
        bedroom_temp_line.setLineWidth(5f);
        bedroom_temp_line.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        temp_linedata = new LineData(livingroom_temp_line);
        temp_linedata.addDataSet(bedroom_temp_line);
        temp_linechart.setData(temp_linedata);
        temp_linechart.getLineData().setValueTextSize(20f);
        temp_linechart.getXAxis().setTextSize(20f);
        temp_linechart.getAxisLeft().setTextSize(20f);
        temp_linechart.getLegend().setTextSize(20f);
        humi_linechart = (LineChart)findViewById(R.id.humi_linechart);
        humi_linechart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        humi_linechart.getAxisRight().setEnabled(false);
        humi_linechart.getXAxis().setDrawGridLines(false);
        livingroom_humi_list.add(livingroom_humi0);
        livingroom_humi_list.add(livingroom_humi1);
        livingroom_humi_list.add(livingroom_humi2);
        livingroom_humi_list.add(livingroom_humi3);
        livingroom_humi_list.add(livingroom_humi4);
        bedroom_humi_list.add(bedroom_humi0);
        bedroom_humi_list.add(bedroom_humi1);
        bedroom_humi_list.add(bedroom_humi2);
        bedroom_humi_list.add(bedroom_humi3);
        bedroom_humi_list.add(bedroom_humi4);
        livingroom_humi_line = new LineDataSet(livingroom_humi_list,"客厅");
        livingroom_humi_line.setLineWidth(5f);
        livingroom_humi_line.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        livingroom_humi_line.setColor(Color.RED);
        bedroom_humi_line = new LineDataSet(bedroom_humi_list,"卧室");
        bedroom_humi_line.setLineWidth(5f);
        bedroom_humi_line.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        humi_linedata = new LineData(livingroom_humi_line);
        humi_linedata.addDataSet(bedroom_humi_line);
        humi_linechart.setData(humi_linedata);
        humi_linechart.getLineData().setValueTextSize(20f);
        humi_linechart.getXAxis().setTextSize(20f);
        humi_linechart.getAxisLeft().setTextSize(20f);
        humi_linechart.getLegend().setTextSize(20f);
        smoke_linechart = (LineChart)findViewById(R.id.smoke_linechart);
        smoke_linechart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        smoke_linechart.getAxisRight().setEnabled(false);
        smoke_linechart.getXAxis().setDrawGridLines(false);
        livingroom_smoke_list.add(livingroom_smoke0);
        livingroom_smoke_list.add(livingroom_smoke1);
        livingroom_smoke_list.add(livingroom_smoke2);
        livingroom_smoke_list.add(livingroom_smoke3);
        livingroom_smoke_list.add(livingroom_smoke4);
        bedroom_smoke_list.add(bedroom_smoke0);
        bedroom_smoke_list.add(bedroom_smoke1);
        bedroom_smoke_list.add(bedroom_smoke2);
        bedroom_smoke_list.add(bedroom_smoke3);
        bedroom_smoke_list.add(bedroom_smoke4);
        livingroom_smoke_line = new LineDataSet(livingroom_smoke_list,"客厅");
        livingroom_smoke_line.setLineWidth(5f);
        livingroom_smoke_line.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        livingroom_smoke_line.setColor(Color.RED);
        bedroom_smoke_line = new LineDataSet(bedroom_smoke_list,"卧室");
        bedroom_smoke_line.setLineWidth(5f);
        bedroom_smoke_line.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        smoke_linedata = new LineData(livingroom_smoke_line);
        smoke_linedata.addDataSet(bedroom_smoke_line);
        smoke_linechart.setData(smoke_linedata);
        smoke_linechart.getLineData().setValueTextSize(20f);
        smoke_linechart.getXAxis().setTextSize(20f);
        smoke_linechart.getAxisLeft().setTextSize(20f);
        smoke_linechart.getLegend().setTextSize(20f);

        db = openOrCreateDatabase("Database.db",Context.MODE_PRIVATE,null);
        temperature_cursor = db.query("temperature",null,null,null,null,null,null);
        humidity_cursor = db.query("humidity",null,null,null,null,null,null);
        smoke_cursor = db.query("smoke",null,null,null,null,null,null);

        temperature_cursor.moveToFirst();
        humidity_cursor.moveToFirst();
        smoke_cursor.moveToFirst();

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (temperature_cursor.isLast()&&humidity_cursor.isLast()&&smoke_cursor.isLast()){
                    temperature_cursor.moveToFirst();
                    humidity_cursor.moveToFirst();
                    smoke_cursor.moveToFirst();
                    livingroom_temperature.setText(temperature_cursor.getString(0));
                    bedroom_temperature.setText(temperature_cursor.getString(1));
                    livingroom_humidity.setText(humidity_cursor.getString(0));
                    bedroom_humidity.setText(humidity_cursor.getString(1));
                    livingroom_smoke.setText(smoke_cursor.getString(0));
                    bedroom_smoke.setText(smoke_cursor.getString(1));
                }else{
                    temperature_cursor.moveToNext();
                    humidity_cursor.moveToNext();
                    smoke_cursor.moveToNext();
                    livingroom_temperature.setText(temperature_cursor.getString(0));
                    bedroom_temperature.setText(temperature_cursor.getString(1));
                    livingroom_humidity.setText(humidity_cursor.getString(0));
                    bedroom_humidity.setText(humidity_cursor.getString(1));
                    livingroom_smoke.setText(smoke_cursor.getString(0));
                    bedroom_smoke.setText(smoke_cursor.getString(1));
                }

                livingroom_temp_4_y = livingroom_temp_3_y;
                livingroom_temp_3_y = livingroom_temp_2_y;
                livingroom_temp_2_y = livingroom_temp_1_y;
                livingroom_temp_1_y = livingroom_temp_0_y;
                livingroom_temp_0_y = Integer.parseInt(livingroom_temperature.getText().toString());
                livingroom_temp0.setY(livingroom_temp_0_y);
                livingroom_temp1.setY(livingroom_temp_1_y);
                livingroom_temp2.setY(livingroom_temp_2_y);
                livingroom_temp3.setY(livingroom_temp_3_y);
                livingroom_temp4.setY(livingroom_temp_4_y);
                bedroom_temp_4_y = bedroom_temp_3_y;
                bedroom_temp_3_y = bedroom_temp_2_y;
                bedroom_temp_2_y = bedroom_temp_1_y;
                bedroom_temp_1_y = bedroom_temp_0_y;
                bedroom_temp_0_y = Integer.parseInt(bedroom_temperature.getText().toString());
                bedroom_temp0.setY(bedroom_temp_0_y);
                bedroom_temp1.setY(bedroom_temp_1_y);
                bedroom_temp2.setY(bedroom_temp_2_y);
                bedroom_temp3.setY(bedroom_temp_3_y);
                bedroom_temp4.setY(bedroom_temp_4_y);
                livingroom_humi_4_y = livingroom_humi_3_y;
                livingroom_humi_3_y = livingroom_humi_2_y;
                livingroom_humi_2_y = livingroom_humi_1_y;
                livingroom_humi_1_y = livingroom_humi_0_y;
                livingroom_humi_0_y = Integer.parseInt(livingroom_humidity.getText().toString());
                livingroom_humi0.setY(livingroom_humi_0_y);
                livingroom_humi1.setY(livingroom_humi_1_y);
                livingroom_humi2.setY(livingroom_humi_2_y);
                livingroom_humi3.setY(livingroom_humi_3_y);
                livingroom_humi4.setY(livingroom_humi_4_y);
                bedroom_humi_4_y = bedroom_humi_3_y;
                bedroom_humi_3_y = bedroom_humi_2_y;
                bedroom_humi_2_y = bedroom_humi_1_y;
                bedroom_humi_1_y = bedroom_humi_0_y;
                bedroom_humi_0_y = Integer.parseInt(bedroom_humidity.getText().toString());
                bedroom_humi0.setY(bedroom_humi_0_y);
                bedroom_humi1.setY(bedroom_humi_1_y);
                bedroom_humi2.setY(bedroom_humi_2_y);
                bedroom_humi3.setY(bedroom_humi_3_y);
                bedroom_humi4.setY(bedroom_humi_4_y);
                livingroom_smoke_4_y = livingroom_smoke_3_y;
                livingroom_smoke_3_y = livingroom_smoke_2_y;
                livingroom_smoke_2_y = livingroom_smoke_1_y;
                livingroom_smoke_1_y = livingroom_smoke_0_y;
                livingroom_smoke_0_y = Integer.parseInt(livingroom_smoke.getText().toString());
                livingroom_smoke0.setY(livingroom_smoke_0_y);
                livingroom_smoke1.setY(livingroom_smoke_1_y);
                livingroom_smoke2.setY(livingroom_smoke_2_y);
                livingroom_smoke3.setY(livingroom_smoke_3_y);
                livingroom_smoke4.setY(livingroom_smoke_4_y);
                bedroom_smoke_4_y = bedroom_smoke_3_y;
                bedroom_smoke_3_y = bedroom_smoke_2_y;
                bedroom_smoke_2_y = bedroom_smoke_1_y;
                bedroom_smoke_1_y = bedroom_smoke_0_y;
                bedroom_smoke_0_y = Integer.parseInt(bedroom_smoke.getText().toString());
                bedroom_smoke0.setY(bedroom_smoke_0_y);
                bedroom_smoke1.setY(bedroom_smoke_1_y);
                bedroom_smoke2.setY(bedroom_smoke_2_y);
                bedroom_smoke3.setY(bedroom_smoke_3_y);
                bedroom_smoke4.setY(bedroom_smoke_4_y);
                temp_linechart.notifyDataSetChanged();
                temp_linechart.invalidate();
                humi_linechart.notifyDataSetChanged();
                humi_linechart.invalidate();
                smoke_linechart.notifyDataSetChanged();
                smoke_linechart.invalidate();

                if(livingroom_temp_set_int <= Integer.parseInt(livingroom_temperature.getText().toString())){
                    Toast.makeText(HomePage.this, "客厅温度达到"+livingroom_temp_set_int+"℃", Toast.LENGTH_SHORT).show();
                }
                if(livingroom_humi_set_int <= Integer.parseInt(livingroom_humidity.getText().toString())){
                    Toast.makeText(HomePage.this, "客厅湿度达到"+livingroom_humi_set_int+"%", Toast.LENGTH_SHORT).show();
                }
                if(livingroom_smoke_set_int <= Integer.parseInt(livingroom_smoke.getText().toString())){
                    Toast.makeText(HomePage.this, "客厅烟雾浓度达到"+livingroom_smoke_set_int+"%", Toast.LENGTH_SHORT).show();
                }
                if(bedroom_temp_set_int <= Integer.parseInt(bedroom_temperature.getText().toString())){
                    Toast.makeText(HomePage.this, "卧室温度达到"+bedroom_temp_set_int+"℃", Toast.LENGTH_SHORT).show();
                }
                if(bedroom_humi_set_int <= Integer.parseInt(bedroom_humidity.getText().toString())){
                    Toast.makeText(HomePage.this, "卧室湿度达到"+bedroom_humi_set_int+"%", Toast.LENGTH_SHORT).show();
                }
                if(bedroom_smoke_set_int <= Integer.parseInt(bedroom_smoke.getText().toString())){
                    Toast.makeText(HomePage.this, "卧室烟雾浓度达到"+bedroom_smoke_set_int+"%", Toast.LENGTH_SHORT).show();
                }

                handler.postDelayed(this,2500);
            }
        };
        handler.postDelayed(runnable,2500);

    }

}