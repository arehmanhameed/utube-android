package io.boringideas.utube;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Fragment mFragment = null;
    private FragmentTransaction mFragmentTransaction;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setTitle(R.string.title_home);
                    showFragment(new HomeFragment());
                    return true;
                case R.id.navigation_search:
                    setTitle(R.string.title_search);
                    showFragment(new SearchFragment());
                    return true;
                case R.id.navigation_favorite:
                    setTitle(R.string.title_favorite);
                    showFragment(new FavoriteFragment());
                    return true;
                case R.id.navigation_history:
                    setTitle(R.string.title_history);
                    showFragment(new HistoryFragment());
                    return true;
            }
            return false;
        }
    };

    private void showFragment(Fragment fragment) {
        mFragment = fragment;
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.replace(R.id.content,fragment);
        mFragmentTransaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.title_home);

        showFragment(new HomeFragment());

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.side_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void onClickHomeCategory(View view){

        if (mFragment.getClass() == HomeFragment.class) {
            ((HomeFragment)mFragment).onClickCategory(view);
        }

    }
}
