package com.patels95.sanam.ewb.ui;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.patels95.sanam.ewb.R;
import com.patels95.sanam.ewb.adapters.ProjectAdapter;
import com.patels95.sanam.ewb.model.ParseConstants;
import com.patels95.sanam.ewb.model.Project;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProjectsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProjectsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProjectsFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String TAG = ProjectsFragment.class.getSimpleName();
    private int mSectionNumber;

    @InjectView(R.id.projectRecyclerView) RecyclerView mProjectRecyclerView;

    private OnFragmentInteractionListener mListener;
    private Project[] mProjectCards;

    public static ProjectsFragment newInstance(int sectionNumber) {
        ProjectsFragment fragment = new ProjectsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ProjectsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        mProjectCards = setProjectsArray();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_projects, container, false);
        ButterKnife.inject(this, view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mProjectRecyclerView.setLayoutManager(layoutManager);
        ProjectAdapter adapter = new ProjectAdapter(getActivity(), mProjectCards);
        mProjectRecyclerView.setAdapter(adapter);

        return view;
    }


    private Project[] setProjectsArray() {

        // create getParseProjects() with code below for query

        ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstants.PROJECT_CLASS);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {

            }
        });

        Project filtration = new Project();
        filtration.setTitle(getString(R.string.filtration_title));
        filtration.setDescription(getString(R.string.filtration_description));
        filtration.setImageUri(getString(R.string.filtration_image_uri));

        Project storage = new Project();
        storage.setTitle(getString(R.string.storage_title));
        storage.setDescription(getString(R.string.storage_description));
        storage.setImageUri(getString(R.string.storage_image_uri));

        Project hygiene = new Project();
        hygiene.setTitle(getString(R.string.hygiene_title));
        hygiene.setDescription(getString(R.string.hygiene_description));
        hygiene.setImageUri(getString(R.string.hygiene_image_uri));

        Project borehole = new Project();
        borehole.setTitle(getString(R.string.borehole_title));
        borehole.setDescription(getString(R.string.borehole_description));
        borehole.setImageUri(getString(R.string.borehold_image_uri));

        // create setParseProjects() with code below

        // Create parse objects for Project class

//        ParseObject parseFiltration = new ParseObject(ParseConstants.PROJECT_CLASS);
//        parseFiltration.put(ParseConstants.PROJECT_TITLE, getString(R.string.filtration_title));
//        parseFiltration.put(ParseConstants.PROJECT_DESCRIPTION, getString(R.string.filtration_description));
//        parseFiltration.put(ParseConstants.PROJECT_IMAGEURI, getString(R.string.filtration_image_uri));
//        parseFiltration.saveInBackground();
//
//        ParseObject parseStorage = new ParseObject(ParseConstants.PROJECT_CLASS);
//        parseStorage.put(ParseConstants.PROJECT_TITLE, getString(R.string.storage_title));
//        parseStorage.put(ParseConstants.PROJECT_DESCRIPTION, getString(R.string.storage_description));
//        parseStorage.put(ParseConstants.PROJECT_IMAGEURI, getString(R.string.storage_image_uri));
//        parseStorage.saveInBackground();
//
//        ParseObject parseHygiene = new ParseObject(ParseConstants.PROJECT_CLASS);
//        parseHygiene.put(ParseConstants.PROJECT_TITLE, getString(R.string.hygiene_title));
//        parseHygiene.put(ParseConstants.PROJECT_DESCRIPTION, getString(R.string.hygiene_description));
//        parseHygiene.put(ParseConstants.PROJECT_IMAGEURI, getString(R.string.hygiene_image_uri));
//        parseHygiene.saveInBackground();
//
//        ParseObject parseBorehole = new ParseObject(ParseConstants.PROJECT_CLASS);
//        parseBorehole.put(ParseConstants.PROJECT_TITLE, getString(R.string.borehole_title));
//        parseBorehole.put(ParseConstants.PROJECT_DESCRIPTION, getString(R.string.borehole_description));
//        parseBorehole.put(ParseConstants.PROJECT_IMAGEURI, getString(R.string.borehold_image_uri));
//        parseBorehole.saveInBackground();


        return new Project[] {filtration, storage, hygiene, borehole};
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
