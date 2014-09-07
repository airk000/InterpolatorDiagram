package com.airk.interpolatordiagram.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;

import com.airk.interpolatordiagram.app.R;
import com.airk.interpolatordiagram.app.widget.DiagramView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kevin on 14-9-5.
 *
 * Bounce
 */
public class BounceDiagramFragment extends Fragment {
    @InjectView(R.id.diagram)
    DiagramView mDiagram;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bounce_fragment, container, false);
        ButterKnife.inject(this, v);
        mDiagram.setInterpolator(new BounceInterpolator());
        return v;
    }
}
