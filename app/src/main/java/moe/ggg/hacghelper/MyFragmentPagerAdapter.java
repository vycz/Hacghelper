package moe.ggg.hacghelper;

import android.graphics.pdf.PdfDocument;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;

/**
 * Created by orange on 2017/3/4.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    public final int COUNT = 6;
    private String[] titles = new String[]{"最近更新","文章","动画","漫画","游戏","音乐"};
    private SparseArray<PageFrament> pageFraments;

    @Override
    public Fragment getItem(int position) {
        PageFrament pageFrament = pageFraments.get(position);
        if(pageFrament == null){
            pageFrament = pageFrament.getPageFrament(position+1);
            pageFraments.put(position,pageFrament);
        }
        if(position == 0){
        pageFrament.setShowdialog(1);}
        return pageFrament;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        pageFraments = new SparseArray<>();
    }
}
