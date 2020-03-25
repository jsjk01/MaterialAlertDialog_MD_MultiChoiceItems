package com.example.alertdialog_md;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String[] light_items = new String[]{"第一层","第二层","第三层"};
    String floor_choice,light_change,light_no_change;
    private ImageView light_first_floor,light_second_floor,light_third_floor;
    private MaterialButton light_switch;
    private TextView first_status,second_status,third_status;
    boolean[] flag = new boolean[]{false,false,false};
    private TextView[] light_status_r;
    private ImageView[] light_items_r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


    }

    private void initView() {
        light_first_floor = findViewById(R.id.light_first_floor);
        light_second_floor = findViewById(R.id.light_second_floor);
        light_third_floor = findViewById(R.id.light_third_floor);
        light_switch = findViewById(R.id.light_switch);
        light_switch.setOnClickListener(this);
        light_items_r = new ImageView[]{light_first_floor,light_second_floor,light_third_floor};

        //灯状态
        first_status = findViewById(R.id.first_status);
        second_status = findViewById(R.id.second_status);
        third_status = findViewById(R.id.third_status);
        light_status_r = new TextView[]{first_status,second_status,third_status};
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.light_switch:
                light_change = "";
                light_no_change = "";
                for(int i = 0; i <flag.length; i++ ){
                    flag[i] = false;
                }
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(MainActivity.this);
                builder.setTitle("楼层电灯开关");
                builder.setMultiChoiceItems(light_items, flag, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which ,boolean b) {
                                flag[which] = b;
                            }
                        });
                builder.setPositiveButton("打开", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for(int k =0; k<flag.length; k++){
                            if(flag[k]){
                                if(checkLightStatus(light_status_r[k])){
                                    light_no_change += light_items[k] + "  ";
                                }else {
                                    openLight(light_items_r[k]);
                                    light_status_r[k].setText("1");
                                    light_change += light_items[k] + "  ";
                                }
                            }
                        }
                        sendMessage(light_change,light_no_change);
                    }
                });
                builder.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for(int j =0; j<flag.length; j++){
                            if(flag[j]){
                                if(!checkLightStatus(light_status_r[j])){
                                    light_no_change += light_items[j] + "  ";
                                }else {
                                    closeLight(light_items_r[j]);
                                    light_status_r[j].setText("0");
                                    light_change += light_items[j] + "  ";
                                }
                            }
                        }
                        sendMessage(light_change,light_no_change);
                    }
                });
                builder.show();
        }
    }

    private void sendMessage(String light_change, String light_no_change) {
        if(light_change.isEmpty() && !light_no_change.isEmpty()){//只有状态保持的
            Toast.makeText(getApplicationContext(),"状态：" + light_no_change + "灯状态保持", Toast.LENGTH_SHORT).show();
        }else if(!light_change.isEmpty() && light_no_change.isEmpty()){//只有状态改变的
            Toast.makeText(getApplicationContext(),"提示：" + light_change + "灯状态改变", Toast.LENGTH_SHORT).show();
        }else if(light_change.isEmpty() && light_no_change.isEmpty()){//未选中
            Toast.makeText(getApplicationContext(),"提示：" + "未选中任何灯", Toast.LENGTH_SHORT).show();
        }else{//情况都有的
            Toast.makeText(getApplicationContext(),"提示：" + light_change + "灯状态改变", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkLightStatus(TextView textView) {
        try {
            if(textView.getText().toString().equals("1")){
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"空值警告",Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private void openLight(ImageView v) {
        try {
            Glide.with(this).load(R.drawable.light_on).into(v);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),String.valueOf(v) + "不合法",Toast.LENGTH_LONG).show();
        }
    }
    private void closeLight(ImageView v) {
        Glide.with(this).load(R.drawable.light_off).into(v);
    }

}
