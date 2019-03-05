package app.asu;

import android.app.Fragment;
import android.content.IntentSender.SendIntentException;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.Date;

public class SportsFragment extends Fragment implements LocationListener, ConnectionCallbacks, OnConnectionFailedListener {
    Long chronometerTime = null;
    TextView distanceTextView;
    private GoogleMap googleMap;
    int mAction = -1;
    private Location mLastLocation = null;
    private long mLastTime = 0;
    private LocationClient mLocationClient;
    private LocationRequest mLocationRequest;
    private ArrayDeque<LatLng> mRouteLines = new ArrayDeque();
    private double mTrackedDistance = 0.0d;
    private long mTrackedTime = 0;
    private boolean mUpdatesRequested = false;
    boolean moveToLocation = true;
    ImageButton pauseButton;
    TextView speedTextView;
    ImageButton startButton;
    ImageButton stopButton;
    Chronometer timeTextView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sports, container, false);
        this.mLocationRequest = LocationRequest.create();
        this.mLocationRequest.setPriority(100);
        this.mLocationRequest.setInterval(1000);
        this.mLocationRequest.setFastestInterval(1000);
        this.mLocationClient = new LocationClient(getActivity().getApplicationContext(), this, this);
        MapView mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        this.googleMap = mapView.getMap();
        MapsInitializer.initialize(getActivity().getApplicationContext());
        this.googleMap.setMyLocationEnabled(true);
        this.googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        this.googleMap.setOnMyLocationChangeListener(new OnMyLocationChangeListener() {
            public void onMyLocationChange(Location location) {
                if (SportsFragment.this.moveToLocation) {
                    SportsFragment.this.googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15.0f));
                }
                SportsFragment.this.moveToLocation = false;
            }
        });
        this.startButton = (ImageButton) view.findViewById(R.id.start);
        this.startButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                switch (SportsFragment.this.mAction) {
                    case -1:
                        SportsFragment.this.googleMap.clear();
                        SportsFragment.this.mLastLocation = null;
                        SportsFragment.this.mLastTime = 0;
                        SportsFragment.this.mTrackedDistance = 0.0d;
                        SportsFragment.this.mTrackedTime = 0;
                        SportsFragment.this.mRouteLines.clear();
                        SportsFragment.this.timeTextView.setBase(SystemClock.elapsedRealtime());
                        SportsFragment.this.timeTextView.start();
                        break;
                    case 1:
                        SportsFragment.this.mLastLocation = null;
                        SportsFragment.this.mLastTime = 0;
                        SportsFragment.this.mRouteLines.clear();
                        if (SportsFragment.this.chronometerTime != null) {
                            SportsFragment.this.timeTextView.setBase(SystemClock.elapsedRealtime() + SportsFragment.this.chronometerTime.longValue());
                        }
                        SportsFragment.this.timeTextView.start();
                        break;
                    case 2:
                        SportsFragment.this.googleMap.clear();
                        SportsFragment.this.mLastLocation = null;
                        SportsFragment.this.mLastTime = 0;
                        SportsFragment.this.mTrackedDistance = 0.0d;
                        SportsFragment.this.mTrackedTime = 0;
                        SportsFragment.this.mRouteLines.clear();
                        SportsFragment.this.timeTextView.setBase(SystemClock.elapsedRealtime());
                        SportsFragment.this.timeTextView.start();
                        break;
                }
                SportsFragment.this.startButton.setVisibility(8);
                SportsFragment.this.pauseButton.setVisibility(0);
                SportsFragment.this.stopButton.setVisibility(0);
                SportsFragment.this.mAction = 0;
                SportsFragment.this.mUpdatesRequested = true;
                SportsFragment.this.startPeriodicUpdates();
            }
        });
        this.pauseButton = (ImageButton) view.findViewById(R.id.pause);
        this.pauseButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                SportsFragment.this.startButton.setVisibility(0);
                SportsFragment.this.pauseButton.setVisibility(8);
                if (SportsFragment.this.mLastLocation != null) {
                    SportsFragment.this.googleMap.addMarker(new MarkerOptions().position(new LatLng(SportsFragment.this.mLastLocation.getLatitude(), SportsFragment.this.mLastLocation.getLongitude())).title("Pauzė: " + new SimpleDateFormat("H:mm:ss").format(new Date())).icon(BitmapDescriptorFactory.defaultMarker(0.0f)));
                }
                SportsFragment.this.mAction = 1;
                SportsFragment.this.mUpdatesRequested = false;
                SportsFragment.this.stopPeriodicUpdates();
                SportsFragment.this.chronometerTime = Long.valueOf(SportsFragment.this.timeTextView.getBase() - SystemClock.elapsedRealtime());
                SportsFragment.this.timeTextView.stop();
            }
        });
        this.stopButton = (ImageButton) view.findViewById(R.id.stop);
        this.stopButton.setVisibility(8);
        this.stopButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                SportsFragment.this.startButton.setVisibility(0);
                SportsFragment.this.pauseButton.setVisibility(8);
                SportsFragment.this.stopButton.setVisibility(8);
                if (SportsFragment.this.mLastLocation != null) {
                    SportsFragment.this.googleMap.addMarker(new MarkerOptions().position(new LatLng(SportsFragment.this.mLastLocation.getLatitude(), SportsFragment.this.mLastLocation.getLongitude())).title("Pabaiga: " + new SimpleDateFormat("H:mm:ss").format(new Date())).icon(BitmapDescriptorFactory.defaultMarker(0.0f)));
                } else {
                    SportsFragment.this.googleMap.clear();
                }
                SportsFragment.this.mAction = 2;
                SportsFragment.this.mUpdatesRequested = false;
                SportsFragment.this.stopPeriodicUpdates();
                SportsFragment.this.timeTextView.stop();
            }
        });
        this.timeTextView = (Chronometer) view.findViewById(R.id.time);
        this.timeTextView.setOnChronometerTickListener(new OnChronometerTickListener() {
            public void onChronometerTick(Chronometer chronometer) {
                if (!String.format("%.2f", new Object[]{Double.valueOf((SportsFragment.this.mTrackedDistance / 1000.0d) / (((Double.valueOf((double) SportsFragment.this.mTrackedTime).doubleValue() / 1000.0d) / 60.0d) / 60.0d))}).equals("NaN")) {
                    SportsFragment.this.distanceTextView.setText(String.format("%.2f", new Object[]{Double.valueOf(SportsFragment.this.mTrackedDistance / 1000.0d)}) + " km");
                    SportsFragment.this.speedTextView.setText(String.format("%.2f", new Object[]{Double.valueOf((SportsFragment.this.mTrackedDistance / 1000.0d) / (((Double.valueOf((double) SportsFragment.this.mTrackedTime).doubleValue() / 1000.0d) / 60.0d) / 60.0d))}) + " km/h");
                }
            }
        });
        this.distanceTextView = (TextView) view.findViewById(R.id.distance);
        this.speedTextView = (TextView) view.findViewById(R.id.speed);
        return view;
    }

    public void onStop() {
        if (this.mLocationClient.isConnected()) {
            stopPeriodicUpdates();
        }
        this.mLocationClient.disconnect();
        super.onStop();
    }

    public void onStart() {
        super.onStart();
        this.mLocationClient.connect();
    }

    public void onConnected(Bundle bundle) {
        if (this.mUpdatesRequested) {
            startPeriodicUpdates();
        }
    }

    public void onDisconnected() {
    }

    public void onLocationChanged(Location location) {
        switch (this.mAction) {
            case 0:
                if (this.mRouteLines.size() == 0) {
                    this.googleMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("Pradžia: " + new SimpleDateFormat("H:mm:ss").format(new Date())).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                    this.mLastLocation = location;
                    this.mLastTime = new Date().getTime();
                    this.mRouteLines.add(new LatLng(location.getLatitude(), location.getLongitude()));
                } else if (this.mLastLocation != null) {
                    Date tempDate = new Date();
                    this.mTrackedTime += tempDate.getTime() - this.mLastTime;
                    this.mTrackedDistance += (double) this.mLastLocation.distanceTo(location);
                    this.mLastLocation = location;
                    this.mLastTime = tempDate.getTime();
                    if (this.mRouteLines.size() == 3) {
                        this.mRouteLines.removeFirst();
                    }
                    this.mRouteLines.addLast(new LatLng(location.getLatitude(), location.getLongitude()));
                    PolylineOptions options = new PolylineOptions().width(7.0f).color(Color.parseColor("#80098291"));
                    options.addAll(this.mRouteLines);
                    this.googleMap.addPolyline(options);
                }
                this.googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15.0f));
                return;
            default:
                return;
        }
    }

    private void startPeriodicUpdates() {
        this.mLocationClient.requestLocationUpdates(this.mLocationRequest, (LocationListener) this);
    }

    private void stopPeriodicUpdates() {
        this.mLocationClient.removeLocationUpdates((LocationListener) this);
    }

    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(getActivity(), LocationUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (SendIntentException e) {
                e.printStackTrace();
            }
        }
    }
}
