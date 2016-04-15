/*
 * Copyright 2014 Toxic Bakery
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.klowerbase.test;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.PageTransformer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.klower.viewpageranimation.AccordionTransformer;
import com.klower.viewpageranimation.BackgroundToForegroundTransformer;
import com.klower.viewpageranimation.CubeInTransformer;
import com.klower.viewpageranimation.CubeOutTransformer;
import com.klower.viewpageranimation.DefaultTransformer;
import com.klower.viewpageranimation.DepthPageTransformer;
import com.klower.viewpageranimation.FlipHorizontalTransformer;
import com.klower.viewpageranimation.FlipVerticalTransformer;
import com.klower.viewpageranimation.ForegroundToBackgroundTransformer;
import com.klower.viewpageranimation.RotateDownTransformer;
import com.klower.viewpageranimation.RotateUpTransformer;
import com.klower.viewpageranimation.ScaleInOutTransformer;
import com.klower.viewpageranimation.StackTransformer;
import com.klower.viewpageranimation.TabletTransformer;
import com.klower.viewpageranimation.ZoomInTransformer;
import com.klower.viewpageranimation.ZoomOutSlideTransformer;
import com.klower.viewpageranimation.ZoomOutTranformer;

public class ViewpagerAnimationActivity extends FragmentActivity implements OnNavigationListener {

    private static final String KEY_SELECTED_PAGE = "KEY_SELECTED_PAGE";
    private static final String KEY_SELECTED_CLASS = "KEY_SELECTED_CLASS";
    private static final ArrayList<TransformerItem> TRANSFORM_CLASSES;

    static {
        TRANSFORM_CLASSES = new ArrayList<TransformerItem>();
        TRANSFORM_CLASSES.add(new TransformerItem(DefaultTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(AccordionTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(BackgroundToForegroundTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(CubeInTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(CubeOutTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(DepthPageTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(FlipHorizontalTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(FlipVerticalTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(ForegroundToBackgroundTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(RotateDownTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(RotateUpTransformer.class));
	    TRANSFORM_CLASSES.add(new TransformerItem(ScaleInOutTransformer.class));
	    TRANSFORM_CLASSES.add(new TransformerItem(StackTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(TabletTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(ZoomInTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(ZoomOutSlideTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(ZoomOutTranformer.class));
    }

    private int mSelectedItem;
    private ViewPager mPager;
    private PageAdapter mAdapter;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int selectedPage = 0;
        if (savedInstanceState != null) {
            mSelectedItem = savedInstanceState.getInt(KEY_SELECTED_CLASS);
            selectedPage = savedInstanceState.getInt(KEY_SELECTED_PAGE);
        }

        final ArrayAdapter<TransformerItem> actionBarAdapter = new ArrayAdapter<TransformerItem>(
                getApplicationContext(), android.R.layout.simple_expandable_list_item_1, android.R.id.text1, TRANSFORM_CLASSES);

        setContentView(R.layout.viewpageranimation);

        mAdapter = new PageAdapter(getSupportFragmentManager());

        mPager = (ViewPager) findViewById(R.id.container);
        mPager.setAdapter(mAdapter);
        mPager.setCurrentItem(selectedPage);

        final ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setListNavigationCallbacks(actionBarAdapter, this);
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
            //noinspection ResourceType
            actionBar.setDisplayOptions(actionBar.getDisplayOptions() ^ ActionBar.DISPLAY_SHOW_TITLE);

            actionBar.setSelectedNavigationItem(mSelectedItem);
        }

    }

    @Override
    public boolean onNavigationItemSelected(int position, long itemId) {
        mSelectedItem = position;
        try {
            mPager.setPageTransformer(true, TRANSFORM_CLASSES.get(position).clazz.newInstance());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_SELECTED_CLASS, mSelectedItem);
        outState.putInt(KEY_SELECTED_PAGE, mPager.getCurrentItem());
    }

    public static class PlaceholderFragment extends Fragment {

        private static final String EXTRA_POSITION = "EXTRA_POSITION";
        private static final int[] COLORS = new int[] { 0xFF33B5E5, 0xFFAA66CC, 0xFF99CC00, 0xFFFFBB33, 0xFFFF4444 };

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            final int position = getArguments().getInt(EXTRA_POSITION);
            final TextView textViewPosition = (TextView) inflater.inflate(R.layout.fragment_main, container, false);
            textViewPosition.setText(Integer.toString(position));
            textViewPosition.setBackgroundColor(COLORS[position - 1]);

            return textViewPosition;
        }

    }

    private static final class PageAdapter extends FragmentStatePagerAdapter {

        public PageAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            final Bundle bundle = new Bundle();
            bundle.putInt(PlaceholderFragment.EXTRA_POSITION, position + 1);

            final PlaceholderFragment fragment = new PlaceholderFragment();
            fragment.setArguments(bundle);

            return fragment;
        }

        @Override
        public int getCount() {
            return 5;
        }

    }

    private static final class TransformerItem {

        final String title;
        final Class<? extends PageTransformer> clazz;

        public TransformerItem(Class<? extends PageTransformer> clazz) {
            this.clazz = clazz;
            title = clazz.getSimpleName();
        }

        @Override
        public String toString() {
            return title;
        }

    }

}
