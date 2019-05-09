package com.ejercicios.fdegregorio.musicapp.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class AdapterQuizViewPager extends FragmentPagerAdapter {

    private List<FragmentQuiz> lstFragmentQuiz;

    public AdapterQuizViewPager(FragmentManager fm) {
        super(fm);
        this.lstFragmentQuiz = new ArrayList<>();
    }


    public void setLstFragmentQuiz(List<FragmentQuiz> lst){
        this.lstFragmentQuiz = lst;
        notifyDataSetChanged();
    }

    public List<FragmentQuiz> getLstFragmentQuiz() {
        return lstFragmentQuiz;
    }

    @Override
    public Fragment getItem(int i) {
        return lstFragmentQuiz.get(i);
    }

    @Override
    public int getCount() {
        return lstFragmentQuiz.size();
    }
}
