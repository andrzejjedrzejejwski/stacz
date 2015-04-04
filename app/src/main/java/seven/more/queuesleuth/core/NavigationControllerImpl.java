package seven.more.queuesleuth.core;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

import seven.more.queuesleuth.InfoActivity;
import seven.more.queuesleuth.R;
import seven.more.queuesleuth.StandsActivity;

@Singleton
public class NavigationControllerImpl implements NavigationController, Application.ActivityLifecycleCallbacks {

    private Activity currentActivity;

    private static final Logger LOG = LoggerFactory.getLogger(NavigationControllerImpl.class.getSimpleName());

    @Inject
    public NavigationControllerImpl() {
    }

    @Override
    public void showStandsActivity(Department department) {
        Intent intent = new Intent(currentActivity, StandsActivity.class);
        App app = (App) currentActivity.getApplication();
        app.setDepartment(department);
        currentActivity.startActivity(intent);
        animateFromRight();
    }

    @Override
    public void showInfoActivity(int groupPosition) {
        Intent intent = new Intent(currentActivity, InfoActivity.class);
        SimpleState.setGroupPosition(groupPosition);
        currentActivity.startActivity(intent);
        animateFromRight();
    }

    @Override
    public void onBackPressed() {
        animateFromLeft();
    }

    private void animateFromRight() {
        currentActivity.overridePendingTransition(R.anim.slide_from_right, R.anim.fade_out);
    }

    private void animateFromLeft() {
        currentActivity.overridePendingTransition(R.anim.fade_in, R.anim.slide_from_left);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        currentActivity = activity;
        LOG.debug("currentActivity is {}", currentActivity.getClass().getSimpleName());
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
