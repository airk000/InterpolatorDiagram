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

package com.airk.interpolatordiagram.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import com.airk.interpolatordiagram.app.R;
import com.airk.interpolatordiagram.app.widget.DiagramView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kevin on 14-9-5.
 * <p/>
 * AccDec
 */
public class LinearDiagramFragment extends Fragment {
    @InjectView(R.id.diagram)
    DiagramView mDiagramView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.linear_fragment, container, false);
        ButterKnife.inject(this, v);
        mDiagramView.setInterpolator(new LinearInterpolator());
        return v;
    }
}
