package sk.mtmp.zadanie_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProjectileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projectile);

        final TextView angleValue = findViewById(R.id.finalXValue);
        final TextView heightValue = findViewById(R.id.maxHeight);
        final TextView speedValue = findViewById(R.id.serverUsageValue);
        final TextView timeValue = findViewById(R.id.timeValue);

        final boolean switchValue = getIntent().getExtras().getBoolean("SWITCH");
        int speed = getIntent().getExtras().getInt("SPEED");
        int angle = getIntent().getExtras().getInt("ANGLE");

        final ArrayList<Suradnice> suradnice = new ArrayList<>();

        if (switchValue) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl("http://10.0.2.2:8080/mtmp_server/calculate_projectile_motion/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            // create an instance of the ApiService
            ApiService apiService = retrofit.create(ApiService.class);
            // make a request by calling the corresponding method
            Single<ArrayList<Coordinates>> coordinates = apiService.getCoordinates(angle, speed);
            coordinates.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<ArrayList<Coordinates>>() {

                CompositeDisposable compositeDisposable = new CompositeDisposable();

                @Override
                public void onSubscribe(Disposable d) {
                    compositeDisposable.add(d);
                }

                @Override
                public void onSuccess(ArrayList<Coordinates> coordinates) {
                    double maxHeight = 0;

                    for (int i = 0; i < coordinates.size(); i++) {
                        if (i == coordinates.size() - 1) {
                            maxHeight = coordinates.get(i).getY();
                            continue;
                        }
                        Suradnice sur = new Suradnice(coordinates.get(i).getX(), coordinates.get(i).getY(), coordinates.get(i).getTime());
                        suradnice.add(sur);
                    }

                    StringBuilder sb;
                    sb = new StringBuilder().append("Doletená vzdialenosť: ").append(suradnice.get(suradnice.size() - 2).getX()).append(" m");
                    angleValue.setText(sb.toString());
                    sb = new StringBuilder().append("Najvyšší dosiahnutý bod: ").append(maxHeight).append(" m");
                    heightValue.setText(sb.toString());
                    sb = new StringBuilder().append("Použitý server: ").append(true);
                    speedValue.setText(sb.toString());
                    sb = new StringBuilder().append("Čas letu: ").append(suradnice.get(suradnice.size() - 2).getTime()).append(" sekúnd");
                    timeValue.setText(sb.toString());
                }

                @Override
                public void onError(Throwable e) {
                    if (!compositeDisposable.isDisposed()) {
                        compositeDisposable.dispose();
                    }
                }
            });
        }
        else {
            double deltaTime = 0.01;
            double x = 0.0;
            double y = 0.0;
            double ay = -9.8;
            double ax = 0;
            double vx = speed * Math.cos(angle * (Math.PI / 180.0));
            double vy = speed * Math.sin(angle * (Math.PI / 180.0));
            double maxHeight = 0;
            double time = 0.0;

            suradnice.add(new Suradnice(Math.round(x * 1000.0) / 1000.0, Math.round(y * 1000.0) / 1000.0, Math.round(time * 1000.0) / 1000.0));

            while (y >= 0) {
                time += deltaTime;
                x += vx * deltaTime;
                y += vy * deltaTime;

                vx += ax * deltaTime;
                vy += ay * deltaTime;

                if (y > maxHeight) {
                    maxHeight = Math.round(y * 1000.0) / 1000.0;
                }

                suradnice.add(new Suradnice(Math.round(x * 1000.0) / 1000.0, Math.round(y * 1000.0) / 1000.0, Math.round(time * 1000.0) / 1000.0));
            }

            StringBuilder sb;
            sb = new StringBuilder().append("Doletená vzdialenosť: ").append(suradnice.get(suradnice.size() - 1).getX()).append(" m");
            angleValue.setText(sb.toString());
            sb = new StringBuilder().append("Najvyšší dosiahnutý bod: ").append(maxHeight).append(" m");
            heightValue.setText(sb.toString());
            sb = new StringBuilder().append("Použitý server: ").append(false);
            speedValue.setText(sb.toString());
            sb = new StringBuilder().append("Čas letu: ").append(suradnice.get(suradnice.size() - 1).getTime()).append(" sekúnd");
            timeValue.setText(sb.toString());
        }

        Button listViewButton = findViewById(R.id.listViewButton);
        listViewButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent listViewIntent = new Intent(getApplicationContext(), ListActivity.class);
                listViewIntent.putExtra("COORDINATES", suradnice);

                startActivity(listViewIntent);
            }
        });

        Button graphViewButton = findViewById(R.id.graphViewButton);
        graphViewButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent graphViewIntent = new Intent(getApplicationContext(), GraphActivity.class);
                graphViewIntent.putExtra("COORDINATES", suradnice);

                startActivity(graphViewIntent);
            }
        });

        Button animationButton = findViewById(R.id.animationButton);
        animationButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent animationIntent = new Intent(getApplicationContext(), AnimationActivity.class);
                animationIntent.putExtra("COORDINATES", suradnice);

                startActivity(animationIntent);
            }
        });
    }
}
