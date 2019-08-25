package co.edu.univalle.shoppingcart.presentacion;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import co.edu.univalle.shoppingcart.R;
import co.edu.univalle.shoppingcart.adaptadores.AdaptadorProductBuy;
import co.edu.univalle.shoppingcart.adaptadores.AdaptadorShoppingCart;
import co.edu.univalle.shoppingcart.dominio.IProductLogic;
import co.edu.univalle.shoppingcart.dominio.ProductoLogic;
import co.edu.univalle.shoppingcart.dto.DataBaseDTO;
import co.edu.univalle.shoppingcart.dto.ProductoDTO;

public class FragmentProductCart extends Fragment implements IGenericPresenter {

    private RecyclerView shoppingRecyclerView;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private TextView txtTotal;
    private TextView txtCantidad;
    private Button btnComprar;

    private List<ProductoDTO> productoDTOList;
    private AdaptadorProductBuy adaptador;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_product_buy, container, false);

        txtCantidad = view.findViewById(R.id.txt_cantidad_productos);
        txtTotal = view.findViewById(R.id.txt_total_pagar);
        btnComprar = view.findViewById(R.id.btnComprar);
        shoppingRecyclerView = (RecyclerView) view.findViewById(R.id.listaProductosBuy);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2,
                GridLayoutManager.VERTICAL, false);

        shoppingRecyclerView.setLayoutManager(gridLayoutManager);
        shoppingRecyclerView.setHasFixedSize(true);
        shoppingRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //Firebase
        firebaseAuth = FirebaseAuth.getInstance();

        productoDTOList = new ArrayList<>();

        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertarCompra();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseUser = firebaseAuth.getCurrentUser();

        ConsultarProductosTodos();
    }

    private void ConsultarProductosTodos(){
        try{
            ProductoDTO productoDTO = new ProductoDTO();
            productoDTO.setUidUsuario(firebaseUser.getUid());

            IProductLogic iProductLogic = new ProductoLogic();
            iProductLogic.consultarProductosXusuario(this, productoDTO);

        }catch (Exception ex){
            Log.e("Error", "Error al consultar los productos " + ex.getMessage());
        }
    }

    private void cargarDatosProductos(List<ProductoDTO> productolist){
        adaptador = new AdaptadorProductBuy(getActivity(), productolist, this);
        shoppingRecyclerView.setAdapter(adaptador);

        actualizarValores(productolist);
    }

    public void actualizarValores(List<ProductoDTO> productolist){
        double valor = 0;
        int cantidad = 0;

        productoDTOList = productolist;

        if(productolist != null){
            for(int i = 0; i< productolist.size(); i++){
                valor += Double.parseDouble(productolist.get(i).getPrecio());
            }

            cantidad = productolist.size();
        }

        txtTotal.setText(String.format("%.2f", valor));
        txtCantidad.setText(String.valueOf(cantidad));

        if(valor == 0){
            btnComprar.setEnabled(false);
        }
    }

    private void mostrarMensaje(String mensaje){
        if(!TextUtils.isEmpty(mensaje)){
            Toast.makeText(AppContext.getContext(), mensaje, Toast.LENGTH_SHORT).show();
        }
    }

    private void InsertarCompra(){
        try{
            IProductLogic iProductLogic = new ProductoLogic();

            for (int i = 0; i < productoDTOList.size(); i++){
                ProductoDTO productoDTO = new ProductoDTO();
                productoDTO = productoDTOList.get(i);

                iProductLogic.insertarCompra(this, productoDTO);
            }

            ConsultarProductosTodos();

        }catch (Exception ex){
            mostrarMensaje("");
        }
    }

    @Override
    public void actualizarVista(DataBaseDTO dataBaseDTO) {
        String mensaje = "";

        if(dataBaseDTO.getOperacion().equals("getxUser")){
            if(dataBaseDTO.getlEntidades() != null){
                cargarDatosProductos((List<ProductoDTO>) dataBaseDTO.getlEntidades());

                if(dataBaseDTO.getlEntidades().size() <= 0){
                    btnComprar.setEnabled(false);
                    mensaje = "No hay productos en el carrito";
                }
            }
        }else if(dataBaseDTO.getOperacion().equals("delete")){
            if(dataBaseDTO.getlEntidades() != null){
                cargarDatosProductos((List<ProductoDTO>) dataBaseDTO.getlEntidades());

                if(dataBaseDTO.getlEntidades().size() <= 0){
                    btnComprar.setEnabled(false);
                    actualizarValores((List<ProductoDTO>) dataBaseDTO.getlEntidades());
                    mensaje = "No hay productos en el carrito";
                }
            }
        }

        mostrarMensaje(mensaje);
    }

    @Override
    public void notificar(DataBaseDTO dataBaseDTO) {
        Log.i("Info","Notificar");
        mostrarMensaje(dataBaseDTO.getException().getMessage());
    }
}
