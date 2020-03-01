package sk.mtmp.zadanie_1;

import java.util.ArrayList;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("projectile_motion_service")
    Single<ArrayList<Coordinates>> getCoordinates(@Query("angle") int angle, @Query("speed") int speed);
}
