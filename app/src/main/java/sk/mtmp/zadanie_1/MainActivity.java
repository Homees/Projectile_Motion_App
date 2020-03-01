package sk.mtmp.zadanie_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView angleTextView;
    int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SeekBar angleSeekBar = findViewById(R.id.angleSeekBar);
        angleSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        angleTextView = findViewById(R.id.progressValue);

        progress = angleSeekBar.getProgress();
        angleTextView.setText(new StringBuilder().append("Uhol: ").append(progress));

        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Switch onlineSwitch = findViewById(R.id.onlineSwitch);
                EditText speedEditText = findViewById(R.id.speedEditText);

                Boolean switchState = onlineSwitch.isChecked();
                int speed = Integer.parseInt(speedEditText.getText().toString());

                Intent intent = new Intent(getApplicationContext(), ProjectileActivity.class);
                intent.putExtra("ANGLE", progress);
                intent.putExtra("SPEED", speed);
                intent.putExtra("SWITCH", switchState);

                startActivity(intent);
            }
        });
    }

    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            progress = i;
            angleTextView.setText(new StringBuilder().append("Uhol: ").append(i));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
}
