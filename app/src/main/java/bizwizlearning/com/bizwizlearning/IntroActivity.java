package bizwizlearning.com.bizwizlearning;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import bizwizlearning.com.bizwizlearning.Adapter.IntroViewPagerAdapter;
import bizwizlearning.com.bizwizlearning.Model.IntroModel;

public class IntroActivity extends AppCompatActivity {

    private ViewPager screenPager;
    IntroViewPagerAdapter introViewPagerAdapter;
    Button next, getStarted;
    private FirebaseAuth mAuth;
    TabLayout tabIndicator;
    int position = 0;
    Animation btnAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //----------make activity on full screen---------------------------------
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //-----------------------------------------------------------------------
        setContentView(R.layout.activity_intro);

        getSupportActionBar().hide();

        next = findViewById(R.id.skipIntro);
        getStarted = findViewById(R.id.btn_getstarted);
        mAuth = FirebaseAuth.getInstance();
        tabIndicator = findViewById(R.id.tab_indicator);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_animation);

        if(mAuth.getCurrentUser() != null){
            //goto main activity
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        final List<IntroModel> mList = new ArrayList<>();
        mList.add(new IntroModel("Success","Want to make your Business a money generating machine?",R.drawable.success));
        mList.add(new IntroModel("To Grow daily 1%","Want to live a life you are proud of!",R.drawable.grow));
        mList.add(new IntroModel("Learn","Working 24 hours a day/ 7days a week, & still no profits!!",R.drawable.learn));

        screenPager = findViewById(R.id.screen_viewpager);
        introViewPagerAdapter = new IntroViewPagerAdapter(this, mList);
        screenPager.setAdapter(introViewPagerAdapter);

        tabIndicator.setupWithViewPager(screenPager);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(IntroActivity.this, LogIn.class));

                position = screenPager.getCurrentItem();
                if (position < mList.size())
                {
                    position++;
                    screenPager.setCurrentItem(position);
                }
                if (position ==  mList.size()-1)
                    loadLastScreen();
            }
        });

        tabIndicator.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == mList.size()-1)
                    loadLastScreen();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void loadLastScreen() {

        next.setVisibility(View.INVISIBLE);
        getStarted.setVisibility(View.VISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);

        getStarted.setAnimation(btnAnim);
        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IntroActivity.this, LogIn.class));
                finish();
            }
        });
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        setTitle("Explore");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.tw__composer_white)));
        getSupportActionBar().setElevation(0);
        return super.onCreateOptionsMenu(menu);
    }*/
}
