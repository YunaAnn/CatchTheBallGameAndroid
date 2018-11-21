package yunaann.game;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

public class Result extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        TextView highScoreLabel = (TextView) findViewById(R.id.highScore);

        int score = getIntent().getIntExtra("SCORE", 0);
        scoreLabel.setText(score + "");

        SharedPreferences settings = getSharedPreferences("HIGH_SCORE", Context.MODE_PRIVATE);
        int highScore = settings.getInt("HIGH_SCORE", 0);

        if(score > highScore)
        {
            highScoreLabel.setText("Highscore : " + score);
            //update highscore
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("HIGH_SCORE", score);

        }
        else
        {
            highScoreLabel.setText("Highscore : " + highScore);
        }

    }

    public void tryAgain(View view)
    {
        startActivity(new Intent(getApplicationContext(), Start.class));
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent keyEvent)
    {
        if(keyEvent.getAction() == KeyEvent.ACTION_DOWN)
        {
            switch(keyEvent.getKeyCode())
            {
                case KeyEvent.KEYCODE_BACK:
                    return true;
            }
        }
     return super.dispatchKeyEvent(keyEvent);
    }
}



