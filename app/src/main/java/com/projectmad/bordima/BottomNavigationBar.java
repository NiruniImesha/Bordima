package com.projectmad.bordima;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BottomNavigationBar extends AppCompatActivity {

    //number of selected tab, we have 3 tabs so value must lie between 1-4. default value is 1 because first tab is selected by default.
    private int selectedTab = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_bar);

        final LinearLayout boardingLayout = findViewById(R.id.boardingLayout);
        final LinearLayout serviceLayout = findViewById(R.id.serviceLayout);
        final LinearLayout profileLayout = findViewById(R.id.profileLayout);

        final ImageView boardingImage = findViewById(R.id.boardingImage);
        final ImageView serviceImage = findViewById(R.id.serviceImage);
        final ImageView profileImage = findViewById(R.id.profileImage);

        final TextView boardingText = findViewById(R.id.boardingText);
        final TextView serviceText = findViewById(R.id.serviceText);
        final TextView profileText = findViewById(R.id.profileText);


        //set boarding fragment by default
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentContainer, BoardingFragment.class, null)
                .commit();

        boardingLayout. setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //check if boarding is already selected or not
                if(selectedTab != 1){

                    //set boarding fragment
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer, BoardingFragment.class, null)
                            .commit();

                    //unselected other tabs except boarding tab
                    serviceText.setVisibility(View.GONE);
                    profileText.setVisibility(View.GONE);

                    serviceImage.setImageResource(R.drawable.service_icon);
                    profileImage.setImageResource(R.drawable.profile_icon);

                    serviceLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    profileLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    //select boarding tab
                    boardingText.setVisibility(View.VISIBLE);
                    boardingImage.setImageResource(R.drawable.home_selected_icon);
                    boardingLayout.setBackgroundResource(R.drawable.round_back_home_100);

                    //create animation
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f,1.0f,1f,1f, Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    boardingLayout.startAnimation(scaleAnimation);

                    //set 1st tab as selected tab
                    selectedTab = 1;


                }
            }
        });

        serviceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //check if service is already selected or not
                if(selectedTab != 2){

                    //set service fragment default
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer, ServiceFragment.class, null)
                            .commit();
                    //unselected other tabs except service tab
                    boardingText.setVisibility(View.GONE);
                    profileText.setVisibility(View.GONE);

                    boardingImage.setImageResource(R.drawable.home_icon);
                    profileImage.setImageResource(R.drawable.profile_icon);

                    boardingLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    profileLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    //select service tab
                    serviceText.setVisibility(View.VISIBLE);
                    serviceImage.setImageResource(R.drawable.service_selected_icon);
                    serviceLayout.setBackgroundResource(R.drawable.round_back_home_100);

                    //create animation
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f,1.0f,1f,1f, Animation.RELATIVE_TO_SELF,1.0f,Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    serviceLayout.startAnimation(scaleAnimation);

                    //set 2nd tab as selected tab
                    selectedTab = 2;

                }

            }
        });

        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //check if profile is already selected or not
                if(selectedTab != 3){

                    //set profile fragment default
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer, ProfileFragment.class, null)
                            .commit();

                    //unselected other tabs except profile tab
                    serviceText.setVisibility(View.GONE);
                    boardingText.setVisibility(View.GONE);

                    serviceImage.setImageResource(R.drawable.service_icon);
                    boardingImage.setImageResource(R.drawable.home_icon);

                    serviceLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    boardingLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    //select profile tab
                    profileText.setVisibility(View.VISIBLE);
                    profileImage.setImageResource(R.drawable.profile_selected_icon);
                    profileLayout.setBackgroundResource(R.drawable.round_back_home_100);

                    //create animation
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f,1.0f,1f,1f, Animation.RELATIVE_TO_SELF,1.0f,Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    boardingLayout.startAnimation(scaleAnimation);

                    //set 3rd tab as selected tab
                    selectedTab = 3;

                }

            }
        });

    }
}