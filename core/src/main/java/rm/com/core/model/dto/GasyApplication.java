package rm.com.core.model.dto;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by oscargallon on 6/4/16.
 */

public class GasyApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
