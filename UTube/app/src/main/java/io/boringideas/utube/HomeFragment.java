package io.boringideas.utube;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class HomeFragment extends Fragment {
    Intent mIntent;

    public HomeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void onClickCategory(View view) {
        switch (view.getId()) {
            case R.id.top_100:
                showActivity(HomeContentActivity.class, "TOP-100");
                break;
            case R.id.trending:
                showActivity(HomeContentActivity.class, "TRENDING");
                break;
            case R.id.recommended:
                showActivity(HomeContentActivity.class, "RECOMMENDED");
                break;
        }
    }

    private void showActivity(Class iclass, String tag) {
        mIntent = new Intent(getActivity(),iclass);
        mIntent.putExtra("CATEGORY", tag);
        startActivity(mIntent);
    }
}
