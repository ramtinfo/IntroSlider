package com.example.ram.introslider;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeScreen extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {
    boolean mShowingBack = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        CardBackFragment.setClick1(new CardBackFragment.getClick1() {
            @Override
            public void onGetClick(boolean value) {
                if(value){
                    flipCard();
                }
            }
        });
        CardFrontFragment.setClick(new CardFrontFragment.getClick() {
            @Override
            public void onGetClick(boolean value) {
                if(value){
                    flipCard();
                }
            }
        });
        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new CardFrontFragment())
                    .commit();
        } else {
            mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);
        }

        // Monitor back stack changes to ensure the action bar shows the appropriate
        // button (either "photo" or "info").
        getFragmentManager().addOnBackStackChangedListener(this);

    }

    public static class CardFrontFragment extends Fragment {
        static getClick listner;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view=inflater.inflate(R.layout.search_layout, container, false);
            view.findViewById(R.id.setting).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listner!=null)
                    listner.onGetClick(true);
                }
            });
            return view;
        }
        static  void setClick(getClick olistner){
            listner=olistner;
        }
        public interface getClick{
            void onGetClick(boolean value);
        }
    }

    /**
     * A fragment representing the back of the card.
     */
    public static class CardBackFragment extends Fragment {
        static getClick1 listner1;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view=inflater.inflate(R.layout.filterlayout, container, false);
            view.findViewById(R.id.tool).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listner1!=null){
                        listner1.onGetClick(true);
                    }
                }
            });
            return view;
        }
        static  void setClick1(getClick1 olistner){
            listner1=olistner;
        }
        public interface getClick1{
            void onGetClick(boolean value);
        }
    }

    public void flipCard() {
        if (mShowingBack) {
            getFragmentManager().popBackStack();
            return;
        }

        // Flip to the back.

        mShowingBack = true;

        // Create and commit a new fragment transaction that adds the fragment for
        // the back of the card, uses custom animations, and is part of the fragment
        // manager's back stack.

        getFragmentManager()
                .beginTransaction()

                // Replace the default fragment animations with animator resources
                // representing rotations when switching to the back of the card, as
                // well as animator resources representing rotations when flipping
                // back to the front (e.g. when the system Back button is pressed).
                .setCustomAnimations(
                        R.animator.card_flip_right_in,
                        R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in,
                        R.animator.card_flip_left_out)

                // Replace any fragments currently in the container view with a
                // fragment representing the next page (indicated by the
                // just-incremented currentPage variable).
                .replace(R.id.container, new CardBackFragment())

                // Add this transaction to the back stack, allowing users to press
                // Back to get to the front of the card.
                .addToBackStack(null)

                // Commit the transaction.
                .commit();
    }

    @Override
    public void onBackStackChanged() {
        mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);

        // When the back stack changes, invalidate the options menu (action bar).
        invalidateOptionsMenu();
    }
}


