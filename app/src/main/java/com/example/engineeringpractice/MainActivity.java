package com.example.engineeringpractice;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.io.IOException;
import java.time.LocalDate;


public class MainActivity extends AppCompatActivity {

    private TextView textID;
    private TextView textPW;
    private SeekBar login;
    private Socket socket = null;
    private DataInputStream dis = null;
    private DataOutputStream dos = null;
    EditText UserID;
    EditText UserPassword;
    String msg = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //将外部数据库文件导入本地
        MyDatabaseHelper myHelper = new MyDatabaseHelper(MainActivity.this);
        try {
            myHelper.CopyDBFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        textID = (TextView) findViewById(R.id.textID);
        textPW = (TextView) findViewById(R.id.textPW);
        textID.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD_ITALIC);
        textPW.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD_ITALIC);

        VideoView videoView = (VideoView) findViewById(R.id.videoview);
        String uri = "android.resource://" + getPackageName() + "/" +R.raw.background;
        videoView.setVideoURI(Uri.parse(uri));
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setVolume(0f,0f);
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
            }
        });

        login = (SeekBar) findViewById(R.id.login);
        UserID = (EditText) findViewById(R.id.UserID);
        UserPassword = (EditText) findViewById(R.id.UserPassword);

        StrictMode.setThreadPolicy(new StrictMode
                .ThreadPolicy
                .Builder()
                .detectDiskReads()
                .detectNetwork()
                .penaltyLog()
                .build() );

        login.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override //当滑块进度改变时，会执行该方法下的代码
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                if(i==100){
//                    try {
//                        socket = new Socket("192.168.1.106",4321);//创建一个socket流连接到目标主机（请更换为目标主机的ip）
//                    }catch(Exception ioe){
//                        System.out.print("socket err");
//                    }
//
//                    try {
//                        //创建输入流对象dis读取数据，创建输出流对象dos发送数据
//                        dis = new DataInputStream(socket.getInputStream());
//                        dos = new DataOutputStream(socket.getOutputStream());
//                        dos.writeUTF(UserID.getText().toString());
//                        dos.flush();
//                        Thread.sleep(500);
//                        dos.writeUTF(UserPassword.getText().toString());
//                        dos.flush();
//                    }catch(IOException | InterruptedException ioe){
//                        System.out.print("DataStream create err");
//                    }
//                    ReadStr();
//                    try {
//                        socket.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
            }

            @Override //当开始滑动滑块时，会执行该方法下的代码
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override //当滑动结束时，会执行该方法下的代码
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d("",String.valueOf(UserID.getText()));
                Log.d("",String.valueOf(UserPassword.getText()));
                if (seekBar.getProgress() == 100) {
                    if (UserID.getText().toString().trim().equals("aaaaa") && UserPassword.getText().toString().trim().equals("123456")) {
                        Intent intent = new Intent(MainActivity.this,HomePage.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("text",UserID.getText().toString());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                        login.setProgress(0);
                    }
                }
                if(seekBar.getProgress()!=100){
                    login.setProgress(0);
                }
            }

            public void ReadStr(){
                try{
                    if((msg = dis.readUTF()).equals("true")){
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this,HomePage.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("text",UserID.getText().toString());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }else{
                        UserID.setText("账号或密码错误");
                        UserPassword.setText("账号或密码错误");
                        login.setProgress(0);
                    }
                }catch (IOException ioe){
                    System.out.print("ReadStr() err ");
                }
            }

        });

    }

}