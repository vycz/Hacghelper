package moe.ggg.hacghelper;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private AlertDialog.Builder builder;
    private SearchView searchView;
    private MenuItem menuItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.about:
                showDialog();
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        menuItem = menu.findItem(R.id.bt_search);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
               searchView.onActionViewCollapsed();
                Intent intent = new Intent(MainActivity.this,SearchResultActivity.class);
                intent.putExtra("key",query);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setQueryHint("输入搜索内容");
        searchView.setSubmitButtonEnabled(true);
        searchView.setIconifiedByDefault(true);
        return true;
    }

    public void initView() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.main_vp);
        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myFragmentPagerAdapter);
        viewPager.setOffscreenPageLimit(6);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }
    public void showDialog(){
        builder = new AlertDialog.Builder(this);
        builder.setTitle("关于");
        builder.setMessage("琉璃神社助手是Tataki个人作品\n只可用于学习交流。\n\n版本:Beta1.0版\n遵守GPL_V3开源协议。");
        builder.setPositiveButton("确定",null);
        builder.create().show();
    }
    public void getReachable(String query){


    }
}
