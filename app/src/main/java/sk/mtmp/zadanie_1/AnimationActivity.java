package sk.mtmp.zadanie_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class AnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        ArrayList<Suradnice> coordinates = (ArrayList<Suradnice>) getIntent().getSerializableExtra("COORDINATES");
        ConstraintLayout layout = findViewById(R.id.layout);
        System.out.println("center of screen " + layout.getBottom());

        Path path = new Path();
        for (Suradnice coordinate : coordinates) {
            path.lineTo((float)(coordinate.getX() * 10f), (float)(coordinate.getY() * -10f));
        }
        ImageView starImage = findViewById(R.id.starImage);
        ObjectAnimator animator = ObjectAnimator.ofFloat(starImage, View.TRANSLATION_X, View.TRANSLATION_Y, path);
        animator.setDuration((long)(coordinates.get(coordinates.size() - 1).getTime() * 1000));
        animator.start();
    }
}
