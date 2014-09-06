package com.airk.interpolatordiagram.app.factory;

import android.support.v4.app.Fragment;

import com.airk.interpolatordiagram.app.fragment.AccDecDiagramFragment;
import com.airk.interpolatordiagram.app.fragment.AccDiagramFragment;
import com.airk.interpolatordiagram.app.fragment.AntiDiagramFragment;
import com.airk.interpolatordiagram.app.fragment.AntiOverDiagramFragment;
import com.airk.interpolatordiagram.app.fragment.BounceDiagramFragment;

/**
 * Created by kevin on 14-9-5.
 */
public class FragmentFactory {
    private static FragmentFactory mFactory;

    private Fragment mAccDecFragment;
    private Fragment mAccFragment;
    private Fragment mAntiFragment;
    private Fragment mAntiOverFragment;
    private Fragment mBounceFragment;

    private FragmentFactory() {}

    public static FragmentFactory getInstance() {
        if (mFactory == null) {
            mFactory = new FragmentFactory();
        }
        return mFactory;
    }

    public Fragment getInterpolator(int type) {
        switch (type) {
            case 0:
                if (mAccDecFragment == null) {
                    mAccDecFragment = new AccDecDiagramFragment();
                }
                return mAccDecFragment;
            case 1:
                if (mAccFragment == null) {
                    mAccFragment = new AccDiagramFragment();
                }
                return mAccFragment;
            case 2:
                if (mAntiFragment == null) {
                    mAntiFragment = new AntiDiagramFragment();
                }
                return mAntiFragment;
            case 3:
                if (mAntiOverFragment == null) {
                    mAntiOverFragment = new AntiOverDiagramFragment();
                }
                return mAntiOverFragment;
            case 4:
                if (mBounceFragment == null) {
                    mBounceFragment = new BounceDiagramFragment();
                }
                return mBounceFragment;
            default:
                break;
        }
        return null;
    }
}
