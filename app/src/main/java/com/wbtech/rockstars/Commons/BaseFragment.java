package com.wbtech.rockstars.Commons;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.wbtech.rockstars.R;

public class BaseFragment extends Fragment {

    /*
    Helper Activity for shared user interface
     */

    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    public void setToolBarTitle(View view, String title) {
        TextView tvTitle = view.findViewById(R.id.tv_toolbar_title);
        tvTitle.setText(title);
    }

    public void setProfileToolBar(View view, String title) {
        Toolbar toolbar = view.findViewById(R.id.custom_toolbar);
        TextView tvTitle = view.findViewById(R.id.tv_toolbar_title);
        tvTitle.setText(title);
        toolbar.inflateMenu(R.menu.save_menu);
    }

    public void showAlertDialog(Context context, String message, BaseActivity.AlertDialogListener alertDialogListener){
        getBaseActivity().showAlertDialog(context, message, alertDialogListener);
    }
}
