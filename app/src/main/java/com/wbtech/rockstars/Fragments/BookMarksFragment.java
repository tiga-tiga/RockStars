package com.wbtech.rockstars.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wbtech.rockstars.Commons.BaseFragment;
import com.wbtech.rockstars.Managers.AppManager;
import com.wbtech.rockstars.R;
import com.wbtech.rockstars.Utils.RockStarsListAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookMarksFragment extends BaseFragment {

    public BookMarksFragment() {
        // Required empty public constructor
    }


    private RecyclerView mRecyclerView;
    private RockStarsListAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_book_marks, container, false);


        mRecyclerView = view.findViewById(R.id.recycler_rock_stars);
        setRockStarsList();

        setToolBarTitle(view, getResources().getString(R.string.bookmarks));
        return view;
    }


    private void setRockStarsList() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new RockStarsListAdapter(AppManager.getInstance().getBookMarksList(getActivity()), getContext(), true);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
