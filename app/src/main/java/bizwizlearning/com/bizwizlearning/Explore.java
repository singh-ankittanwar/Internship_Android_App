package bizwizlearning.com.bizwizlearning;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import bizwizlearning.com.bizwizlearning.Adapter.PagerAdapter;
import bizwizlearning.com.bizwizlearning.ExploreFragments.ExploreAudio;
import bizwizlearning.com.bizwizlearning.ExploreFragments.ExploreDocs;
import bizwizlearning.com.bizwizlearning.ExploreFragments.ExploreImage;
import bizwizlearning.com.bizwizlearning.ExploreFragments.ExploreVideo;

public class Explore extends AppCompatActivity implements ExploreAudio.OnFragmentInteractionListener, ExploreDocs.OnFragmentInteractionListener, ExploreVideo.OnFragmentInteractionListener, ExploreImage.OnFragmentInteractionListener {

    /*private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private ExploreAudio audioFragment;
    private ExploreVideo videoFragment;
    private ExploreImage imageFragment;
    private ExploreDocs docFragment;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "MyMy")
                .setContentTitle("Exploring resources?")
                .setSmallIcon(R.drawable.sc_logo)
                .setAutoCancel(true)
                .setContentText("Click here to check out our upcoming events too!")
                .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, Events.class), 0));

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(999, builder.build());

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("Image"));
        tabLayout.addTab(tabLayout.newTab().setText("Docs"));
        tabLayout.addTab(tabLayout.newTab().setText("Video"));
        tabLayout.addTab(tabLayout.newTab().setText("Audio"));
        tabLayout.addTab(tabLayout.newTab().setText("Event"));
        tabLayout.addTab(tabLayout.newTab().setText("course"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager)findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });






        /*mMainFrame = (FrameLayout) findViewById(R.id.explore_frame);
        mMainNav = (BottomNavigationView) findViewById(R.id.explore_nav);

        audioFragment = new ExploreAudio();
        videoFragment = new ExploreVideo();
        docFragment = new ExploreDocs();
        imageFragment = new ExploreImage();

        setFragment(imageFragment);

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {

                    case R.id.menu_item_image :
                        //mMainNav.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(imageFragment);
                        return true;

                    case R.id.menu_item_video :
                        //mMainNav.setItemBackgroundResource(R.color.colorAccent);
                        setFragment(videoFragment);
                        return true;

                    case R.id.menu_item_audio :
                        //mMainNav.setItemBackgroundResource(R.color.colorPrimaryDark);
                        setFragment(audioFragment);
                        return true;

                    case R.id.menu_item_docs :
                        //mMainNav.setItemBackgroundResource(R.color.colorPrimaryDark);
                        setFragment(docFragment);
                        return true;

                    default :
                        return false;

                }
            }
        });*/
    }

    /*private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.explore_frame, fragment);
        fragmentTransaction.commit();
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        setTitle("Explore");
        return super.onCreateOptionsMenu(menu);
    }

    public void onFragmentInteraction(Uri uri) {}
}
