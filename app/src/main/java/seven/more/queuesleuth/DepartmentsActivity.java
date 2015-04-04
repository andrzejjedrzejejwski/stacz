package seven.more.queuesleuth;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;
import seven.more.queuesleuth.core.App;
import seven.more.queuesleuth.core.Department;
import seven.more.queuesleuth.core.NavigationController;
import seven.more.queuesleuth.core.RemoteApiService;
import seven.more.queuesleuth.core.ResponseEvent;
import seven.more.queuesleuth.core.SharedObjectGraph;


public class DepartmentsActivity extends Activity {

    @Inject
    NavigationController controller;

    @Inject
    Bus bus;

    @InjectView(R.id.list)
    ListView listView;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    private SplashScreen splashScreen;
    private RemoteApiService service;
    private Department department;
    private ArrayAdapter<Department> adapter;
    private static final Logger LOG = LoggerFactory.getLogger(DepartmentsActivity.class.getSimpleName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedObjectGraph.inject(this);
        bus.register(this);
        setContentView(R.layout.departments);
        ButterKnife.inject(this);

        toolbar.setLogo(getResources().getDrawable(R.drawable.logotopright));
        TextView header = (TextView) LayoutInflater.from(this).inflate(R.layout.header, null);
        header.setText(getString(R.string.departments_header));
        listView.addHeaderView(header, null, false);

        splashScreen = new SplashScreen(this);
        showSplash();
        bindService(new Intent(this, RemoteApiService.class), serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void populateListView() {
        adapter = new DepartmentAdapter(this, new ArrayList<Department>() {{
            add(department);

        }});
        listView.setAdapter(adapter);
    }

    @OnItemClick(R.id.list)
    public void listItemClicked(int position) {
        controller.showStandsActivity(department);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_departments, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void responseFromService(ResponseEvent event) {
        LOG.debug("response from service");
        if (event.getStatus() == ResponseEvent.Status.OK) {
            LOG.debug("status ok");
            LOG.debug(event.toString());
            hideSplash();
            department = event.getDepartment();
            populateListView();
        } else {
            LOG.debug("status error");
            Toast.makeText(DepartmentsActivity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
            hideSplash();
        }
    }

    private void showSplash() {
        toolbar.setAlpha(0.0f);
        listView.setAlpha(0.0f);
        splashScreen.show();
    }

    private void hideSplash() {
        if(splashScreen != null && splashScreen.isShowing()){
            splashScreen.dismiss(toolbar, listView);
        }
    }


    private ServiceConnection serviceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder binder) {
            LOG.debug("Service connected");
            service = ((RemoteApiService.LocalBinder) binder).getService();
            App app = (App) getApplication();
            app.setService(service);
        }

        public void onServiceDisconnected(ComponentName className) {
            LOG.error("this should not ever happen");
        }
    };

    @Override
    protected void onDestroy() {
        bus.unregister(this);
        unbindService(serviceConnection);
        super.onDestroy();
    }
}
