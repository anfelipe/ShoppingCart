package co.edu.univalle.shoppingcart.presentacion;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import co.edu.univalle.shoppingcart.R;

public class FragmentStoresMap extends AppCompatActivity
        implements OnMapReadyCallback, LocationListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mapa;
    private boolean isAuthorized = true;
    private LocationManager locationManager;
    private static final int CODIGO_PERMISOS_LOCATION = 1;

    private LatLng posicionInicial;
    private LatLng posicionFinal;

    private Double inicialLatitude;
    private Double inicialLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_stores_map);

        SupportMapFragment supportMapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapa);

        supportMapFragment.getMapAsync(this);

        ObtenerUbicacionActual();
    }

    public void ObtenerUbicacionActual(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                isAuthorized = !isAuthorized;

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        CODIGO_PERMISOS_LOCATION);
            }
        }

        Log.i("INFO", "isAuthorized" + isAuthorized);

        if(isAuthorized){
            locationManager = (LocationManager) this.
                    getSystemService(this.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    1000, 0, this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        inicialLatitude = location.getLatitude();
        inicialLongitude = location.getLongitude();

        posicionInicial = new LatLng(inicialLatitude,inicialLongitude);
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(posicionInicial,13));

        MarkerOptions marcadorInicial = new MarkerOptions();
        marcadorInicial.title("Yo");
        marcadorInicial.position(posicionInicial);
        marcadorInicial.icon(BitmapDescriptorFactory.fromResource(R.drawable.bug));
        mapa.addMarker(marcadorInicial);

        //CameraPosition cameraPosition = CameraPosition.builder().target(posicionInicial).zoom(15).bearing(90).build();
        //mapa.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),5000,null);

        locationManager.removeUpdates(this);

        Log.i("Info","Posicion inicial: "+inicialLatitude+" ---- "+inicialLongitude);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CODIGO_PERMISOS_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this,"Permiso de ubicacion concedido",
                            Toast.LENGTH_LONG).show();
                    isAuthorized = true;
                    ObtenerUbicacionActual();
                } else {
                    Toast.makeText(this,"Permiso de ubicacion no fue concedido",
                            Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;
        mapa.getUiSettings().setZoomControlsEnabled(true);
        mapa.setOnMarkerClickListener(this);

        //marcador final para armar la ruta
        posicionFinal = new LatLng(3.4590,-76.5290);
        MarkerOptions marcadorFinal = new MarkerOptions();
        marcadorFinal.title("Torre de Cali");
        marcadorFinal.position(posicionFinal);
        mapa.addMarker(marcadorFinal);

        posicionFinal = new LatLng(3.417788,-76.540451);
        MarkerOptions marcadorFinal2 = new MarkerOptions();
        marcadorFinal2.title("Tequendama");
        marcadorFinal2.position(posicionFinal);
        mapa.addMarker(marcadorFinal2);

        posicionFinal = new LatLng(3.434812, -76.517398);

        CameraPosition cameraPosition = CameraPosition.builder().target(posicionFinal).zoom(15).bearing(90).build();
        mapa.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),10000,null);
    }
}
