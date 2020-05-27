package com.wbtech.rockstars.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.wbtech.rockstars.Managers.PreferencesManager;
import com.wbtech.rockstars.Models.RockStarModel;
import com.wbtech.rockstars.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class RockStarsListAdapter extends RecyclerView.Adapter<RockStarsListAdapter.ViewHolder> {
    private List<RockStarModel> rockStarModels;
    private List<RockStarModel> filteredRockStarsModels;
    private Context context;
    private PreferencesManager preferencesManager;
    private boolean isFromBookMarks;


    public RockStarsListAdapter(List<RockStarModel> rockStarModels, Context context, boolean isFromBookMarks) {
        this.rockStarModels = rockStarModels;
        this.filteredRockStarsModels = rockStarModels;
        this.context = context;
        this.isFromBookMarks = isFromBookMarks;
        this.preferencesManager = PreferencesManager.getInstance(context.getSharedPreferences("prefs", MODE_PRIVATE));
    }

    @Override
    public RockStarsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rock_star_item, parent, false);


        return new RockStarsListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RockStarsListAdapter.ViewHolder holder, final int position) {
        final RockStarModel rockStarModel = filteredRockStarsModels.get(position);

        //set rockStars info
        holder.tvName.setText(rockStarModel.toString());
        holder.tvStatus.setText(rockStarModel.getStatus());

        Set<String> bookMarks = preferencesManager.getBookMark();

        //check if is on bookMarks or not
        if (isFromBookMarks) {
            //change icon for BookMarks fragment
            holder.ivActionIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_delete));
        } else {

            if (bookMarks.contains(rockStarModel.toString())) {
                holder.ivActionIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_full_star));
            } else {
                holder.ivActionIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star));
            }

        }

        //rounded corner options to imageView
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(8));
        Glide
                .with(context)
                .load(rockStarModel.getPictureUrl())
                .centerCrop()
                .apply(requestOptions)
                .into(holder.ivPicture);


        holder.ivActionIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (preferencesManager.getBookMark().contains(rockStarModel.toString())) {
                    preferencesManager.removeRockStarFromBookMark(rockStarModel.toString());
                    if (isFromBookMarks) {
                        rockStarModels.remove(position);
                    }

                } else {
                    preferencesManager.saveRockStarToBookMark(rockStarModel.toString());
                }

                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {

        return filteredRockStarsModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName, tvStatus;
        private ImageView ivPicture, ivActionIcon;

        public ViewHolder(View v) {
            super(v);
            this.tvName = v.findViewById(R.id.tv_name);
            this.tvStatus = v.findViewById(R.id.tv_status);
            this.ivPicture = v.findViewById(R.id.iv_picture);
            this.ivActionIcon = v.findViewById(R.id.iv_book_mark);

        }
    }


    //Filter for the rock stars list
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredRockStarsModels = rockStarModels;
                } else {
                    List<RockStarModel> filteredList = new ArrayList<>();
                    for (RockStarModel row : rockStarModels) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getFirstName().toLowerCase().contains(charString.toLowerCase()) || row.getLastName().toLowerCase().contains(charString.toLowerCase()) || row.getStatus().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    filteredRockStarsModels = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredRockStarsModels;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredRockStarsModels = (ArrayList<RockStarModel>) filterResults.values;
                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }

}
