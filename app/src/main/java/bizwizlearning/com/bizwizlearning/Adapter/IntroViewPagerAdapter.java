package bizwizlearning.com.bizwizlearning.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import bizwizlearning.com.bizwizlearning.Model.IntroModel;
import bizwizlearning.com.bizwizlearning.R;

public class IntroViewPagerAdapter extends PagerAdapter {

    Context mContext;
    List<IntroModel> mListScreem;

    public IntroViewPagerAdapter(Context mContext, List<IntroModel> mListScreem) {
        this.mContext = mContext;
        this.mListScreem = mListScreem;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen = layoutInflater.inflate(R.layout.intro_item, null);

        ImageView imgSlide = layoutScreen.findViewById(R.id.intro_img);
        TextView title = layoutScreen.findViewById(R.id.intro_title);
        TextView description = layoutScreen.findViewById(R.id.intro_description);

        title.setText(mListScreem.get(position).getTitle());
        description.setText(mListScreem.get(position).getDescription());
        imgSlide.setImageResource(mListScreem.get(position).getScreenImg());

        container.addView(layoutScreen);

        return layoutScreen;
    }

    @Override
    public int getCount() {
        return mListScreem.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((View)object);
    }
}
