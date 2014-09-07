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
 *
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
