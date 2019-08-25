package co.edu.univalle.shoppingcart.adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

import co.edu.univalle.shoppingcart.R;
import co.edu.univalle.shoppingcart.dominio.IProductLogic;
import co.edu.univalle.shoppingcart.dominio.ProductoLogic;
import co.edu.univalle.shoppingcart.dto.DataBaseDTO;
import co.edu.univalle.shoppingcart.dto.ProductoDTO;
import co.edu.univalle.shoppingcart.presentacion.AppContext;
import co.edu.univalle.shoppingcart.presentacion.IGenericPresenter;
import co.edu.univalle.shoppingcart.utilidades.AsyncTaskCargarImagen;

public class AdaptadorShoppingCart extends RecyclerView.Adapter<ViewHolderShoppingCart>
        implements IGenericPresenter {

    private Context context;
    private List<ProductoDTO> lstProductos;

    public AdaptadorShoppingCart(Context context, List<ProductoDTO> lstProductos){
        this.context = context;
        this.lstProductos = lstProductos;
    }

    @NonNull
    @Override
    public ViewHolderShoppingCart onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.fragment_listaproductos, viewGroup, false);
        ViewHolderShoppingCart holderShoppingCart = new ViewHolderShoppingCart(view);

        return holderShoppingCart;
    }

    @Override
    public void onBindViewHolder(ViewHolderShoppingCart viewHolderShoppingCart, int i) {
        final ProductoDTO productoDTO = lstProductos.get(i);

        viewHolderShoppingCart.productName.setText(productoDTO.getNombre());
        viewHolderShoppingCart.productPrice.setText("$" + productoDTO.getPrecio());
        new AsyncTaskCargarImagen(viewHolderShoppingCart.produceImage).execute(productoDTO.getSrcImagen());

        viewHolderShoppingCart.productCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarProducto(productoDTO);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstProductos.size();
    }

    private void agregarProducto(ProductoDTO productoDTO){
        try{
            IProductLogic iProductLogic = new ProductoLogic();
            iProductLogic.agegarProducto(this, productoDTO);
        }catch (Exception ex){
            Toast.makeText(context, "No se pudo agregar al carrito",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void actualizarVista(DataBaseDTO dataBaseDTO) {
        String mensaje = "";

        if(dataBaseDTO.getOperacion().equals("add")){
            mensaje = "Producto agregado al carrito";
        }else if(dataBaseDTO.getOperacion().equals("find-all")){
            Log.i("info-size",  dataBaseDTO.getlEntidades().size() + "");

            for (int i = 0; i < dataBaseDTO.getlEntidades().size(); i++){
                Log.i("Info-todos", dataBaseDTO.getEntidad().toString());
            }
        }

        if(!TextUtils.isEmpty(mensaje)){
            Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void notificar(DataBaseDTO dataBaseDTO) {
        Log.i("Info","Notificar");
        Toast.makeText(context, "Producto ya se agregó al carrito", Toast.LENGTH_SHORT).show();
    }
}
