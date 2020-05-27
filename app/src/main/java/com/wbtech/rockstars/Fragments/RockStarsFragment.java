package com.wbtech.rockstars.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.wbtech.rockstars.Activities.SplashScreenActivity;
import com.wbtech.rockstars.Commons.BaseActivity;
import com.wbtech.rockstars.Commons.BaseFragment;
import com.wbtech.rockstars.Managers.AppManager;
import com.wbtech.rockstars.R;
import com.wbtech.rockstars.Utils.RockStarsListAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class RockStarsFragment extends BaseFragment {

    public RockStarsFragment() {
        // Required empty public constructor
    }


    private RecyclerView mRecyclerView;
    private RockStarsListAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SearchView mSearchView;
    private AppManager mAppManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rock_stars, container, false);


        mAppManager = AppManager.getInstance();
        mSwipeRefreshLayout = view.findViewById(R.id.refresh_layout);
        mSearchView = view.findViewById(R.id.search_rock_star);
        mRecyclerView = view.findViewById(R.id.recycler_rock_stars);
        setRockStarsList();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshList();
            }
        });
        mSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchView.setIconified(false);
            }
        });


        setToolBarTitle(view, getResources().getString(R.string.rockstars));
        return view;
    }


    private void setRockStarsList() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new RockStarsListAdapter(mAppManager.getRockStarModels(), getContext(), false);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mSearchView.setQueryHint("Name, Status");
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mSearchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mAdapter.getFilter().filter(s);
                return false;
            }
        });

    }

    private void refreshList() {
        mAppManager.getRockStars(getContext(), new AppManager.LoadRockStarsListener() {
            @Override
            public void onComplete() {
                mSwipeRefreshLayout.setRefreshing(false);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure() {
                mSwipeRefreshLayout.setRefreshing(false);
                showAlertDialog(getBaseActivity(), "Something went wrong!", new BaseActivity.AlertDialogListener() {
                    @Override
                    public void onConfirm() {
                    }
                });
            }
        });
    }


}
