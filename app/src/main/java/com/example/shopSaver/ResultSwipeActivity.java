package com.example.shopSaver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

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
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("termino")) {
            textoInicial = extras.getString("termino");
        }else{
            textoInicial = "Item";
        }
        bundle.putString("termino",textoInicial);
        pagerAdapter = new ScreenSlidePagerAdapter(this, bundle);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setUserInputEnabled(false);
        TabLayout tabLayout = findViewById(R.id.pestanias);
        new TabLayoutMediator(tabLayout, viewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        switch (position) {
                            case 0:
                                tab.setText(getString(R.string.SuperName1));
                                break;
                            case 1:
                                tab.setText(getString(R.string.SuperName2));
                                break;
                            case 2:
                                tab.setText(getString(R.string.SuperName3));
                                break;
                            case 3:
                                tab.setText(getString(R.string.SuperTab4));
                                break;
                            case 4:
                                tab.setText(getString(R.string.SuperName5));
                                break;
                        }
                    }
                }).attach();
           viewPager.setOffscreenPageLimit(4);


    }
    private static class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        private final Bundle bundle;
        public ScreenSlidePagerAdapter(FragmentActivity fa, Bundle bundle) {
            super(fa);
            this.bundle = bundle;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Fragment fragment;

            switch(position) {

                case 1:
                    fragment = new WebFragmentTienda2();
                    break;
                case 2:
                    fragment = new WebFragmentTienda3();
                    break;
                case 3:
                    fragment = new WebFragmentTienda4();
                    break;
                case 4:
                    fragment = new WebFragmentTienda5();
                    break;
                default:
                    fragment = new WebFragmentTienda1();
            }

            fragment.setArguments(bundle);

            return fragment;
        }

        @Override
        public int getItemCount() {
            return 5;
        }
    }
    public void returnBack(View view) {
        finish();
    }
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Cambiar a la actividad horizontal
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("termino", textoInicial);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        }
    }
}