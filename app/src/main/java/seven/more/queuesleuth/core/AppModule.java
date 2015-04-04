package seven.more.queuesleuth.core;

import android.app.Application;
import android.content.Context;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.Callback;
import retrofit.MockRestAdapter;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import seven.more.queuesleuth.DepartmentsActivity;
import seven.more.queuesleuth.InfoActivity;
import seven.more.queuesleuth.StandsActivity;

@Module(injects = {
        App.class,
        DepartmentsActivity.class,
        InfoActivity.class,
        RemoteApiService.class,
        StandsActivity.class
}, library = true)
public class AppModule {

    private Context applicationContext;


    public AppModule(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Provides
    public Context providesApplicationContext(){
        return applicationContext;
    }

    @Provides
    @Singleton
    public NavigationController providesNavigationController(NavigationControllerImpl navigationController){
        return navigationController;
    }

    @Provides
    public Application.ActivityLifecycleCallbacks providesActivityLifecycleCallbacks(NavigationControllerImpl navigationController){
        return navigationController;
    }

    @Provides @Singleton
    public Bus providesBus(){
        return new Bus(ThreadEnforcer.MAIN);
    }

    @Provides
    public RemoteApi providesRemoteApi(){

        OkHttpClient client = new OkHttpClient();
/*        try {
            KeyStore keyStore = SSLUtils.getKeyStore(applicationContext);
            SSLContext sslContext = SSLContext.getInstance("SSL");
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            sslContext.init(null,trustManagerFactory.getTrustManagers(), new SecureRandom());
            client.setSslSocketFactory(sslContext.getSocketFactory());
        } catch (Exception e) {
            Log.d("QueueSleuth", "cannot create http client", e);
        }*/
        OkClient okClient = new OkClient(client);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.um.warszawa.pl/api")
                .setClient(okClient)
             //   .setRequestInterceptor(new ApiRequestInterceptor())
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        RemoteApi rAPI = restAdapter.create(RemoteApi.class);
        return rAPI;
    }

    @Provides @Mock
    public RemoteApi providesMockRemoteApi(){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.um.warszawa.pl/api")
               // .setRequestInterceptor(new ApiRequestInterceptor())
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        final MockRestAdapter adapter = MockRestAdapter.from(restAdapter);
        adapter.setDelay(200);

        return adapter.create(RemoteApi.class, new RemoteApi() {
            @Override
            public void getDepartment(String idDepartment, Callback<Department> callback) {
                Department mocked = new Department();
                //mocked.setBaseName("Mocked");
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
                mocked.setDate(sdf.format(new Date()));
                //mocked.setHour("");
                final Department.Result.Group group = new Department.Result.Group();
                group.setName("Robienie dobrze");
                group.setAvargeServiceTime("154");
                group.setCurrentNumber("AG 123");
                group.setQueueLength("1");
                group.setOpenStandsCount("0");
                group.setLetter("-69");
                mocked.setGroups(new ArrayList<Department.Result.Group>(){{add(group);}});
                callback.success(mocked, null);
            }
        });
    }

}
