package co.edu.univalle.shoppingcart.adaptadores;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import co.edu.univalle.shoppingcart.R;

public class ViewHolderProductBuy extends RecyclerView.ViewHolder {

    public ImageView produceImage;
    public TextView productName;
    public TextView productPrice;
    public ImageButton deleteProduct;

    public ViewHolderProductBuy(View itemView) {
        super(itemView);
        produceImage =(ImageView)itemView.findViewById(R.id.product_image_buy);
        productName = (TextView)itemView.findViewById(R.id.product_name_buy);
        productPrice = (TextView)itemView.findViewById(R.id.txt_product_price_buy);
        deleteProduct = (ImageButton) itemView.findViewById(R.id.img_product_delete);
    }
}
