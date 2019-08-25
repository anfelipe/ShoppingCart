package co.edu.univalle.shoppingcart.dominio;

import android.os.AsyncTask;
import android.util.Log;

import co.edu.univalle.shoppingcart.accesodatos.ProductDAO;
import co.edu.univalle.shoppingcart.dto.DataBaseDTO;
import co.edu.univalle.shoppingcart.dto.ProductoDTO;

public class ProcesadorSegundoPlano extends AsyncTask<DataBaseDTO, Integer, DataBaseDTO> {

    @Override
    protected DataBaseDTO doInBackground(DataBaseDTO... dataBaseDTOS) {

        try{
            ProductDAO productDAO = dataBaseDTOS[0].getGenericDAO()
                    instanceof ProductDAO ? (ProductDAO) dataBaseDTOS[0].getGenericDAO(): null;

            if (dataBaseDTOS[0].getOperacion().equalsIgnoreCase("find-all")){
                if (productDAO != null){
                    dataBaseDTOS[0].setlEntidades(productDAO.ConsultarProductoTodos());
                }
            }else if(dataBaseDTOS[0].getOperacion().equalsIgnoreCase("add")){
                if (productDAO != null){
                    ProductoDTO productoDTO = (ProductoDTO) dataBaseDTOS[0].getEntidad();
                    if(productDAO.ConsultarProductoXid(productoDTO.getIdProducto()) != null){
                        Log.i("Info", "Existe");
                        dataBaseDTOS[0].setException(new Exception("Producto ya se agregó al carrito"));
                    }else{
                        Log.i("info-Nuevo", dataBaseDTOS[0].getEntidad().toString());
                        productDAO.Crear((ProductoDTO) dataBaseDTOS[0].getEntidad());
                    }
                }
            }else if(dataBaseDTOS[0].getOperacion().equalsIgnoreCase("getxUser")){
                if (productDAO != null) {
                    ProductoDTO productoDTO = (ProductoDTO) dataBaseDTOS[0].getEntidad();
                    dataBaseDTOS[0].setlEntidades(productDAO.
                            ConsultarProductoXusuario(productoDTO.getUidUsuario()));
                }
            }else if(dataBaseDTOS[0].getOperacion().equalsIgnoreCase("delete")){
                if (productDAO != null){
                    Log.i("info", dataBaseDTOS[0].getEntidad().toString());
                    productDAO.Eliminar((ProductoDTO) dataBaseDTOS[0].getEntidad());
                }
            }

        }catch (Exception ex){
            dataBaseDTOS[0].setException(ex);
        }

        return dataBaseDTOS[0];
    }

    @Override
    protected void onPostExecute(DataBaseDTO dataBaseDTO) {
        if (dataBaseDTO.getException() != null){
            Log.i("Info", "Excepcion: " + dataBaseDTO.getException());
            dataBaseDTO.getGenericPresenter().notificar(dataBaseDTO);
        }else{
            Log.i("Info", "Actualizar");
            dataBaseDTO.getGenericPresenter().actualizarVista(dataBaseDTO);
        }
    }
}
