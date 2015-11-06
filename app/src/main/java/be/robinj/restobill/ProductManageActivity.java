package be.robinj.restobill;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import java.util.HashSet;
import java.util.List;

import be.robinj.restobill.adapter.ProductAdapter;
import be.robinj.restobill.listener.ProductAddOnClickedListener;
import be.robinj.restobill.listener.ProductGridViewOnItemClickListener;
import be.robinj.restobill.listener.ProductSearchTextChangedListener;
import be.robinj.restobill.listener.ProductSubmitOnClickListener;
import be.robinj.restobill.model.ProductEntity;
import be.robinj.restobill.model.TableEntity;

/**
 * Created by Afaci on 05/11/15.
 */
public class ProductManageActivity
        extends AppCompatActivity
{
    private HashSet<Long> selected = new HashSet<Long> ();

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarManageProduct);
        setSupportActionBar (toolbar);
        getSupportActionBar ().setDisplayHomeAsUpEnabled (true);

        FloatingActionButton btnAddProduct = (FloatingActionButton) this.findViewById (R.id.fabManageProduct);
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductAddOnClickedListener pd = new ProductAddOnClickedListener(ProductManageActivity.this);
                pd.show();
            }
        });

        GridView gvProducts = (GridView) this.findViewById (R.id.gvManageProducts);
        //EditText etProductSearch = (EditText) this.findViewById (R.id.etProductSearch);

        //long billId = this.getIntent ().getLongExtra ("billId", -1);

        //if (billId == -1) // Shouldn't happen //
        //    this.finish ();

		/*(new ProductEntity ("Pizza", 10F, "Dough with tomato sauce and other stuff...")).save ();
		(new ProductEntity ("Gnocci", 8.5F, "Some kind of pasta thing")).save ();
		(new ProductEntity ("Bread", 1F)).save ();
		(new ProductEntity ("Water", 1.5F, "Fresh from the tap")).save ();
		(new ProductEntity ("Cookie", 2F)).save ();*/

        List<ProductEntity> products = ProductEntity.find (ProductEntity.class, null, new String[] {}, null, "name", null);
        //btnSubmitProducts.setOnClickListener (new ProductSubmitOnClickListener(this, this.selected, billId));
        gvProducts.setAdapter(new ProductAdapter(this, products, this.selected));
        gvProducts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder adb = new AlertDialog.Builder(ProductManageActivity.this);
                adb.setTitle("Delete Product");
                final long prodId = (long) view.getTag();
                adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ProductEntity product = ProductEntity.findById(ProductEntity.class, prodId);
                        product.delete();
                        refreshProducts();
                    }
                });
                adb.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                adb.show();
                return true;
            }
        });
        //gvProducts.setOnItemClickListener(new ProductGridViewOnItemClickListener(this.selected, btnSubmitProducts));
        //etProductSearch.addTextChangedListener (new ProductSearchTextChangedListener(products, (ProductAdapter) gvProducts.getAdapter ()));
    }

    public void refreshProducts ()
    {

        GridView gvProducts = (GridView) this.findViewById (R.id.gvManageProducts);
        ProductAdapter adapter = (ProductAdapter) gvProducts.getAdapter ();

        adapter.clear ();
        adapter.addAll(ProductEntity.listAll(ProductEntity.class));
        adapter.notifyDataSetChanged();
    }
}
