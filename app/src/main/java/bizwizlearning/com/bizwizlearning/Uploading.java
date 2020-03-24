package bizwizlearning.com.bizwizlearning;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import bizwizlearning.com.bizwizlearning.UploadingFragments.UploadAudio;
import bizwizlearning.com.bizwizlearning.UploadingFragments.UploadDocs;
import bizwizlearning.com.bizwizlearning.UploadingFragments.UploadImage;
import bizwizlearning.com.bizwizlearning.UploadingFragments.UploadEvent;

public class Uploading extends AppCompatActivity {

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private UploadAudio audioFragment;
    private UploadEvent videoFragment;
    private UploadImage imageFragment;
    private UploadDocs docFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploading);

        mMainFrame = (FrameLayout) findViewById(R.id.upload_frame);
        mMainNav = (BottomNavigationView) findViewById(R.id.upload_nav);

        audioFragment = new UploadAudio();
        videoFragment = new UploadEvent();
        docFragment = new UploadDocs();
        imageFragment = new UploadImage();

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
        });
    }

    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.upload_frame, fragment);
        fragmentTransaction.commit();

    }
}
