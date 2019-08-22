package com.example.androidproject;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> fragments = new ArrayList<>();
        ArrayList<String> strings = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int i){
            switch(i){
                case 0:
                    return new FragmentsBots();
                case 1:
                    return new FragmentCam();
                case 2:
                    return new FragmentAdopt();
                case 3:
                    return new FragmentDonation();
                default:
                    return null;
            }
        }

        public int getCount(){
            return fragments.size();
        }

    public void add(Fragment fr, String str) {
        fragments.add(fr);
        strings.add(str);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position){
            return strings.get(position);
    }
}
