package app.asu.Models;

import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

public class RecreationZoneModel {
    private String address;
    private List<LatLng> coordinates;
    private String coordinatesJSON;
    private String name;
    private int typeId;
    private String website;

    public RecreationZoneModel() {
        this.coordinates = new ArrayList();
    }

    public RecreationZoneModel(String name, List<LatLng> coordinates) {
        this.name = name;
        this.typeId = 1;
        this.coordinates = new ArrayList();
        this.coordinates = coordinates;
    }

    public RecreationZoneModel(String name, int typeId, String website, String address, List<LatLng> coordinates) {
        this.name = name;
        this.typeId = typeId;
        this.website = website;
        this.address = address;
        this.coordinates = new ArrayList();
        this.coordinates = coordinates;
    }

    public RecreationZoneModel(String name, int typeId, String website, String address, String coordinates) {
        this.name = name;
        this.typeId = typeId;
        this.website = website;
        this.address = address;
        this.coordinates = new ArrayList();
        try {
            JSONArray jsonArray = new JSONArray(coordinates);
            for (int i = 0; i < jsonArray.length(); i += 2) {
                this.coordinates.add(new LatLng(Double.parseDouble(jsonArray.get(i).toString()), Double.parseDouble(jsonArray.get(i + 1).toString())));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTypeId() {
        return this.typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getWebsite() {
        return this.website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LatLng getCoordinate() {
        return (LatLng) this.coordinates.get(0);
    }

    public void setCoordinate(LatLng coordinate, int i) {
        this.coordinates.set(i, coordinate);
    }

    public void addCoordinate(LatLng coordinate) {
        this.coordinates.add(coordinate);
    }

    public List<LatLng> getCoordinates() {
        return this.coordinates;
    }

    public void setCoordinates(List<LatLng> coordinates) {
        this.coordinates = coordinates;
    }

    public String getCoordinatesJSON() {
        JSONArray jsonArray = new JSONArray();
        for (LatLng coordinate : this.coordinates) {
            try {
                jsonArray.put(coordinate.latitude);
                jsonArray.put(coordinate.longitude);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonArray.toString();
    }
}
