package yunaann.game;

import android.content.Intent;
import android.graphics.Point;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    //labels
    private TextView startLabel;
    private TextView scoreLabel;
    private TextView lifeLabel;
    //score and life
    private int score = 0;
    private int life = 5;
    //sound
    private SoundPool soundPool;
    private int catchSound;
    private int hitSound;
    private AudioAttributes audioAttributes;

    private ImageView ball;
    private ImageView ball2;
    private ImageView ball3;
    private ImageView ball4;
    private ImageView ball5;
    private ImageView enemy;
    private ImageView enemy2;
    private ImageView hero;
    //hero pos (Y)
    private int heroY;
    //position of ball,ball2, ball3, ball4, ball5 i enemy, enemy2 (Y and X)
    private int ballY;
    private int ballX;
    private int ball2Y;
    private int ball2X;
    private int ball3Y;
    private int ball3X;
    private int ball4Y;
    private int ball4X;
    private int ball5Y;
    private int ball5X;
    private int enemyY;
    private int enemyX;
    private int enemy2Y;
    private int enemy2X;
    //center
    private int ballCenterX;
    private int ballCenterY;
    private int ball2CenterX;
    private int ball2CenterY;
    private int ball3CenterX;
    private int ball3CenterY;
    private int ball4CenterX;
    private int ball4CenterY;
    private int ball5CenterX;
    private int ball5CenterY;
    private int enemyCenterX;
    private int enemyCenterY;
    private int enemy2CenterX;
    private int enemy2CenterY;
    //frame and hero size
    private int frameHeight;
    private int heroSize;
    //screen size
    private int screenWidth;
    private int screenHeight;
    //handler and timer
    private Handler handler = new Handler();
    private Timer timer = new Timer();
    //status
    private boolean actionFlag = false;
    private boolean startFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //view
        lifeLabel = (TextView) findViewById(R.id.lifeLabel);
        startLabel = (TextView) findViewById(R.id.startLabel);
        scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        ball = (ImageView) findViewById(R.id.ball);
        ball2 = (ImageView) findViewById(R.id.ball2);
        ball3 = (ImageView) findViewById(R.id.ball3);
        ball4 = (ImageView) findViewById(R.id.ball4);
        ball5 = (ImageView) findViewById(R.id.ball5);
        enemy = (ImageView) findViewById(R.id.enemy);
        enemy2 = (ImageView) findViewById(R.id.enemy2);
        hero = (ImageView) findViewById(R.id.hero);

        //screen
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenHeight = size.y;
        screenWidth = size.x;

        ball.setX(-80);
        ball.setY(-80);
        ball2.setX(-80);
        ball2.setY(-80);
        ball3.setX(-80);
        ball3.setY(-80);
        ball4.setX(-80);
        ball4.setY(-80);
        ball5.setX(-80);
        ball5.setY(-80);
        enemy.setX(-80);
        enemy.setY(-80);
        enemy2.setX(-80);
        enemy2.setY(-80);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(10)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            soundPool = new SoundPool(10,AudioManager.STREAM_MUSIC,0);
        }
        //catchSound = soundPool.load(this, R.raw.catchsound, 0);
        //hitSound = soundPool.load(this, R.raw.hitsound, 0);

        scoreLabel.setText("Score : 0");
        lifeLabel.setText("Life : 5");
    }

    public void changePosition() {

        checkHit();

        //ball
        ballX -=12;
        if(ballX < 0)
        {
            ballX = screenWidth + 20;
            ballY = (int)Math.floor(Math.random() * (frameHeight - ball.getHeight()));
        }
        ball.setX(ballX);
        ball.setY(ballY);

        //ball2
        ball2X -=20;
        if(ball2X < 0)
        {
            ball2X = screenWidth + 500;
            ball2Y = (int)Math.floor(Math.random() * (frameHeight - ball2.getHeight()));
        }
        ball2.setX(ball2X);
        ball2.setY(ball2Y);

        //ball3
        ball3X -=25;
        if(ball3X < 0)
        {
            ball3X = screenWidth + 5000;
            ball3Y = (int)Math.floor(Math.random() * (frameHeight - ball3.getHeight()));
        }
        ball3.setX(ball3X);
        ball3.setY(ball3Y);

        //ball4
        ball4X -=27;
        if(ball4X < 0)
        {
            ball4X = screenWidth + 10000;
            ball4Y = (int)Math.floor(Math.random() * (frameHeight - ball4.getHeight()));
        }
        ball4.setX(ball4X);
        ball4.setY(ball4Y);

        //ball5
        ball5X -=23;
        if(ball5X < 0)
        {
            ball5X = screenWidth + 20000;
            ball5Y = (int)Math.floor(Math.random() * (frameHeight - ball5.getHeight()));
        }
        ball5.setX(ball5X);
        ball5.setY(ball5Y);

        //enemy
        enemyX -=32;
        if(enemyX < 0)
        {
            enemyX = screenWidth + 15000;
            enemyY = heroY;
        }
        enemy.setX(enemyX);
        enemy.setY(enemyY);

        //enemy2
        enemy2X -=20;
        if(enemy2X < 0)
        {
            enemy2X = screenWidth + 10;
            enemy2Y = (int)Math.floor(Math.random() * (frameHeight - enemy2.getHeight()));
        }
        enemy2.setX(enemy2X);
        enemy2.setY(enemy2Y);

        //hero
        if (actionFlag == true) {
            heroY -= 20;
        } else {
            heroY += 20;
        }

        //hero position
        if (heroY < 0) heroY = 0;
        if (heroY > frameHeight - heroSize) heroY = frameHeight - heroSize;
        hero.setY(heroY);

        scoreLabel.setText("Score : " + score);
        lifeLabel.setText("Life : " + life);

    }

    public void checkHit()
    {
     //ball
        ballCenterX = ballX + ball.getWidth()/2;
        ballCenterY = ballY + ball.getHeight()/2;

     if(0 <= ballCenterX && ballCenterX <= heroSize && heroY <= ballCenterY && ballCenterY <= heroY + heroSize)
     {
        // soundPool.play(catchSound,1,1,0,0,1);
        score += 10;
        ballX = -10;
     }

        //ball2
        ball2CenterX = ball2X + ball2.getWidth()/2;
        ball2CenterY = ball2Y + ball2.getHeight()/2;

        if(0 <= ball2CenterX && ball2CenterX <= heroSize && heroY <= ball2CenterY && ball2CenterY <= heroY + heroSize)
        {
           // soundPool.play(catchSound,1,1,0,0,1);
            score += 30;
            ball2X = -10;
        }

        //ball3
        ball3CenterX = ball3X + ball3.getWidth()/2;
        ball3CenterY = ball3Y + ball3.getHeight()/2;

        if(0 <= ball3CenterX && ball3CenterX <= heroSize && heroY <= ball3CenterY && ball3CenterY <= heroY + heroSize)
        {
           // soundPool.play(catchSound,1,1,0,0,1);
            score += 50;
            ball3X = -10;
        }

        //ball4
        ball4CenterX = ball4X + ball4.getWidth()/2;
        ball4CenterY = ball4Y + ball4.getHeight()/2;

        if(0 <= ball4CenterX && ball4CenterX <= heroSize && heroY <= ball4CenterY && ball4CenterY <= heroY + heroSize)
        {
           // soundPool.play(catchSound,1,1,0,0,1);
            score += 100;
            ball2X = -10;
        }

        //ball5
        ball5CenterX = ball5X + ball5.getWidth()/2;
        ball5CenterY = ball5Y + ball5.getHeight()/2;

        if(0 <= ball5CenterX && ball5CenterX <= heroSize && heroY <= ball5CenterY && ball5CenterY <= heroY + heroSize)
        {
          //  soundPool.play(catchSound,1,1,0,0,1);
            life += 1;
            ball5X = -10;
        }

        //enemy
        enemyCenterX = enemyX + enemy.getWidth()/2;
        enemyCenterY = enemyY + enemy.getHeight()/2;

        if(0 <= enemyCenterX && enemyCenterX <= heroSize && heroY <= enemyCenterY && enemyCenterY <= heroY + heroSize)
        {
           // soundPool.play(hitSound,1,1,0,0,1);
            GameOver();
        }

        //enemy2
        enemy2CenterX = enemy2X + enemy2.getWidth()/2;
        enemy2CenterY = enemy2Y + enemy2.getHeight()/2;

        if(0 <= enemy2CenterX && enemy2CenterX <= heroSize && heroY <= enemy2CenterY && enemy2CenterY <= heroY + heroSize)
        {
          //  soundPool.play(hitSound,1,1,0,0,1);
           life -= 1;
            enemy2X = -10;
           if(life<1)
           {
               GameOver();
           }
        }

    }

    private void GameOver()
    {
        //stop timer
        timer.cancel();
        timer = null;

        Intent intent =  new Intent(getApplicationContext(), Result.class);
        intent.putExtra("SCORE", score);
        startActivity(intent);
    }


    public boolean onTouchEvent(MotionEvent motionEvent) {

        if (startFlag == false) {
            startFlag = true;

            FrameLayout frame = (FrameLayout) findViewById(R.id.frame);
            frameHeight = frame.getHeight();
            heroY = (int) hero.getY();
            heroSize = hero.getHeight();

            startLabel.setVisibility(View.GONE);
            //set timer
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changePosition();
                        }
                    });
                }
            }, 0, 20);

        } else {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                actionFlag = true;
            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                actionFlag = false;
            }
        }
        return true;
    }

}

