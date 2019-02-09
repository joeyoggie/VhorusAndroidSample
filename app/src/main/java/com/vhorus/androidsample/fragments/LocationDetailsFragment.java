package com.vhorus.androidsample.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vhorus.androidsample.R;
import com.vhorus.androidsample.Utils;
import com.vhorus.androidsample.activities.MainActivity;
import com.vhorus.androidsample.entities.Location;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LocationDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LocationDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocationDetailsFragment extends Fragment {
    private static final String TAG = LocationDetailsFragment.class.getSimpleName();

    private OnFragmentInteractionListener mListener;

    TextView nameTextView, timestampTextView, longitudeTextView, latitudeTextView, altitudeTextView;

    private Location location;

    public LocationDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LocationDetailsFragment.
     */
    public static LocationDetailsFragment newInstance(String param1, String param2) {
        LocationDetailsFragment fragment = new LocationDetailsFragment();
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
        View view = inflater.inflate(R.layout.fragment_location_details, container, false);
        MainActivity.setActionBarTitle(Utils.getString(getActivity(), R.string.location_details), getResources().getColor(R.color.whiteColor));

        nameTextView = view.findViewById(R.id.location_name_textview);
        timestampTextView = view.findViewById(R.id.location_timestamp_textview);
        longitudeTextView = view.findViewById(R.id.location_longitude_textview);
        latitudeTextView = view.findViewById(R.id.location_latitude_textview);
        altitudeTextView = view.findViewById(R.id.location_altitude_textview);

        if(location != null){
            nameTextView.setText(""+location.getTitle());
            timestampTextView.setText(""+Utils.getTimeStringDateHoursMinutes(location.getTimestamp()));
            longitudeTextView.setText(""+location.getLongitude());
            latitudeTextView.setText(""+location.getLatitude());
            altitudeTextView.setText(""+location.getAltitude());
        }else{
            if(getFragmentManager() != null){
                getFragmentManager().popBackStack();
            }
        }

        return view;
    }

    public void setLocation(Location location){
        this.location = location;
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
