package co.edu.univalle.shoppingcart.accesodatos;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import co.edu.univalle.shoppingcart.dto.ProductoDTO;

public class UsuarioDAO implements IUsuarioDAO {

    @Override
    public void insertarCompra(ProductoDTO productoDTO) throws Exception {
        try{
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.
                    getReference("UsuarioShoppingCart");
            DatabaseReference referenceChild = databaseReference.child(productoDTO.getUidUsuario());
            DatabaseReference referenceProducto = referenceChild.child("Productos");
            referenceProducto.child(productoDTO.getIdProducto()).setValue(productoDTO);

        }catch (Exception ex){
            throw new Exception("El registro no se almacenó correctamente: " + ex.getMessage());
        }
    }
}
