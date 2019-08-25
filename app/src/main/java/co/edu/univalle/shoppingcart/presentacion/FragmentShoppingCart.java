package co.edu.univalle.shoppingcart.presentacion;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import co.edu.univalle.shoppingcart.R;
import co.edu.univalle.shoppingcart.adaptadores.AdaptadorShoppingCart;
import co.edu.univalle.shoppingcart.dominio.IProductLogic;
import co.edu.univalle.shoppingcart.dominio.ProductoLogic;
import co.edu.univalle.shoppingcart.dto.DataBaseDTO;
import co.edu.univalle.shoppingcart.dto.ProductoDTO;

public class FragmentShoppingCart extends Fragment implements IGenericPresenter {

    private RecyclerView shoppingRecyclerView;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private RelativeLayout relativeLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_shoppingcart, container, false);

        relativeLayout = view.findViewById(R.id.loadingPanel);
        relativeLayout.setVisibility(View.GONE);

        shoppingRecyclerView = (RecyclerView) view.findViewById(R.id.listaProductos);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2,
                GridLayoutManager.VERTICAL, false);
        shoppingRecyclerView.setLayoutManager(gridLayoutManager);
        shoppingRecyclerView.setHasFixedSize(true);
        //DividerItemDecoration decoration = new DividerItemDecoration(getContext(), gridLayoutManager.getOrientation());
        //shoppingRecyclerView.addItemDecoration(decoration);
        shoppingRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //shoppingRecyclerView.addItemDecoration(new SpacesItemDecoration(2, 12, false));

        //Firebase
        firebaseAuth = FirebaseAuth.getInstance();

        //Cargar adaptador vacio
        CargarAdapter(new ArrayList<ProductoDTO>());

        relativeLayout.setVisibility(View.VISIBLE);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ConsultarProductosTodos();
            }
        }, 1000);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseUser = firebaseAuth.getCurrentUser();
    }

    private void ConsultarProductosTodos(){
        try{
            IProductLogic iProducto = new ProductoLogic();
            iProducto.ConsultarProductosCategoria1(this, firebaseUser.getUid());
        }catch (Exception ex){
            Log.e("Error", "Error al consultar los productos " + ex.getMessage());

            Toast.makeText(getContext(), "Los productos no se cargaron correctamente",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void CargarAdapter(List<ProductoDTO> list){
        AdaptadorShoppingCart adaptador = new AdaptadorShoppingCart(getActivity(), list);
        shoppingRecyclerView.setAdapter(adaptador);
    }

    private void CargarDatos(String respuesta){
        try{
            IProductLogic iProducto = new ProductoLogic();
            List<ProductoDTO> productolist = iProducto.ListaProductosInicio(respuesta, firebaseUser.getUid());

            CargarAdapter(productolist);

        }catch (Exception e){
            relativeLayout.setVisibility(View.GONE);
            Log.e("Error", e.getMessage());

            Toast.makeText(getContext(), "Los productos no se cargaron correctamente",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void actualizarVista(DataBaseDTO dataBaseDTO) {
        Log.i("Info-actualizarVista", "Terminó");

        if(dataBaseDTO.getOperacion().equals("all")){
            Log.i("Info-Respuesta", dataBaseDTO.getRespuesta());
            CargarDatos(dataBaseDTO.getRespuesta());
        }

        relativeLayout.setVisibility(View.GONE);
    }

    @Override
    public void notificar(DataBaseDTO dataBaseDTO) {
        Log.i("Info-notificar", "se dañó");
        relativeLayout.setVisibility(View.GONE);
    }
}
