package com.example.project_ql;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.project_ql.DataBase.DataBase;
import com.example.project_ql.NoDaiLy.NoDaiLyActivity;
import com.example.project_ql.ThongKeNhap.ThongKeNhapActivity;
import com.example.project_ql.NoNhaSanXuat.NoNSXActivity;
import com.example.project_ql.ThongKeXuat.ThongKeXuatActivity;
import com.example.project_ql.model.SanPham;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView nav;
    ViewPager vp;
    DataBase database;
    public static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager=getSupportFragmentManager();

//        DB
        database=new DataBase(this, "quanlydiennuoc.sqlite",null, 1);
        kiemTraHangTon();
//        database.QueryData("DELETE FROM tblSanPham");
//        database.QueryData("DELETE FROM tblDonNhap");
//        database.QueryData("DELETE FROM tblDonXuat");
//        database.QueryData("DELETE FROM tblNhaSanXuat");
//        database.QueryData("DELETE FROM tblDaiLy");

        database.QueryData("CREATE TABLE IF NOT EXISTS tblSanPham(ma INTEGER PRIMARY KEY AUTOINCREMENT, ten VARCHAR(200), soluong INTEGER, gianhap INTEGER, giaban INTEGER, nhaphanphoi VARCHAR(200), ghichu VARCHAR(200), anh BLOB)");
        database.QueryData("CREATE TABLE IF NOT EXISTS tblDonNhap(ma INTEGER PRIMARY KEY AUTOINCREMENT, tennsx VARCHAR(200), tensp VARCHAR(400), soluong VARCHAR(400), tongtien INTEGER, datra INTEGER, conno INTEGER, ngay VARCHAR(200), ghichu VARCHAR(200))");
        database.QueryData("CREATE TABLE IF NOT EXISTS tblDonXuat(ma INTEGER PRIMARY KEY AUTOINCREMENT, tendaily VARCHAR(200), tensp VARCHAR(400), soluong VARCHAR(400), tongtien INTEGER, datra INTEGER, conno INTEGER, ngay VARCHAR(200), ghichu VARCHAR(200))");
        database.QueryData("CREATE TABLE IF NOT EXISTS tblNhaSanXuat(ma INTEGER PRIMARY KEY AUTOINCREMENT, tennsx VARCHAR(200), conno INTEGER)");
        database.QueryData("CREATE TABLE IF NOT EXISTS tblDaiLy(ma INTEGER PRIMARY KEY AUTOINCREMENT, tendaily VARCHAR(200), conno INTEGER)");
        //        database.QueryData("DELETE FROM tblSanPham");
//        database.QueryData("INSERT INTO tblSanPham VALUES("+null+",'cut25',"+5+","+10000+","+15000+",'Hong Thang','',"+R.drawable.imgnhap+")");
        initView();
//        Set ViewPager
        vp.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));
        vp.setOffscreenPageLimit(3);
        //        Set Change Pager
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        nav.getMenu().findItem(R.id.menu_tab1).setChecked(true);
                        break;
                    case 1:
                        nav.getMenu().findItem(R.id.menu_tab2).setChecked(true);
                        break;
                    case 2:
                        nav.getMenu().findItem(R.id.menu_tab3).setChecked(true);
                        break;
                    case 3:
                        nav.getMenu().findItem(R.id.menu_tab4).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_tab1:
                        vp.setCurrentItem(0);
                        break;
                    case R.id.menu_tab2:
                        vp.setCurrentItem(1);
                        break;
                    case R.id.menu_tab3:
                        vp.setCurrentItem(2);
                        break;
                    case R.id.menu_tab4:
                        vp.setCurrentItem(3);
                        break;
                }
                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemThoat:
                finish();
                System.exit(0);
                return true;
            case R.id.itemNhap:
                Intent myIntent = new Intent(MainActivity.this, ThongKeNhapActivity.class);
//                myIntent.putExtra("key", 1); //Optional parameters
                MainActivity.this.startActivity(myIntent);
                return true;
            case R.id.itemXuat:
                Intent myIntent2 = new Intent(MainActivity.this, ThongKeXuatActivity.class);
//                myIntent.putExtra("key", 1); //Optional parameters
                MainActivity.this.startActivity(myIntent2);
                return true;
            case R.id.itemNoNSX:
                Intent myIntent1 = new Intent(MainActivity.this, NoNSXActivity.class);
//                myIntent.putExtra("key", 1); //Optional parameters
                MainActivity.this.startActivity(myIntent1);
                return true;
            case R.id.itemNoDaiLy:
                Intent myIntent3 = new Intent(MainActivity.this, NoDaiLyActivity.class);
//                myIntent.putExtra("key", 1); //Optional parameters
                MainActivity.this.startActivity(myIntent3);
                return true;
            case R.id.itemDoiMK:
                Intent myIntent4 = new Intent(MainActivity.this, DoiMatKhauActivity.class);
//                myIntent.putExtra("key", 1); //Optional parameters
                MainActivity.this.startActivity(myIntent4);
                return true;
            case R.id.itemHang:
                Intent myIntent5 = new Intent(MainActivity.this, ThongKeHangActivity.class);
//                myIntent.putExtra("key", 1); //Optional parameters
                MainActivity.this.startActivity(myIntent5);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return  super.onCreateOptionsMenu(menu);
    }

    private void initView() {
        nav=findViewById(R.id.bottom_navigation);
        vp=findViewById(R.id.view_pager);
    }
    private void kiemTraHangTon() {
        Cursor data=database.GetData("SELECT * FROM tblSanPham");
        while (data.moveToNext()) {
//            int ma=Integer.parseInt(dataSp.getString(0));
            String ten=data.getString(1);
            int soluong=data.getInt(2);
            if(soluong<=50) {
                sendNotification(ten);
            }
        }
    }

    private void sendNotification(String ten) {
        Notification notification = new
                NotificationCompat.Builder(this, "Thông báo hết hàng")
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle("Thông báo hết hàng")
                .setContentText("Sản phẩm "+ten+" trong kho sắp hết. Hãy nhập hàng để không gián đoạn hoạt động kinh doanh.")
                .setColor(getResources().getColor(R.color.blue))
                .build();
        NotificationManager notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(notification!=null) {
            notificationManager.notify(getNotificationId(),notification);
        }
    }

    private int getNotificationId() {
        return (int) new Date().getTime();
    }

}