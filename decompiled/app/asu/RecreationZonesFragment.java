package app.asu;

import android.app.Fragment;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import app.asu.CustomMultiSelectionSpinner.OnItemSelected;
import app.asu.Databases.RecreationZonesDB;
import app.asu.Models.RecreationZoneModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import java.util.ArrayList;
import java.util.List;

public class RecreationZonesFragment extends Fragment implements OnItemSelected {
    CustomMultiSelectionSpinner filterSpinner;
    GoogleMap googleMap;
    private boolean moveToLocation;
    List<RecreationZoneModel> recreationZoneModels;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recreation_zones, container, false);
        this.recreationZoneModels = new RecreationZonesDB(getActivity().getApplicationContext()).getRecreationZones();
        List filterItems = new ArrayList();
        filterItems.add("Bėgimo trasos");
        filterItems.add("Sporto klubai");
        filterItems.add("KTU sporto bazės");
        filterItems.add("Rekreacinės zonos");
        this.filterSpinner = (CustomMultiSelectionSpinner) view.findViewById(R.id.spinner);
        this.filterSpinner.setItems(filterItems);
        this.filterSpinner.setSelection(new int[]{0, 1, 2, 3});
        this.filterSpinner.setCallback(this);
        MapView mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        this.googleMap = mapView.getMap();
        MapsInitializer.initialize(getActivity());
        this.googleMap.setMyLocationEnabled(true);
        this.googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        setupMap();
        this.moveToLocation = true;
        this.googleMap.setOnMyLocationChangeListener(new OnMyLocationChangeListener() {
            public void onMyLocationChange(Location location) {
                if (RecreationZonesFragment.this.moveToLocation) {
                    RecreationZonesFragment.this.googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15.0f));
                }
                RecreationZonesFragment.this.moveToLocation = false;
            }
        });
        return view;
    }

    private void setupMap() {
        this.googleMap.clear();
        List<Integer> choices = this.filterSpinner.getSelectedIndicies();
        for (RecreationZoneModel recreationZoneModel : this.recreationZoneModels) {
            for (Integer intValue : choices) {
                if (intValue.intValue() + 1 == recreationZoneModel.getTypeId()) {
                    switch (recreationZoneModel.getTypeId()) {
                        case 1:
                            PolylineOptions options = new PolylineOptions().width(7.0f).color(Color.parseColor("#80098291"));
                            options.addAll(recreationZoneModel.getCoordinates());
                            this.googleMap.addPolyline(options);
                            this.googleMap.addMarker(new MarkerOptions().position(recreationZoneModel.getCoordinate()).title(recreationZoneModel.getName()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                            break;
                        case 2:
                            this.googleMap.addMarker(new MarkerOptions().position(recreationZoneModel.getCoordinate()).title(recreationZoneModel.getName()).snippet(recreationZoneModel.getAddress() + " | " + recreationZoneModel.getWebsite()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                            break;
                        case 3:
                            this.googleMap.addMarker(new MarkerOptions().position(recreationZoneModel.getCoordinate()).title(recreationZoneModel.getName()).snippet(recreationZoneModel.getAddress() + " | " + recreationZoneModel.getWebsite()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                            break;
                        case 4:
                            this.googleMap.addMarker(new MarkerOptions().position(recreationZoneModel.getCoordinate()).title(recreationZoneModel.getName()).snippet(recreationZoneModel.getAddress() + " | " + recreationZoneModel.getWebsite()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    public void OnItemSelected() {
        setupMap();
    }
}
