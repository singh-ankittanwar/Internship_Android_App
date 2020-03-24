package bizwizlearning.com.bizwizlearning.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import bizwizlearning.com.bizwizlearning.ExploreFragments.ExploreAudio;
import bizwizlearning.com.bizwizlearning.ExploreFragments.ExploreDocs;
import bizwizlearning.com.bizwizlearning.ExploreFragments.ExploreEvent;
import bizwizlearning.com.bizwizlearning.ExploreFragments.ExploreImage;
import bizwizlearning.com.bizwizlearning.ExploreFragments.ExplorePrograms;
import bizwizlearning.com.bizwizlearning.ExploreFragments.ExploreVideo;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNoOfTabs;

    public PagerAdapter(FragmentManager fm, int NumberOfTabs)
    {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }

    @Override
    public Fragment getItem(int i) {

        switch (i)
        {
            case 0:
                ExploreImage ei = new ExploreImage();
                return ei;
            case 1:
                ExploreDocs ed = new ExploreDocs();
                return ed;
            case 2:
                ExploreVideo ev = new ExploreVideo();
                return ev;
            case 3:
                ExploreAudio ea = new ExploreAudio();
                return ea;
            case 4:
                ExploreEvent ee = new ExploreEvent();
                return ee;
            case 5:
                ExplorePrograms ep = new ExplorePrograms();
                return ep;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
