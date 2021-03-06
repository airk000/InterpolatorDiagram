/*
 * This source is part of the InterpolatorDiagram repository.
 *
 * Copyright 2014 Kevin Liu (airk908@gmail.com)
 *
 * InterpolatorDiagram is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CommonClasses is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with InterpolatorDiagram.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.airk.interpolatordiagram.app.factory;

import android.support.v4.app.Fragment;

import com.airk.interpolatordiagram.app.fragment.AccDecDiagramFragment;
import com.airk.interpolatordiagram.app.fragment.AccDiagramFragment;
import com.airk.interpolatordiagram.app.fragment.AntiDiagramFragment;
import com.airk.interpolatordiagram.app.fragment.AntiOverDiagramFragment;
import com.airk.interpolatordiagram.app.fragment.BounceDiagramFragment;
import com.airk.interpolatordiagram.app.fragment.CycleDiagramFragment;
import com.airk.interpolatordiagram.app.fragment.DecDiagramFragment;
import com.airk.interpolatordiagram.app.fragment.LinearDiagramFragment;
import com.airk.interpolatordiagram.app.fragment.OvershotDiagramFragment;

/**
 * Created by kevin on 14-9-5.
 * <p/>
 * Fragment factory for navigation drawer
 */
public class FragmentFactory {
    private static FragmentFactory mFactory;

    private Fragment mAccDecFragment;
    private Fragment mAccFragment;
    private Fragment mAntiFragment;
    private Fragment mAntiOverFragment;
    private Fragment mBounceFragment;
    private Fragment mCycleFragment;
    private Fragment mDecFragment;
    private Fragment mLinearFragment;
    private Fragment mOverFragment;

    private FragmentFactory() {
    }

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
            case 5:
                if (mCycleFragment == null) {
                    mCycleFragment = new CycleDiagramFragment();
                }
                return mCycleFragment;
            case 6:
                if (mDecFragment == null) {
                    mDecFragment = new DecDiagramFragment();
                }
                return mDecFragment;
            case 7:
                if (mLinearFragment == null) {
                    mLinearFragment = new LinearDiagramFragment();
                }
                return mLinearFragment;
            case 8:
                if (mOverFragment == null) {
                    mOverFragment = new OvershotDiagramFragment();
                }
                return mOverFragment;
            default:
                break;
        }
        return null;
    }
}
