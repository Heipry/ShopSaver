package com.example.ShopSaver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ResultSwipeActivity extends AppCompatActivity {
    String textoInicial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_swipe);
        ViewPager2 viewPager;
        FragmentStateAdapter pagerAdapter;
        viewPager = findViewById(R.id.paginador);
        Bundle bundle = new Bundle();
        textoInicial = getIntent().getExtras().getString("termino");
        bundle.putString("termino",textoInicial);
        pagerAdapter = new ScreenSlidePagerAdapter(this, bundle);
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = findViewById(R.id.pestanias);
        new TabLayoutMediator(tabLayout, viewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        switch (position) {
                            case 0:
                                tab.setText("Dia");
                                break;
                            case 1:
                                tab.setText("Bonarea");
                                break;
                            case 2:
                                tab.setText("Eroski");
                                break;
                        }
                    }
                }).attach();
    }
    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        private final Bundle bundle;
        public ScreenSlidePagerAdapter(FragmentActivity fa, Bundle bundle) {
            super(fa);
            this.bundle = bundle;
        }

        @Override
        public Fragment createFragment(int position) {
            Fragment fragment;

            switch(position) {
                case 0:
                    fragment = new WebFragmentDia();
                    break;
                case 1:
                    fragment = new WebFragmentBonarea();
                    break;
                case 2:
                    fragment = new WebFragmentEroski();
                    break;
                default:
                    fragment = null;
            }
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
}