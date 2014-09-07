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
import android.view.animation.CycleInterpolator;
import android.view.animation.Interpolator;
import android.widget.EditText;

import com.airk.interpolatordiagram.app.R;
import com.airk.interpolatordiagram.app.widget.DiagramView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Kevin on 2014/9/7.
 *
 * Cycle
 */
public class CycleDiagramFragment extends Fragment implements TextWatcher {
    @InjectView(R.id.cycles)
    EditText mCycles;
    @InjectView(R.id.diagram)
    DiagramView mDiagram;
    private Interpolator mInterpolator;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cycle_fragment, container, false);
        ButterKnife.inject(this, v);
        mInterpolator = new CycleInterpolator(1f);
        mCycles.addTextChangedListener(this);
        mDiagram.setInterpolator(mInterpolator);
        return v;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!TextUtils.isEmpty(s)) {
            float cycles;
            cycles = Float.valueOf(s.toString());
            mInterpolator = null;
            mInterpolator = new CycleInterpolator(cycles);
            mDiagram.setInterpolator(mInterpolator);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}
