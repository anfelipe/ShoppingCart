package co.edu.univalle.shoppingcart.enumeraciones;

public enum EnumUrl {
    URL_BASE("https://apidojo-forever21-v1.p.rapidapi.com/"),
    URL_CATEGORIAS("categories/list"),
    URL_PRODUCTS_LIST("products/list"),
    URL_PRODUCTS_DETAIL("products/detail"),
    URL_PRODUCTS_SEARCH("products/search"),
    URL_IMAGES_1_FRONT("https://www.forever21.com/images/1_front_750/"),
    URL_IMAGES_2_SIDE("https://www.forever21.com/images/2_side_750/"),
    URL_IMAGES_3_BACK("https://www.forever21.com/images/3_back_750/"),
    URL_IMAGES_4_FULL("https://www.forever21.com/images/4_full_750/"),
    URL_IMAGES_5_DETAIL("https://www.forever21.com/images/5_detail_750/"),
    HOST("apidojo-forever21-v1.p.rapidapi.com");

    public String valor;

    private EnumUrl(String valor){
        this.valor = valor;
    }

    public String getValor(){
        return valor;
    }
}
