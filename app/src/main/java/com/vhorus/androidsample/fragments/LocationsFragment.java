package com.vhorus.androidsample.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.vhorus.androidsample.MySettings;
import com.vhorus.androidsample.R;
import com.vhorus.androidsample.Utils;
import com.vhorus.androidsample.adapters.LocationsAdapter;
import com.vhorus.androidsample.entities.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LocationsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LocationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocationsFragment extends Fragment {
    private static final String TAG = LocationsFragment.class.getSimpleName();

    private OnFragmentInteractionListener mListener;

    ListView locationsListView;
    LocationsAdapter locationsAdapter;
    List<Location> locations;
    TextView noLocationsTextView;

    FloatingActionButton addLocationFab;

    public LocationsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LocationsFragment.
     */
    public static LocationsFragment newInstance(String param1, String param2) {
        LocationsFragment fragment = new LocationsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_locations, container, false);

        noLocationsTextView = view.findViewById(R.id.no_locations_textview);
        addLocationFab = view.findViewById(R.id.add_location_fab);
        locationsListView = view.findViewById(R.id.locations_listview);

        locations = new ArrayList<>();
        locations.addAll(MySettings.getAllLocations());
        locationsAdapter = new LocationsAdapter(getActivity(), locations);
        locationsListView.setAdapter(locationsAdapter);

        if(locations.size() < 1){
            noLocationsTextView.setVisibility(View.VISIBLE);
        }else{
            noLocationsTextView.setVisibility(View.GONE);
        }

        locationsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Location clickedLocation = (Location) locationsAdapter.getItem(position);

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction = Utils.setAnimations(fragmentTransaction, Utils.ANIMATION_TYPE_TRANSLATION);
                LocationDetailsFragment locationDetailsFragment = new LocationDetailsFragment();
                locationDetailsFragment.setLocation(clickedLocation);
                fragmentTransaction.replace(R.id.fragment_view, locationDetailsFragment, "locationDetailsFragment");
                fragmentTransaction.addToBackStack("locationDetailsFragment");
                fragmentTransaction.commit();
            }
        });

        locationsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Location clickedLocation = (Location) locationsAdapter.getItem(position);

                AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                        //set icon
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        //set title
                        .setTitle(Utils.getString(getActivity(), R.string.remove_location_question))
                        //set message
                        .setMessage(Utils.getString(getActivity(), R.string.remove_location_message))
                        //set positive button
                        .setPositiveButton(Utils.getString(getActivity(), R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //set what would happen when positive button is clicked
                                MySettings.removeLocation(clickedLocation.getId());

                                locations.clear();
                                locations.addAll(MySettings.getAllLocations());
                                locationsAdapter.notifyDataSetChanged();
                            }
                        })
                        //set negative button
                        .setNegativeButton(Utils.getString(getActivity(), R.string.no), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //set what should happen when negative button is clicked
                            }
                        })
                        .show();


                return true;
            }
        });

        addLocationFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction = Utils.setAnimations(fragmentTransaction, Utils.ANIMATION_TYPE_TRANSLATION);
                AddLocationFragment addLocationFragment = new AddLocationFragment();
                fragmentTransaction.replace(R.id.fragment_view, addLocationFragment, "addLocationFragment");
                fragmentTransaction.addToBackStack("addLocationFragment");
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
