package co.edu.univalle.shoppingcart.presentacion;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import co.edu.univalle.shoppingcart.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        //Settings bar
        //setSupportActionBar(toolbar);

        //Button flotante
        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //Firebase
        firebaseAuth = FirebaseAuth.getInstance();

        /*Cargar lista de productos*/
        CargarFragmentProductos();
    }

    protected void onStart() {
        super.onStart();

         firebaseUser = firebaseAuth.getCurrentUser();

        /*Cargar usuario*/
        CargarUsuario();
    }

    public void listaProductos(View view){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentProductList = fragmentManager.beginTransaction();
        fragmentProductList.replace(R.id.contenido, new FragmentProductCart());
        fragmentProductList.addToBackStack(null);
        fragmentProductList.commit();
    }

    private void CargarUsuario(){
        NavigationView navigationView = findViewById(R.id.nav_view);

        View userHeaderView = navigationView.getHeaderView(0);
        TextView txtNavUserEmail = userHeaderView.findViewById(R.id.txtUserHeader);
        txtNavUserEmail.setText(firebaseUser.getEmail());

        TextView txtNavUserName = userHeaderView.findViewById(R.id.txtUserNameHeader);
        txtNavUserName.setText(firebaseUser.getDisplayName());
    }

    private void CargarFragmentProductos(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentProductList = fragmentManager.beginTransaction();
        fragmentProductList.add(R.id.contenido, new FragmentShoppingCart());
        fragmentProductList.commit();
    }

    private void CargarFragmentMapa(){
        /*FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentProductList = fragmentManager.beginTransaction();
        fragmentProductList.replace(R.id.contenido, new FragmentStoresMap());
        fragmentProductList.addToBackStack(null);
        fragmentProductList.commit();*/

        Intent intent = new Intent(this, FragmentStoresMap.class);
        startActivity(intent);
        //this.finish();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        /*if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            CargarFragmentMapa();
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        if(id == R.id.nav_shopping){
            CargarFragmentMapa();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
