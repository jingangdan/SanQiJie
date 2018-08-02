package com.chehubang.duolejie.modules.home.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.chehubang.duolejie.NearbyMerchants.NMChildFragment;
import com.chehubang.duolejie.NearbyMerchants.NmSreachActivity;
import com.chehubang.duolejie.NearbyMerchants.NoScrollViewPager;
import com.chehubang.duolejie.NearbyMerchants.SimpleFragmentPagerAdapter;
import com.chehubang.duolejie.R;
import com.chehubang.duolejie.base.BaseFragment;
import com.chehubang.duolejie.model.BrandType;
import com.chehubang.duolejie.modules.home.presenter.NearbyMerchantsPresenter;
import com.chehubang.duolejie.utils.GsonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import common.Utils.ToastUtils;
import common.mvp.activity.MainView;

/**
 * 附近商户
 * Created by jingang on 2018/7/19.
 */
public class NearbyMerchanrsFragment extends BaseFragment<NearbyMerchantsPresenter> implements ViewPager.OnPageChangeListener, MainView {
    Unbinder unbinder;
    @BindView(R.id.iv_nm_back)
    ImageView ivNmBack;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.rv_nm)
    RecyclerView rvNm;
    @BindView(R.id.noScrollViewPager)
    NoScrollViewPager noScrollViewPager;
    @BindView(R.id.et_nm)
    EditText etNm;

    private List<BrandType.BrandTypeListBean> list = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    private SimpleFragmentPagerAdapter sfpAdapter;
    private NMChildFragment mFragment;
    private String type;

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    private String address;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_nearby_merchants, null);
        unbinder = ButterKnife.bind(this, view);

        //判断是否为android6.0系统版本，如果是，需要动态添加权限
        if (Build.VERSION.SDK_INT >= 23) {
            showContacts();
        } else {
            startLocate();
        }

        return view;
    }

    /**
     * 定位
     */
    private void startLocate() {
        mLocationClient = new LocationClient(getActivity().getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        Log.e("ssssss","111");
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        //option.setScanSpan(0);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
//        option.setOpenGps(false);//可选，默认false,设置是否使用gps
//        option.setLocationNotify(false);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
//        option.setIsNeedLocationDescribe(false);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
//        option.setIsNeedLocationPoiList(false);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
//        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
//        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
//        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
        //开启定位
        mLocationClient.start();

        Log.e("ssssss","222");
    }

    @OnClick({R.id.et_nm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_nm:
                if (!TextUtils.isEmpty(address)) {
                    startActivity(new Intent(getActivity(), NmSreachActivity.class).putExtra("address", address));
                } else {
                    ToastUtils.showToast(getActivity(), "定位失败");
                }
                break;
        }
    }
    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            Log.e("ssssss","444");
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");

            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
//            List<Poi> list = location.getPoiList();// POI数据
//            if (list != null) {
//                sb.append("\npoilist size = : ");
//                sb.append(list.size());
//                for (Poi p : list) {
//                    sb.append("\npoi= : ");
//                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
//                }
//            }
            Log.e("ssssss","333");

            if(!TextUtils.isEmpty(String.valueOf(location.getLatitude()))){
                address = location.getLongitude()+","+location.getLatitude();
                mvpPresenter.getBrandTypeList(ACTION_DEFAULT+1);
            }
//            if (!TextUtils.isEmpty(location.getAddrStr())) {
//                address = location.getAddrStr();
//                mvpPresenter.getBrandTypeList(ACTION_DEFAULT + 1);
//            }
        }
    }

    public void setFragment() {
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                type = list.get(i).getBrand_type();
                mFragment = new NMChildFragment();
                Bundle bundle = new Bundle();
                bundle.putString("type", type);
                bundle.putString("brand_type_id", String.valueOf(list.get(i).getId()));
                bundle.putString("user_address", address);
                bundle.putInt("tag", i);
                mFragment.setArguments(bundle);
                fragments.add(mFragment);
            }
        }
        sfpAdapter = new SimpleFragmentPagerAdapter(getActivity().getSupportFragmentManager(), getActivity(), fragments, list);
        noScrollViewPager.setAdapter(sfpAdapter);
        noScrollViewPager.setOffscreenPageLimit(list.size());
        noScrollViewPager.addOnPageChangeListener(this);
        tabLayout.setupWithViewPager(noScrollViewPager);
    }

    @Override
    protected NearbyMerchantsPresenter createPresenter() {
        return new NearbyMerchantsPresenter(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void getDataSuccess(Object model, int action) {
        if (action == ACTION_DEFAULT + 1) {
            BrandType brandType = GsonUtil.gsonIntance().gsonToBean(model.toString(), BrandType.class);
            list.clear();
            list.addAll(brandType.getBrandTypeList());
            setFragment();
        }
    }

    @Override
    public void getDataFail(String msg, int action) {

    }


    public void showContacts() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ToastUtils.showToast(getActivity(),"没有权限,请手动开启定位权限");
            // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
            NearbyMerchanrsFragment.this.requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE},
                    1);
        } else {
            startLocate();
        }
    }

    //Android6.0申请权限的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case 1:
                if (grantResults.length >0&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
                    startLocate();
                    Log.e("ssssss","33333333333333");
                } else {
                    // 没有获取到权限，做特殊处理
                    Log.e("ssssss","ssssssssssssssss");
                    ToastUtils.showToast(getActivity(),"获取位置权限失败，请手动开启");
                    mvpPresenter.getBrandTypeList(ACTION_DEFAULT + 1);
                }
                break;
            default:
                break;
        }
    }
}
