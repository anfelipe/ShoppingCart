package co.edu.univalle.shoppingcart.dto;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "products")
public class ProductoDTO {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "idProducto")
    private String idProducto;

    @ColumnInfo(name = "uidusuario")
    private String uidUsuario;

    @ColumnInfo(name = "codigo")
    private String codigo;

    @ColumnInfo(name = "nombre")
    private String nombre;

    @ColumnInfo(name = "srcImagen")
    private String srcImagen;

    @ColumnInfo(name = "precio")
    private String precio;

    @ColumnInfo(name = "descripcion")
    private String descripcion;

    public ProductoDTO() {
    }

    public ProductoDTO(String uidUsuario, String codigo, String nombre, String srcImagen,
                       String idProducto, String precio, String descripcion) {
        this.uidUsuario = uidUsuario;
        this.codigo = codigo;
        this.nombre = nombre;
        this.srcImagen = srcImagen;
        this.idProducto = idProducto;
        this.precio = precio;
        this.descripcion = descripcion;
    }

    public String getUidUsuario() {
        return uidUsuario;
    }

    public void setUidUsuario(String uidUsuario) {
        this.uidUsuario = uidUsuario;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSrcImagen() {
        return srcImagen;
    }

    public void setSrcImagen(String srcImagen) {
        this.srcImagen = srcImagen;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "ProductoDTO{" +
                "uidUsuario='" + uidUsuario + '\'' +
                ", codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", srcImagen='" + srcImagen + '\'' +
                ", idProducto='" + idProducto + '\'' +
                ", precio='" + precio + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
