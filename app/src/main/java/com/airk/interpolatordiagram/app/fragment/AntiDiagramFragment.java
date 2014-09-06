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
import android.widget.EditText;

import com.airk.interpolatordiagram.app.R;
import com.airk.interpolatordiagram.app.widget.AntiDiagramView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kevin on 14-9-5.
 *
 * Acc
 */
public class AntiDiagramFragment extends Fragment implements TextWatcher {
    @InjectView(R.id.tension)
    EditText mTension;
    @InjectView(R.id.diagram)
    AntiDiagramView mDiagram;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.anti_fragment, container, false);
        ButterKnife.inject(this, v);
        mTension.addTextChangedListener(this);
        return v;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        float tension;
        if (TextUtils.isEmpty(s)) {
            tension = 1f;
        } else {
            tension = Float.valueOf(s.toString());
        }
        mDiagram.setTension(tension);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
