package com.hostpital.hospitaladmin.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.hostpital.hospitaladmin.Models.UserRequests;
import com.hostpital.hospitaladmin.Activities.ShowRequestsDetailActivity;
import com.hostpital.hospitaladmin.R;

import java.util.List;

/**
 * Created by Arbab on 8/8/2019.
 */

public class UserRequestAdapter extends RecyclerView.Adapter<UserRequestAdapter.AdminRequestViewHolder> {

    RequestOptions options;
    private List<UserRequests> mUserRequestData;
    private Context context;
    private LayoutInflater inflater;
    private Bundle mBundle;

    public UserRequestAdapter(Context context, List<UserRequests> feedsList) {

        this.context = context;
        this.mUserRequestData = feedsList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


//        options = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);

    }


    @NonNull
    @Override
    public AdminRequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.user_request_singleitem_recyclerview, parent, false);
        final AdminRequestViewHolder viewHolder = new AdminRequestViewHolder(view);

        viewHolder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ShowRequestsDetailActivity.class);
                // sending data process

                //Order Information
                i.putExtra("medical_name", mUserRequestData.get(viewHolder.getAdapterPosition()).getMedical_name());
                i.putExtra("medical_name_specialties", mUserRequestData.get(viewHolder.getAdapterPosition()).getMedical_name_specialties());
                i.putExtra("id", mUserRequestData.get(viewHolder.getAdapterPosition()).getId());
                i.putExtra("price", mUserRequestData.get(viewHolder.getAdapterPosition()).getPrice());
                i.putExtra("address", mUserRequestData.get(viewHolder.getAdapterPosition()).getAddress());
                i.putExtra("start_time", mUserRequestData.get(viewHolder.getAdapterPosition()).getStart_time());
                i.putExtra("end_time", mUserRequestData.get(viewHolder.getAdapterPosition()).getEnd_time());


                //User Information
                i.putExtra("request_admin_name", mUserRequestData.get(viewHolder.getAdapterPosition()).getRequest_admin_name());
                i.putExtra("request_admin_phone", mUserRequestData.get(viewHolder.getAdapterPosition()).getRequest_admin_phone());
                i.putExtra("user_address", mUserRequestData.get(viewHolder.getAdapterPosition()).getUser_address());
                i.putExtra("job_title", mUserRequestData.get(viewHolder.getAdapterPosition()).getJob_title());
                i.putExtra("field", mUserRequestData.get(viewHolder.getAdapterPosition()).getField());
                i.putExtra("registration_number", mUserRequestData.get(viewHolder.getAdapterPosition()).getRegistration_number());
                i.putExtra("graduation_date", mUserRequestData.get(viewHolder.getAdapterPosition()).getGraduation_date());
                i.putExtra("years_of_experience", mUserRequestData.get(viewHolder.getAdapterPosition()).getYears_of_experience());
                i.putExtra("cv", mUserRequestData.get(viewHolder.getAdapterPosition()).getCv());

                context.startActivity(i);

            }
        });

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final AdminRequestViewHolder holder, int position) {
        final UserRequests feeds = mUserRequestData.get(position);
        //Pass the values of feeds object to Views

        holder.medical_name.setText(feeds.getMedical_name());
        holder.medical_name_specialties.setText(feeds.getMedical_name_specialties());
        holder.address.setText(feeds.getAddress());
        holder.price.setText(feeds.getPrice());
        holder.start_time.setText(feeds.getStart_time());
        holder.end_time.setText(feeds.getEnd_time());
        holder.request_admin_name.setText(feeds.getRequest_admin_name());
        holder.request_admin_phone.setText(feeds.getRequest_admin_phone());


        holder.user_address.setText(feeds.getRequest_admin_phone());
        holder.job_title.setText(feeds.getRequest_admin_phone());
        holder.field.setText(feeds.getRequest_admin_phone());
        holder.registration_number.setText(feeds.getRequest_admin_phone());
        holder.graduation_date.setText(feeds.getRequest_admin_phone());
        holder.years_of_experience.setText(feeds.getRequest_admin_phone());


//        holder.mLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent mIntent = new Intent(context, SponsorsDetailActivity.class);
//                mIntent.putExtra("title", holder.title.getText().toString());
//                mIntent.putExtra("content", holder.content.getText().toString());
////                Bundle extras = new Bundle();
////                mIntent.putExtra("imageview", );
////                holder.imageview.getDrawable();
////                mIntent.putExtra("imageview", (Parcelable) holder.imageview.getDrawable());
//                mIntent.putExtra("date", holder.date.getText().toString());
//               context.startActivity(mIntent);
//                fragmentJump(feeds);

//                AppCompatActivity activity = (AppCompatActivity) v.getContext();
//                ShowRequestsDetailFragment myFragment = new ShowRequestsDetailFragment();
//                ShowRequestsDetailFragment myFragment = ShowRequestsDetailFragment.newInstance("fragment1");

        /*mBundle = new Bundle();
        mBundle.putString("medical_name", feeds.getMedical_name());
        mBundle.putString("medical_name_specialties", feeds.getMedical_name_specialties());
        mBundle.putString("address", feeds.getAddress());
        mBundle.putString("price", feeds.getPrice());
        mBundle.putString("start_time", feeds.getStart_time());
        mBundle.putString("end_time", feeds.getEnd_time());
        mBundle.putString("request_admin_name", feeds.getRequest_admin_name());
        mBundle.putString("request_admin_phone", feeds.getRequest_admin_phone());*/
//                myFragment.setArguments(mBundle);
//                fragmentManager.beginTransaction().replace(unique_id_here, fragment).commit();
//                activity.getSupportFragmentManager().beginTransaction().replace(R.id.viewPager, myFragment).addToBackStack(null).commit();
//            }
//        });

    }

//    public void switchContent(int id, Fragment fragment) {
//        if (context == null)
//            return;
//        if (context instanceof MainActivity) {
//            MainActivity mainActivity = (MainActivity) context;
//            Fragment frag = fragment;
//            mainActivity.switchContent(id, frag);
//        }
//    }

//    private void fragmentJump(AdminRequests feeds) {
//        mFragment = new ShowRequestsDetailFragment();
//        mBundle = new Bundle();
//        mBundle.putString("medical_name", feeds.getMedical_name());
//        mBundle.putString("medical_name_specialties", feeds.getMedical_name_specialties());
//        mBundle.putString("address", feeds.getAddress());
//        mBundle.putString("price", feeds.getPrice());
//        mBundle.putString("start_time", feeds.getStart_time());
//        mBundle.putString("end_time", feeds.getEnd_time());
//        mBundle.putString("request_admin_name", feeds.getRequest_admin_name());
//        mBundle.putString("request_admin_phone", feeds.getRequest_admin_phone());
//        mFragment.setArguments(mBundle);
//        switchContent(R.id.viewPager, mFragment);
//    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mUserRequestData.size();
    }

    public class AdminRequestViewHolder extends RecyclerView.ViewHolder {

        TextView medical_name;
        TextView medical_name_specialties;
        TextView address;
        TextView price;
        TextView start_time;
        TextView end_time;
        TextView request_admin_name;
        TextView request_admin_phone;

        TextView user_address, job_title, field, registration_number, graduation_date, years_of_experience;

        RelativeLayout mLayout;
//        FrameLayout mLayout;

        public AdminRequestViewHolder(View itemView) {
            super(itemView);
//            mIcon = itemView.findViewById(R.id.sponsors_thumbnail);
//        mSender = itemView.findViewById(R.id.tvEmailSender);
            medical_name = itemView.findViewById(R.id.medical_name);
            medical_name_specialties = itemView.findViewById(R.id.medical_name_specialties);
            address = itemView.findViewById(R.id.address);
            price = itemView.findViewById(R.id.price);
            start_time = itemView.findViewById(R.id.start_time);
            end_time = itemView.findViewById(R.id.end_time);
            request_admin_name = itemView.findViewById(R.id.request_admin_name);
            request_admin_phone = itemView.findViewById(R.id.request_admin_phone);
            mLayout = itemView.findViewById(R.id.sponsors_layout);

            user_address = itemView.findViewById(R.id.user_address);
            job_title = itemView.findViewById(R.id.job_title);
            field = itemView.findViewById(R.id.field);
            registration_number = itemView.findViewById(R.id.registration_number);
            graduation_date = itemView.findViewById(R.id.graduation_date);
            years_of_experience = itemView.findViewById(R.id.years_of_experience);

        }
    }

}
