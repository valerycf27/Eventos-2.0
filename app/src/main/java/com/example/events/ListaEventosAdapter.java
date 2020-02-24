package com.example.events;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ListaEventosAdapter extends FragmentPagerAdapter {

    private final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] {"Lugar", "Organizador"};

    public ListaEventosAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return LugarFragment.newInstance(position + 1);
        else
            return OrganizadorFragment.newInstance(position + 1);
    }


    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
