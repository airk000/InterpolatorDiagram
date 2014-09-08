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

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.airk.interpolatordiagram.app.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Kevin on 2014/9/7.
 * <p/>
 * About Dialog
 */
public class AboutFragmentDialog extends DialogFragment {
    @InjectView(R.id.app_version)
    TextView mVersion;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton(android.R.string.ok, null);
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.about_fragment, null);
        ButterKnife.inject(this, v);
        try {
            String version = getActivity().getPackageManager().getPackageInfo(
                    getActivity().getPackageName(), 0).versionName;
            mVersion.setText("v" + version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        builder.setView(v);
        return builder.create();
    }
}
