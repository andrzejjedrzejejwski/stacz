package seven.more.queuesleuth.core;

import android.app.Application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import seven.more.queuesleuth.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class App extends Application {

    @Inject
    ActivityLifecycleCallbacks lifecycleCallbacks;

    private RemoteApiService service;
    private Department department;

    private static final Logger LOG = LoggerFactory.getLogger(App.class.getSimpleName());

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault("fonts/advantage-book.ttf", R.attr.fontPath);
        SharedObjectGraph.create(this);
        registerActivityLifecycleCallbacks(lifecycleCallbacks);
    }

    public RemoteApiService getService() {
        return service;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setService(RemoteApiService service) {
        this.service = service;
    }
}
