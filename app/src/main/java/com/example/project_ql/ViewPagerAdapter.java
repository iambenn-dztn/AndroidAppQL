package com.example.project_ql;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.project_ql.DonNhap.nhaphang_frag;
import com.example.project_ql.DonXuat.xuathang_frag;
import com.example.project_ql.SanPham.sanpham_frag;
import com.example.project_ql.TraHang.trahang_frag;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new sanpham_frag();
            case 1:
                return new nhaphang_frag();
            case 2:
                return new xuathang_frag();
            case 3:
                return new trahang_frag();
            default:
                return new sanpham_frag();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
