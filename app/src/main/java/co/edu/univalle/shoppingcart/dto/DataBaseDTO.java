package co.edu.univalle.shoppingcart.dto;

import java.util.List;

import co.edu.univalle.shoppingcart.accesodatos.GenericDAO;
import co.edu.univalle.shoppingcart.presentacion.IGenericPresenter;

public class DataBaseDTO <T> {

    private T entidad;
    private GenericDAO genericDAO;
    private String operacion;
    private String parametros;
    private String respuesta;
    private List<T> lEntidades;
    private Exception exception;
    private IGenericPresenter genericPresenter;

    public T getEntidad() {
        return entidad;
    }

    public void setEntidad(T entidad) {
        this.entidad = entidad;
    }

    public GenericDAO getGenericDAO() {
        return genericDAO;
    }

    public void setGenericDAO(GenericDAO genericDAO) {
        this.genericDAO = genericDAO;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public List<T> getlEntidades() {
        return lEntidades;
    }

    public void setlEntidades(List<T> lEntidades) {
        this.lEntidades = lEntidades;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public IGenericPresenter getGenericPresenter() {
        return genericPresenter;
    }

    public void setGenericPresenter(IGenericPresenter genericPresenter) {
        this.genericPresenter = genericPresenter;
    }

    public String getParametros() {
        return parametros;
    }

    public void setParametros(String parametros) {
        this.parametros = parametros;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    @Override
    public String toString() {
        return "DataBaseDTO{" +
                "entidad=" + entidad +
                ", genericDAO=" + genericDAO +
                ", operacion='" + operacion + '\'' +
                ", lEntidades=" + lEntidades +
                ", exception=" + exception +
                ", genericPresenter=" + genericPresenter +
                '}';
    }
}
