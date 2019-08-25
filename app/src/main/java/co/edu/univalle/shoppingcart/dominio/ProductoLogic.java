package co.edu.univalle.shoppingcart.dominio;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.edu.univalle.shoppingcart.accesodatos.ConexionDB;
import co.edu.univalle.shoppingcart.accesodatos.IUsuarioDAO;
import co.edu.univalle.shoppingcart.accesodatos.UsuarioDAO;
import co.edu.univalle.shoppingcart.dto.DataBaseDTO;
import co.edu.univalle.shoppingcart.dto.ProductoDTO;
import co.edu.univalle.shoppingcart.enumeraciones.EnumUrl;
import co.edu.univalle.shoppingcart.presentacion.IGenericPresenter;

public class ProductoLogic implements IProductLogic {

    private static final String URL_PRODUCTOS_CATEGORIA;

    static {
        URL_PRODUCTOS_CATEGORIA = EnumUrl.URL_BASE.getValor() +
                EnumUrl.URL_PRODUCTS_LIST.getValor();
    }

    @Override
    public List<ProductoDTO> ConsultarProductosCategoria(IGenericPresenter iGenericPresenter,
                                                         String uidUsuario) throws Exception {

        List<ProductoDTO> lstProducto = new ArrayList<>();
        DataBaseDTO<ProductoDTO> dataBaseDTO = new DataBaseDTO<ProductoDTO>();
        dataBaseDTO.setGenericPresenter(iGenericPresenter);
        dataBaseDTO.setParametros(URL_PRODUCTOS_CATEGORIA);

        AccesoConexionRestLogic restLogic = new AccesoConexionRestLogic();
        //String respuesta = restLogic.execute(URL_PRODUCTOS_CATEGORIA).get();
        DataBaseDTO dto = restLogic.execute(dataBaseDTO).get();
        String respuesta = dto.getRespuesta();

        if(respuesta == null && respuesta.equals("")){
            throw new Exception("Los prodcutos no se cargaron correctamente");
        }

        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(respuesta);

        if(jsonElement.isJsonObject()){
            JSONObject jsonObject = new JSONObject(respuesta);
            JSONArray catalogProductos = jsonObject.getJSONArray("CatalogProducts");

            Log.i("INFO-Catalogproducts", catalogProductos.toString());

            for (int i = 0; i < catalogProductos.length(); i++){
                ProductoDTO productoDTO = new ProductoDTO();
                JSONObject jsonObjCatalogP = catalogProductos.getJSONObject(i);

                Log.i("Info-json", jsonObjCatalogP.toString());

                productoDTO.setUidUsuario(uidUsuario);
                productoDTO.setCodigo(jsonObjCatalogP.getString("BrandCode"));
                productoDTO.setIdProducto(jsonObjCatalogP.getString("ProductId"));
                productoDTO.setNombre(jsonObjCatalogP.getString("DisplayName"));
                productoDTO.setPrecio(jsonObjCatalogP.getString("ListPrice"));
                productoDTO.setSrcImagen(EnumUrl.URL_IMAGES_1_FRONT.getValor() +
                        jsonObjCatalogP.getString("ImageFilename"));
                productoDTO.setDescripcion(jsonObjCatalogP.getString("Description"));

                Log.i("Info-Product", productoDTO.toString());

                lstProducto.add(productoDTO);
            }
        }

        return lstProducto;
    }

    @Override
    public void ConsultarProductosCategoria1(IGenericPresenter iGenericPresenter, String uidUsuario) throws Exception {
        DataBaseDTO<ProductoDTO> dataBaseDTO = new DataBaseDTO<ProductoDTO>();
        dataBaseDTO.setGenericPresenter(iGenericPresenter);
        dataBaseDTO.setParametros(URL_PRODUCTOS_CATEGORIA);
        dataBaseDTO.setOperacion("all");
        AccesoConexionRestLogic restLogic = new AccesoConexionRestLogic();
        restLogic.execute(dataBaseDTO).get();
    }

    @Override
    public List<ProductoDTO> ListaProductosInicio(String respuesta, String uidUsuario) throws Exception {
        List<ProductoDTO> lstProducto = new ArrayList<>();

        if(respuesta == null && respuesta.equals("")){
            throw new Exception("Los prodcutos no se cargaron correctamente");
        }

        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(respuesta);

        if(jsonElement.isJsonObject()){
            JSONObject jsonObject = new JSONObject(respuesta);
            JSONArray catalogProductos = jsonObject.getJSONArray("CatalogProducts");

            Log.i("INFO-Catalogproducts", catalogProductos.toString());

            for (int i = 0; i < catalogProductos.length(); i++){
                ProductoDTO productoDTO = new ProductoDTO();
                JSONObject jsonObjCatalogP = catalogProductos.getJSONObject(i);

                Log.i("Info-json", jsonObjCatalogP.toString());

                productoDTO.setUidUsuario(uidUsuario);
                productoDTO.setCodigo(jsonObjCatalogP.getString("BrandCode"));
                productoDTO.setIdProducto(jsonObjCatalogP.getString("ProductId"));
                productoDTO.setNombre(jsonObjCatalogP.getString("DisplayName"));
                productoDTO.setPrecio(jsonObjCatalogP.getString("ListPrice"));
                productoDTO.setSrcImagen(EnumUrl.URL_IMAGES_1_FRONT.getValor() +
                        jsonObjCatalogP.getString("ImageFilename"));
                productoDTO.setDescripcion(jsonObjCatalogP.getString("Description"));

                Log.i("Info-Product", productoDTO.toString());

                lstProducto.add(productoDTO);
            }
        }

        return lstProducto;
    }

    @Override
    public void agegarProducto(IGenericPresenter iGenericPresenter, ProductoDTO productoDTO)
            throws Exception {
        ProcesadorSegundoPlano segundoPlano = new ProcesadorSegundoPlano();
        DataBaseDTO<ProductoDTO> dataBaseDTO = new DataBaseDTO<ProductoDTO>();

        dataBaseDTO.setOperacion("add");
        dataBaseDTO.setEntidad(productoDTO);
        dataBaseDTO.setGenericDAO(ConexionDB.getInstance().getProductDAO());
        dataBaseDTO.setGenericPresenter(iGenericPresenter);

        segundoPlano.execute(dataBaseDTO);
    }

    @Override
    public void eliminarProducto(IGenericPresenter iGenericPresenter, ProductoDTO productoDTO)
            throws Exception {
        ProcesadorSegundoPlano segundoPlano = new ProcesadorSegundoPlano();
        DataBaseDTO<ProductoDTO> dataBaseDTO = new DataBaseDTO<ProductoDTO>();

        dataBaseDTO.setOperacion("delete");
        dataBaseDTO.setEntidad(productoDTO);
        dataBaseDTO.setGenericDAO(ConexionDB.getInstance().getProductDAO());
        dataBaseDTO.setGenericPresenter(iGenericPresenter);

        segundoPlano.execute(dataBaseDTO);
    }

    @Override
    public List<ProductoDTO> consultarProductosTodos(IGenericPresenter iGenericPresenter,
                                                     ProductoDTO productoDTO) throws Exception {

        ProcesadorSegundoPlano segundoPlano = new ProcesadorSegundoPlano();
        DataBaseDTO<ProductoDTO> dataBaseDTO = new DataBaseDTO<ProductoDTO>();

        dataBaseDTO.setOperacion("find-all");
        dataBaseDTO.setEntidad(productoDTO);
        dataBaseDTO.setGenericDAO(ConexionDB.getInstance().getProductDAO());
        dataBaseDTO.setGenericPresenter(iGenericPresenter);

        segundoPlano.execute(dataBaseDTO);

        return dataBaseDTO.getlEntidades();
    }

    @Override
    public List<ProductoDTO> consultarProductosXusuario(IGenericPresenter iGenericPresenter,
                                                        ProductoDTO productoDTO) throws Exception {
        ProcesadorSegundoPlano segundoPlano = new ProcesadorSegundoPlano();
        DataBaseDTO<ProductoDTO> dataBaseDTO = new DataBaseDTO<ProductoDTO>();

        dataBaseDTO.setOperacion("getxUser");
        dataBaseDTO.setEntidad(productoDTO);
        dataBaseDTO.setGenericDAO(ConexionDB.getInstance().getProductDAO());
        dataBaseDTO.setGenericPresenter(iGenericPresenter);

        segundoPlano.execute(dataBaseDTO);

        return dataBaseDTO.getlEntidades();
    }

    @Override
    public void insertarCompra(IGenericPresenter iGenericPresenter,
                               ProductoDTO productoDTO) throws Exception {
        try{
            IUsuarioDAO iUsuarioDAO = new UsuarioDAO();
            iUsuarioDAO.insertarCompra(productoDTO);

            eliminarProducto(iGenericPresenter, productoDTO);
        }catch (Exception ex){
            throw ex;
        }
    }
}
