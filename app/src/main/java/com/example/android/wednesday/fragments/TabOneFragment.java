package com.example.android.wednesday.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.wednesday.R;
import com.github.demono.AutoScrollViewPager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TabOneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabOneFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public TabOneFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabOneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TabOneFragment newInstance(String param1, String param2) {
        TabOneFragment fragment = new TabOneFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_tab_one, container, false);
        AutoScrollViewPager mViewPager = (AutoScrollViewPager) rootView.findViewById(R.id.viewPager);
        mViewPager.startAutoScroll();

        //For Dynamic addition

//        LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.linear);
//        for (int i = 0; i < 10; i++) {
//            ImageView imageView = new ImageView(getContext());
//            imageView.setId(i);
//            imageView.setPadding(2, 2, 2, 2);
//            imageView.setImageBitmap(BitmapFactory.decodeResource(
//                    getResources(), R.mipmap.ic_launcher));
//            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//            layout.addView(imageView);
//
//        }
        return rootView;
    }

}
