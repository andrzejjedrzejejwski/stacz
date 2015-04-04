package seven.more.queuesleuth.core;

import dagger.ObjectGraph;

public class SharedObjectGraph {

    private static ObjectGraph objectGraph;

    public static void create(App app){
        objectGraph = ObjectGraph.create(new AppModule(app.getApplicationContext()));
        objectGraph.inject(app);
    }

    public static void inject(Object object) {
        objectGraph.inject(object);
    }
}
