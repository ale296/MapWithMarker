package com.example.mapwithmarker;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * An activity that displays a Google map with a marker (pin) to indicate a particular location.
 */
public class GPSMarkeringar extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_maps);
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map when it's available.
     * The API invokes this callback when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user receives a prompt to install
     * Play services inside the SupportMapFragment. The API invokes this method after the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Heliga Korsets kapell, Kristianstad,
        // and move the map's camera to the same location.
        LatLng okg = new LatLng(56.028084, 14.168715); // östra kyrkogården
        LatLng gkg = new LatLng(56.024753, 14.156813); // gamla kyrkogården
        LatLng nak = new LatLng(55.989078, 14.150939); // norra åsums kyrkogård
        LatLng rlk = new LatLng(56.053280, 14.179056); // rödaleds kyrkogård
        mMap.addMarker(new MarkerOptions().position(okg).title("Östra begravningsplatsen").snippet(getString(R.string.obpText)).icon(BitmapDescriptorFactory.fromResource(R.drawable.pinobtrans))); // lägga till marker
        mMap.addMarker(new MarkerOptions().position(gkg).title("Gamla begravningsplatsen").snippet(getString(R.string.gbpText)).icon(BitmapDescriptorFactory.fromResource(R.drawable.pingbtrans)));
        mMap.addMarker(new MarkerOptions().position(nak).title("Norra Åsums kyrkogård").snippet(getString(R.string.nakText)).icon(BitmapDescriptorFactory.fromResource(R.drawable.pinnatrans)));
        mMap.addMarker(new MarkerOptions().position(rlk).title("Rödaleds begravningsplats").snippet(getString(R.string.rlkText)).icon(BitmapDescriptorFactory.fromResource(R.drawable.pinrltrans)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(okg, 18)); // center marker on map
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
            Log.d("EEROR", "ERROR");
        } else {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        }
        showMapTypeSelectorDialog();

    }
    private static final CharSequence[] MAP_TYPE_ITEMS =
            {"Road Map", "Hybrid", "Satellite", "Terrain"};

    private void showMapTypeSelectorDialog() {
        // Prepare the dialog by setting up a Builder.
        final String fDialogTitle = "Select Map Type";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(fDialogTitle);

        // Find the current map type to pre-check the item representing the current state.
        int checkItem = mMap.getMapType() - 1;

        // Add an OnClickListener to the dialog, so that the selection will be handled.
        builder.setSingleChoiceItems(
                MAP_TYPE_ITEMS,
                checkItem,
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int item) {
                        // Locally create a finalised object.

                        // Perform an action depending on which item was selected.
                        switch (item) {
                            case 1:
                                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                                break;
                            case 2:
                                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                                break;
                            case 3:
                                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                                break;
                            default:
                                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        }
                        dialog.dismiss();
                    }
                }
        );

        // Build the dialog and show it.
        AlertDialog fMapTypeDialog = builder.create();
        fMapTypeDialog.setCanceledOnTouchOutside(true);
        fMapTypeDialog.show();
    }
}
