package com.klowerbase.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.klower.pin.PINActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

    ListView mListView;
    String[] strs = new String[]{"ScanCode", "SlidingMenu",
            "RoundedImageview", "PullGridView", "TextviewMore",
            "ViewpagerIndicator", "GestureImageview", "SoundRecord",
            "Download", "HorizonalListview", "ActionBar", "CustomTextview",
            "StickyScrollView", "Choose photo", "SeeKbar", "SwitchButton",
            "Code", "ViewPager", "WheelView", "ProgressBar", "CropImageview",
            "DataBase", "Signature", "ColorPicker", "Tabhost+Fragment",
            "9LOCK", "LabelLayout", "Gif", "MoveView", "ChartLine", "ChartPie",
            "ChartStack", "RoundCornerProgressBar", "SortListView",
            "SlideListView", "SlideExpandableListView ", "ARCmenu",
            "DatePickerView", "CircleLayout", "FlatUI", "MailSend",
            "CoverFlow", "DialogUtils", "ResideMenu", "Animation",
            "ViewPagerAnimation", "AsymmetricGridView", "UltraPullRefresh",
            "FileChooser", "ToggleBar", "RubberView", "KeyBoard",
            "Velocimeter", "FreeAPI", "XListview", "StepView",
            "DynamicGridView", "CustomViewTest", "PIN", "PercentLayout", "RecycleView",
            "ListGridView", "RichEdittext", "RecycleView", "AppCompatActivity"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // sendMessage(null, "abcdefg");

        mListView = (ListView) findViewById(R.id.function_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1);
        for (int i = 0; i < strs.length; i++) {
            adapter.add(strs[i]);
        }
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {

                Intent intent = null;
                switch (position) {
                    case 0:
                        intent = new Intent(MainActivity.this,
                                MYCaptureActivity.class);
                        break;
                    case 1:
                        intent = new Intent(MainActivity.this,
                                SlidingActivity.class);
                        break;
                    case 2:
                        intent = new Intent(MainActivity.this,
                                RoundedImageviewActivity.class);
                        break;
                    case 3:
                        intent = new Intent(MainActivity.this,
                                PullGridviewActivity.class);
                        break;
                    case 4:
                        intent = new Intent(MainActivity.this,
                                TextViewActivity.class);
                        break;
                    case 5:
                        intent = new Intent(MainActivity.this,
                                ViewpagerIndicatorActivity.class);
                        break;
                    case 6:
                        intent = new Intent(MainActivity.this,
                                GestureViewpagerIndicatorActivity.class);
                        break;
                    case 7:
                        intent = new Intent(MainActivity.this,
                                SoundRecordActivity.class);
                        break;
                    case 8:
                        intent = new Intent(MainActivity.this,
                                DownloadActivity.class);
                        break;
                    case 9:
                        intent = new Intent(MainActivity.this,
                                HorizonalListviewActivity.class);
                        break;
                    case 10:
                        intent = new Intent(MainActivity.this,
                                ActionBarActivity.class);
                        break;
                    case 11:
                        intent = new Intent(MainActivity.this,
                                CustomTextviewActivity.class);
                        break;
                    case 12:
                        intent = new Intent(MainActivity.this,
                                StickyScrollViewActivity.class);
                        break;
                    case 13:
                        intent = new Intent(MainActivity.this,
                                ChoosePhotoActivity.class);
                        break;
                    case 14:
                        intent = new Intent(MainActivity.this,
                                SeekbarActivity.class);
                        break;
                    case 15:
                        intent = new Intent(MainActivity.this,
                                SwitchButtonActivity.class);
                        break;
                    case 16:
                        intent = new Intent(MainActivity.this, CodeActivity.class);
                        break;
                    case 17:
                        intent = new Intent(MainActivity.this,
                                ViewPagerActivity.class);
                        break;
                    case 18:
                        intent = new Intent(MainActivity.this,
                                WheelViewActivity.class);
                        break;
                    case 19:
                        intent = new Intent(MainActivity.this,
                                ProgressBarActivity.class);
                        break;
                    case 20:
                        intent = new Intent(MainActivity.this,
                                CropImageViewActivity.class);
                        break;
                    case 21:
                        intent = new Intent(MainActivity.this, DBActivity.class);
                        break;
                    case 22:
                        intent = new Intent(MainActivity.this,
                                SignatureActivity.class);
                        break;
                    case 23:
                        intent = new Intent(MainActivity.this,
                                ColorPickerActivity.class);
                        break;
                    case 24:
                        intent = new Intent(MainActivity.this,
                                TabHostFragmentActivity.class);
                        break;
                    case 25:
                        intent = new Intent(MainActivity.this, LockActivity.class);
                        break;
                    case 26:
                        intent = new Intent(MainActivity.this,
                                LabelLayoutActivity.class);
                        break;
                    case 27:
                        intent = new Intent(MainActivity.this, GifActivity.class);
                        break;
                    case 28:
                        intent = new Intent(MainActivity.this,
                                TouchMoveImageActivity.class);
                        break;
                    case 29:
                        intent = new Intent(MainActivity.this,
                                ChartLineActivity.class);
                        break;
                    case 30:
                        intent = new Intent(MainActivity.this,
                                ChartPieActivity.class);
                        break;
                    case 31:
                        intent = new Intent(MainActivity.this,
                                ChartStackActivity.class);
                        break;
                    case 32:
                        intent = new Intent(MainActivity.this,
                                ProgressbarActivity2.class);
                        break;
                    case 33:
                        intent = new Intent(MainActivity.this,
                                SortListActivity.class);
                        break;
                    case 34:
                        intent = new Intent(MainActivity.this, SlideActivity.class);
                        break;
                    case 35:
                        intent = new Intent(MainActivity.this,
                                SlideExpandableListViewActivity.class);
                        break;
                    case 36:
                        intent = new Intent(MainActivity.this,
                                ArcMenuActivity.class);
                        break;
                    case 37:
                        intent = new Intent(MainActivity.this,
                                DatePickerViewActivity.class);
                        break;

                    case 38:
                        intent = new Intent(MainActivity.this,
                                CircleLayoutActivity.class);
                        break;

                    case 39:
                        intent = new Intent(MainActivity.this, FlatUIActivity.class);
                        break;
                    case 40:
                        intent = new Intent(MainActivity.this,
                                MailSendActivity.class);
                        break;
                    case 41:
                        intent = new Intent(MainActivity.this,
                                CoverFlowActivity.class);
                        break;
                    case 42:
                        intent = new Intent(MainActivity.this, DialogActivity.class);
                        break;
                    case 43:
                        intent = new Intent(MainActivity.this,
                                ResideMenuActivity.class);
                        break;
                    case 44:
                        intent = new Intent(MainActivity.this,
                                AnimationListActivity.class);
                        break;
                    case 45:
                        intent = new Intent(MainActivity.this,
                                ViewpagerAnimationActivity.class);
                        break;
                    case 46:
                        intent = new Intent(MainActivity.this,
                                AsymmetricgridViewActivity.class);
                        break;
                    case 47:
                        intent = new Intent(MainActivity.this,
                                UITraPullRefreshActivity.class);
                        break;
                    case 48:
                        intent = new Intent(MainActivity.this,
                                FileChooseActivity.class);
                        break;
                    case 49:
                        intent = new Intent(MainActivity.this,
                                ToggleBarActivity.class);
                        break;
                    case 50:
                        intent = new Intent(MainActivity.this,
                                RubberViewActivity.class);
                        break;
                    case 51:
                        intent = new Intent(MainActivity.this,
                                KeyBoardActivity.class);
                        break;
                    case 52:
                        intent = new Intent(MainActivity.this,
                                VelocimeterActivity.class);
                        break;
                    case 53:
                        intent = new Intent(MainActivity.this, APIActivity.class);
                        break;
                    case 54:
                        intent = new Intent(MainActivity.this,
                                XListviewActivity.class);
                        break;
                    case 55:
                        intent = new Intent(MainActivity.this,
                                StepViewActivity.class);
                        break;

                    case 56:
                        intent = new Intent(MainActivity.this,
                                DynamicGridViewActivity.class);
                        break;
                    case 57:
                        intent = new Intent(MainActivity.this,
                                OnDrawActivity.class);
                        break;
                    case 58:
                        intent = new Intent(MainActivity.this,
                                PINActivity.class);
                        break;

                    case 59:
                        intent = new Intent(MainActivity.this,
                                PercentActivity.class);
                        break;
                    case 60:
                        intent = new Intent(MainActivity.this,
                                RecycleViewActivity.class);
                        break;
                    case 61:
                        intent = new Intent(MainActivity.this,
                                ListGridViewActivity.class);
                        break;
                    case 62:
                        intent = new Intent(MainActivity.this,
                                RichEdittextActivity.class);
                        break;

                    case 63:
                        intent = new Intent(MainActivity.this,
                                RecycleViewTest.class);
                        break;
                    case 64:
                        intent = new Intent(MainActivity.this,
                                TestSuperActivity.class);
                        break;

                }
                if (intent != null) {
                    startActivity(intent);
                }

            }
        });

    }

    private void sendMessage(OutputStream out, String str) {
        InputStream inputStream = new ByteArrayInputStream(str.getBytes());
        Toast.makeText(this, getMessage(inputStream), Toast.LENGTH_SHORT)
                .show();
        return;
        // byte [] buf = new byte[1024];
        // int len;
        // try {
        // while((len = inputStream.read(buf)) != -1){
        // out.write(buf, 0, len);
        // }
        // inputStream.close();
        // out.close();
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
    }

    private String getMessage(InputStream in) {
        byte[] buf = new byte[1024];
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int len;
        try {
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
            String msg = out.toString();
            in.close();
            out.close();
            return msg;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
