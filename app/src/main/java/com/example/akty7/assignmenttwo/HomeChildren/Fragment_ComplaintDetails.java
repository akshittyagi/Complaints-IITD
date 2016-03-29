package com.example.akty7.assignmenttwo.HomeChildren;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.akty7.assignmenttwo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_ComplaintDetails extends Fragment {

    public Fragment_ComplaintDetails() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_complaint_details,container,false);

    }

}
