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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.Interpolator;
import android.widget.EditText;

import com.airk.interpolatordiagram.app.R;
import com.airk.interpolatordiagram.app.widget.DiagramView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kevin on 14-9-5.
 * <p/>
 * AntiOver
 */
public class AntiOverDiagramFragment extends BaseFragment {
    @InjectView(R.id.tension)
    EditText mTension;
    @InjectView(R.id.extra)
    EditText mExtra;
    @InjectView(R.id.diagram)
    DiagramView mDiagram;
    private Interpolator mInterpolator;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.anti_over_fragment, container, false);
        ButterKnife.inject(this, v);
        mTension.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changeFactors();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mExtra.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changeFactors();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mInterpolator = new AnticipateOvershootInterpolator();
        mDiagram.setInterpolator(mInterpolator);
        return v;
    }

    private void changeFactors() {
        float tension = 2.0f;
        float extra = 1.5f;

        if (!TextUtils.isEmpty(mTension.getText())) {
            tension = Float.valueOf(mTension.getText().toString());
        }
        if (!TextUtils.isEmpty(mExtra.getText())) {
            extra = Float.valueOf(mExtra.getText().toString());
        }
        mInterpolator = null;
        mInterpolator = new AnticipateOvershootInterpolator(tension, extra);
        mDiagram.setInterpolator(mInterpolator);
    }

    @Override
    public DiagramView getDiagramView() {
        return mDiagram;
    }
}
