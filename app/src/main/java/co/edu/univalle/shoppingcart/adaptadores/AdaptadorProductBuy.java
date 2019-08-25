package co.edu.univalle.shoppingcart.adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import co.edu.univalle.shoppingcart.R;
import co.edu.univalle.shoppingcart.dominio.IProductLogic;
import co.edu.univalle.shoppingcart.dominio.ProductoLogic;
import co.edu.univalle.shoppingcart.dto.DataBaseDTO;
import co.edu.univalle.shoppingcart.dto.ProductoDTO;
import co.edu.univalle.shoppingcart.presentacion.AppContext;
import co.edu.univalle.shoppingcart.presentacion.FragmentProductCart;
import co.edu.univalle.shoppingcart.presentacion.IGenericPresenter;
import co.edu.univalle.shoppingcart.utilidades.AsyncTaskCargarImagen;

public class AdaptadorProductBuy extends RecyclerView.Adapter<ViewHolderProductBuy>
        implements IGenericPresenter {

    private Context context;
    private List<ProductoDTO> lstProductos;
    private int posicion;
    private FragmentProductCart productCart;

    public AdaptadorProductBuy(Context context, List<ProductoDTO> lstProductos,
                               FragmentProductCart fragmentProductCart){
        this.context = context;
        this.lstProductos = lstProductos;
        this.productCart = fragmentProductCart;
    }

    @NonNull
    @Override
    public ViewHolderProductBuy onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.fragment_productos_buy, viewGroup, false);
        ViewHolderProductBuy viewHolderProductBuy = new ViewHolderProductBuy(view);

        return viewHolderProductBuy;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderProductBuy viewHolderProductBuy, final int i) {
        final ProductoDTO productoDTO = lstProductos.get(i);

        viewHolderProductBuy.productName.setText(productoDTO.getNombre());
        viewHolderProductBuy.productPrice.setText("$" + productoDTO.getPrecio());
        new AsyncTaskCargarImagen(viewHolderProductBuy.produceImage).execute(productoDTO.getSrcImagen());

        viewHolderProductBuy.deleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                posicion = i;
                eliminarProducto(productoDTO);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstProductos.size();
    }

    private void eliminarProducto(ProductoDTO productoDTO){
        try{
            IProductLogic iProductLogic = new ProductoLogic();
            iProductLogic.eliminarProducto(this, productoDTO);
        }catch (Exception ex){
            Toast.makeText(context, "No se pudo agregar al carrito",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void actualizarVista(DataBaseDTO dataBaseDTO) {
        String mensaje = "";

        if(dataBaseDTO.getOperacion().equals("delete")){
            lstProductos.remove(posicion);
            this.notifyItemRemoved(posicion);

            productCart.actualizarValores(lstProductos);

            mensaje = "Producto eliminado del carro";
        }

        if(!TextUtils.isEmpty(mensaje)){
            Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void notificar(DataBaseDTO dataBaseDTO) {
        Log.i("Info","Notificar");
        Toast.makeText(context, "No se pudo quitar el producto del carro",
                Toast.LENGTH_SHORT).show();
    }
}
