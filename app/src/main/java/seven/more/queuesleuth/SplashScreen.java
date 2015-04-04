package seven.more.queuesleuth;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

public class SplashScreen extends Dialog {

    private View root;

    public SplashScreen(Context context) {
        super(context, R.style.AppThemeFullscreen);
        setContentView(R.layout.splash);
        root = findViewById(R.id.splash);
    }

    public void dismiss(final Toolbar toolbar, final ListView listView) {
        root.animate().alpha(0.0f).setDuration(1800).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                toolbar.animate().alpha(1.0f).setDuration(1700);
                listView.animate().alpha(1.0f).setDuration(1700);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                SplashScreen.super.dismiss();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();
    }
}
