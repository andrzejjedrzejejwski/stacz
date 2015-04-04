package seven.more.queuesleuth;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;
import seven.more.queuesleuth.core.App;
import seven.more.queuesleuth.core.Department;
import seven.more.queuesleuth.core.NavigationController;
import seven.more.queuesleuth.core.ResponseEvent;
import seven.more.queuesleuth.core.SharedObjectGraph;

public class StandsActivity extends Activity {

    @Inject
    NavigationController controller;

    @Inject
    Bus bus;

    @InjectView(R.id.list)
    ListView listView;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    private StandsAdapter adapter;
    private Department department;

    private static final Logger LOG = LoggerFactory.getLogger(StandsActivity.class.getSimpleName());
    private TextView header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedObjectGraph.inject(this);
        bus.register(this);
        setContentView(R.layout.stands);
        ButterKnife.inject(this);

        toolbar.setLogo(getResources().getDrawable(R.drawable.logotopright));

        App app = (App) getApplication();
        department = app.getDepartment();


        header = (TextView) LayoutInflater.from(this).inflate(R.layout.header, null);
        header.setText(getString(R.string.stands_header));
        listView.addHeaderView(header);
        populateListView();

    }

    private void populateListView() {
        adapter = new StandsAdapter(this, department.getGroups());
        listView.setAdapter(adapter);
    }

    @OnItemClick(R.id.list)
    public void onListClick(int position) {
        controller.showInfoActivity(position-1); // -1 because of headerView
    }

    @Subscribe
    public void responseFromService(ResponseEvent event) {
        LOG.debug("response from service");
        if (event.getStatus() == ResponseEvent.Status.OK) {
            LOG.debug("status ok");
            department = event.getDepartment();
            populateListView();
        } else {
            LOG.debug("status error");
        }
    }

    @Override
    protected void onDestroy() {
        bus.unregister(this);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        controller.onBackPressed();
    }
}
