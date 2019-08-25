package co.edu.univalle.shoppingcart.adaptadores;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import co.edu.univalle.shoppingcart.R;

public class ViewHolderShoppingCart extends RecyclerView.ViewHolder {

    public ImageView produceImage;
    public TextView productName;
    public ImageButton productCar;
    public TextView productPrice;

    public ViewHolderShoppingCart(View itemView) {
        super(itemView);
        produceImage =(ImageView)itemView.findViewById(R.id.product_image);
        productName = (TextView)itemView.findViewById(R.id.product_name);
        productPrice = (TextView)itemView.findViewById(R.id.txt_product_price);
        productCar = (ImageButton) itemView.findViewById(R.id.img_product_cart);
    }

}
