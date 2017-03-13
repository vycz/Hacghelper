package moe.ggg.hacghelper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by orange on 2017/3/13.
 */

public class SearchResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.search_tb);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        String SearchKey = getIntent().getStringExtra("key");
        PageFrament pageFrament = PageFrament.getPageFrament(7,SearchKey);
        FragmentManager supportfm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportfm.beginTransaction();
        fragmentTransaction.replace(R.id.search_fl,pageFrament);
        fragmentTransaction.commit();
    }
}
