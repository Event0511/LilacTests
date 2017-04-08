package com.example.lilactests.view.layoutfragment;

import android.content.Context;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.widget.Toast;

import com.example.lilactests.R;
import com.example.lilactests.app.LilacTestsApp;
import com.example.lilactests.utils.PrefUtils;



/**
 *  Created by Eventory on 2017/2/8 0008.
 */
public class SettingFragment extends PreferenceFragment {
    private OnTransition mOnTransition;
    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        optionCheckBox("saving_net_mode");
        optionCheckBox("night_mode");
    }

    @Override
    public void onAttach(Context context) {
        mContext = context;
        mOnTransition = (OnTransition) context;
        super.onAttach(context);
    }

    /**
     * 根据传入字符串决定配置switch
     *
     * @param item 相应的ID
     */
    private void optionCheckBox(String item) {
        switch (item) {
            case "saving_net_mode":
                final CheckBoxPreference savingNetModeCheckBox = (CheckBoxPreference) getPreferenceManager()
                    .findPreference("saving_net_mode");

                savingNetModeCheckBox.setChecked(PrefUtils.isSavingNetMode());
                savingNetModeCheckBox.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {

                        boolean checked = Boolean.valueOf(newValue.toString());
                        PrefUtils.setSaveNetMode(checked);
                        Log.i("Transaction:","Pref " + preference.getKey() + " changed to " + newValue.toString());
                        Toast.makeText(LilacTestsApp.getContext(), "省流量模式设置为"+PrefUtils.isSavingNetMode(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                break;
            case "night_mode":
                final CheckBoxPreference nightModeCheckBox = (CheckBoxPreference) getPreferenceManager()
                    .findPreference("night_mode");

                nightModeCheckBox.setChecked(PrefUtils.isNightMode());
                nightModeCheckBox.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {

                        boolean checked = Boolean.valueOf(newValue.toString());
                        PrefUtils.setNightMode(checked);
                        Log.i("Transaction:","Pref " + preference.getKey() + " changed to " + newValue.toString());
                        Toast.makeText(LilacTestsApp.getContext(), "夜间模式设置为"+PrefUtils.isNightMode(), Toast.LENGTH_SHORT).show();
                        if (PrefUtils.isNightMode()) {
                            LilacTestsApp.setAppTheme(R.style.NightModeAppTheme);
                        } else {
                            LilacTestsApp.setAppTheme(R.style.DefaultAppTheme);
                        }
                        mOnTransition.beginTransition();
                        return true;
                    }
                });
                break;
            default:
        }
    }

    public interface OnTransition {
        void beginTransition();
    }

}
