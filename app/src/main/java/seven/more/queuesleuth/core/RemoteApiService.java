package seven.more.queuesleuth.core;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.squareup.otto.Bus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import seven.more.queuesleuth.R;

public class RemoteApiService extends Service {

    @Inject
    RemoteApi remoteApi;

    @Inject
    Bus bus;

    private LocalBinder binder = new LocalBinder();
    private Timer timer;

    private int updateFreqInSeconds = 45;

    private static final Logger LOG = LoggerFactory.getLogger(RemoteApiService.class.getSimpleName());

    private String idDepartment = "7ef70889-4eb9-4301-a970-92287db23052";
    private HashMap<String, String> departments;
    @Override
    public IBinder onBind(Intent intent) {
        LOG.debug("■■■ onBind ■■■");
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SharedObjectGraph.inject(this);
        makeYourAwesomeJob();
        LOG.debug("■■■ onCreate ■■■");
        departments.put("Wola", "7ef70889-4eb9-4301-a970-92287db23052");
    }

    private void makeYourAwesomeJob() {
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                LOG.debug("■■■ awesome service task begin ■■■");
                remoteApi.getDepartment(idDepartment, new Callback<Department>() {
                    @Override
                    public void success(Department department, Response response) {

                        LOG.debug("■■■ [SUCCEED] fetch department ■■■");
                        department.setName(getString(R.string.govName));
                        if(SimpleState.isNotificationEnabled()){
                            NotificationBuilder.createAndShow(getApplicationContext(), department.getGroups().get(SimpleState.getGroupPosition()));
                        }
                        bus.post(ResponseEvent.ok(department));
                        LOG.debug("■■■ awesome service task end ■■■");
                        LOG.debug(department.getDate());
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        LOG.error("[FAILED] fetch department", error);
                        bus.post(ResponseEvent.error());
                        LOG.debug("■■■ awesome service task end ■■■");
                    }
                });
            }
        };
        timer.schedule(task, 1000, updateFreqInSeconds * 1000);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        LOG.debug("■■■ onTaskRemoved ■■■");
        timer.cancel();
    }

    @Override
    public void onDestroy() {
        LOG.debug("■■■ onDestroy ■■■");
        timer.cancel();
        super.onDestroy();
    }

    public class LocalBinder extends Binder{
        public RemoteApiService getService(){
            return RemoteApiService.this;
        }
    }


}
