package io.boringideas.utube;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Fragment mFragment = null;
    private FragmentTransaction mFragmentTransaction;
    ImageView playPauseControl, closeControl;
    TextView title;
    FrameLayout controlPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.title_home);

        showFragment(new SearchFragment(),"SEARCH");
//        playPauseControl = findViewById(R.id.playPause);
//        closeControl = findViewById(R.id.close);
//        title = findViewById(R.id.title);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.side_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
//                case R.id.navigation_home:
//                    setTitle(R.string.title_home);
//                    showFragment(new HomeFragment());
//                    return true;
                case R.id.navigation_search:
                    setTitle(R.string.title_search);
                    showFragment(new SearchFragment(),"SEARCH");
                    return true;
//                case R.id.navigation_favorite:
//                    setTitle(R.string.title_favorite);
//                    showFragment(new FavoriteFragment());
//                    return true;
                case R.id.navigation_history:
                    showFragment(new HistoryFragment(), "HISTORY");
                    return true;
            }
            return true;
        }
    };

    private void showFragment(Fragment fragment, String tag) {
//        mFragment = fragment;
        FragmentManager manager = getSupportFragmentManager();
        mFragmentTransaction = manager.beginTransaction();

        Fragment currentFrag = manager.getPrimaryNavigationFragment();
        if(currentFrag != null ){
            mFragmentTransaction.detach(currentFrag);
        }

        Fragment alreadyFrag = manager.findFragmentByTag(tag);
        if(alreadyFrag == null){
            alreadyFrag = fragment;
            mFragmentTransaction.add(R.id.content,alreadyFrag,tag);
        }else{
            mFragmentTransaction.attach(alreadyFrag);
        }
        mFragmentTransaction.setPrimaryNavigationFragment(alreadyFrag);
        mFragmentTransaction.setReorderingAllowed(true);
        mFragmentTransaction.commitNowAllowingStateLoss();
//        mFragment = manager.getPrimaryNavigationFragment();
//        mFragmentTransaction.replace(R.id.content, fragment);
//        mFragmentTransaction.commit();
    }


//    public void onClickHomeCategory(View view){
//
//        if (mFragment.getClass() == HomeFragment.class) {
//            ((HomeFragment)mFragment).onClickCategory(view);
//        }
//
//    }

//    public void showPlayingTile(){
//
//    }
//
//    public void closePlayingTile(){
//
//    }
//
//    public void onControlClick(View view) {
//        switch (view.getId()) {
//            case R.id.playPause:
//                ((SearchFragment)mFragment).playPause(null);
//                break;
//            case R.id.close:
//                ((SearchFragment)mFragment).closeControl();
//                break;
//        }
//    }
}
