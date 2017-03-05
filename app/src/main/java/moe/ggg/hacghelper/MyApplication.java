package moe.ggg.hacghelper;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by orange on 2017/2/21.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
