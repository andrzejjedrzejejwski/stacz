package seven.more.queuesleuth.core;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface RemoteApi {

    @GET("/action/wsstore_get")
    void getDepartment(@Query("id") String idDepartment, Callback<Department> callback);
}
