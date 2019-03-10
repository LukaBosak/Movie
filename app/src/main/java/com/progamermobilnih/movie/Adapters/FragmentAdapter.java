package com.progamermobilnih.movie.Adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.progamermobilnih.movie.Fragments.PopularFragment;
import com.progamermobilnih.movie.R;
import com.progamermobilnih.movie.Fragments.TopRatedFragment;


public class FragmentAdapter extends FragmentPagerAdapter {

    private static final int NUMBER_OF_FRAGMENTS = 2;
    Context context;


    public FragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return TopRatedFragment.newInstance("","");

            case 1:
                return PopularFragment.newInstance("","");
        }
        return TopRatedFragment.newInstance("","");
    }

    @Override
    public int getCount() {
        return NUMBER_OF_FRAGMENTS;
    }

    @Nullable
    @Override

    public CharSequence getPageTitle(int position) {

        if(position == 0){
            return context.getString(R.string.topRated);
        } else {
            return context.getString(R.string.popular);
        }
    }
}
