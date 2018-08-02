package com.chehubang.duolejie.modules.home.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.chehubang.duolejie.R;
import com.chehubang.duolejie.base.BaseActivity;
import com.chehubang.duolejie.config.UserInfo;
import com.chehubang.duolejie.listener.OnButtonClick;
import com.chehubang.duolejie.modules.home.fragment.GiftFragment;
import com.chehubang.duolejie.modules.home.fragment.HomeFragment;
import com.chehubang.duolejie.modules.home.fragment.MyFragment;
import com.chehubang.duolejie.modules.home.fragment.NearbyMerchanrsFragment;
import com.chehubang.duolejie.modules.home.fragment.ShoppingFragment;
import com.chehubang.duolejie.modules.home.presenter.MainPresenter;
import com.chehubang.duolejie.modules.login.activity.LoginActivity;

import java.lang.ref.WeakReference;

import common.mvp.activity.MainView;

public class MainActivity extends BaseActivity<MainPresenter> implements MainView, View.OnClickListener, OnButtonClick {
    public WeakReference<Object> weakReference;
    private FrameLayout home_content;
    private RadioButton rbuttonHome, rbuttonGift, rbuttonShopping, rbuttonMy, rbuttonNm;
    private Fragment mFragment;//当前显示的Fragment
    private HomeFragment fragment1;
    private GiftFragment fragment2;
    private ShoppingFragment fragment3;
    private MyFragment fragment4;
    private NearbyMerchanrsFragment fragment5;
    private OnButtonClick mclick;
    private SwitchReceiver switchReceiver;
    private final String ACTION_SWITCH = "com.chehubang.duolejie.ACTION_SWITCH";

    private SharedPreferences sharedPreferences;
    private String user;//判断用户是否为首次进入

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        initData();

        setFragment();

        sharedPreferences = getSharedPreferences("config", 0);
        user = sharedPreferences.getString("first", "1");

        if (user.equals("1")) {
            if (Build.VERSION.SDK_INT >= 23) {
                requestPermission();
            }

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("first", "0");
            editor.commit();
        }

    }

    public void requestPermission() {
        //定位 相机和摄影 读  写
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                ) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            android.Manifest.permission.ACCESS_FINE_LOCATION,
                    },
                    1);
        } else {
            //正常走
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //权限被用户同意,做相应的事情
            } else {
                //权限被用户拒绝，做相应事情
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



    public void initData() {
        Log.e("ssssss","id = "+UserInfo.getInstance().getId());
        mvpPresenter.isDisplayLineBrand(1, UserInfo.getInstance().getId());
    }

    public void setFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.main_fragment, fragment1).commit();
        mFragment = fragment1;
        switchReceiver = new SwitchReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(switchReceiver, new IntentFilter(ACTION_SWITCH));
    }

    private void initView() {
        home_content = (FrameLayout) findViewById(R.id.main_fragment);
        rbuttonHome = (RadioButton) findViewById(R.id.rb_main_home);
        rbuttonGift = (RadioButton) findViewById(R.id.rb_main_gift);
        rbuttonShopping = (RadioButton) findViewById(R.id.rb_main_shopping);
        rbuttonMy = (RadioButton) findViewById(R.id.rb_main_my);
        rbuttonNm = (RadioButton) findViewById(R.id.rb_main_nm);
        rbuttonHome.setOnClickListener(this);
        rbuttonGift.setOnClickListener(this);
        rbuttonShopping.setOnClickListener(this);
        rbuttonMy.setOnClickListener(this);
        rbuttonNm.setOnClickListener(this);
        fragment1 = new HomeFragment();
        fragment2 = new GiftFragment();
        fragment3 = new ShoppingFragment();
        fragment3.setClickListener(this);
        fragment4 = new MyFragment();
        fragment5 = new NearbyMerchanrsFragment();
        tmpBtn = rbuttonHome;
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    private RadioButton tmpBtn;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_main_home:
                tmpBtn = rbuttonHome;
                switchFragment(fragment1);
                break;
            case R.id.rb_main_gift:
                tmpBtn = rbuttonGift;
                switchFragment(fragment2);
                break;
            case R.id.rb_main_shopping:
                if (!TextUtils.isEmpty(UserInfo.getInstance().getId())) {
                    switchFragment(fragment3);
                    if (mclick != null) {
                        mclick.OnHomeButtonClick(3);
                    }
                } else {
                    if (tmpBtn != null) {
                        tmpBtn.setChecked(true);
                    }
                    rbuttonShopping.setChecked(false);
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rb_main_my:
                tmpBtn = rbuttonMy;
                switchFragment(fragment4);
                if (mclick != null) {
                    mclick.OnHomeButtonClick(4);
                }
                break;

            case R.id.rb_main_nm:
                //附近商户
                tmpBtn = rbuttonNm;
                switchFragment(fragment5);
                break;
            default:
                break;
        }
    }

    public void addButtonClickListener(OnButtonClick clickListener) {
        this.mclick = clickListener;
    }

    private void switchFragment(Fragment fragment) {
        if (mFragment != fragment) {
            if (!fragment.isAdded()) {
                getSupportFragmentManager().beginTransaction().hide(mFragment).add(R.id.main_fragment, fragment).commitAllowingStateLoss();
            } else {
                getSupportFragmentManager().beginTransaction().hide(mFragment).show(fragment).commitAllowingStateLoss();
            }
            mFragment = fragment;
        }
    }

    @Override
    public void getDataSuccess(Object model, int action) {
        if (action == 1) {
            Log.e("ssssss", "datas = " + model.toString());
        }
    }

    @Override
    public void getDataFail(String msg, int action) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(switchReceiver);
    }

    @Override
    public void OnHomeButtonClick(int position) {
        switchFragment(fragment1);
    }


    public class SwitchReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getStringExtra("action");
            if (TextUtils.equals("fragment3", action)) {
                rbuttonShopping.performClick();
            } else if (TextUtils.equals("fragment4", action)) {
                fragment4.onRefreshUserImageHeader();
                rbuttonMy.performClick();
            }
        }
    }
}
