package app.shopping.forevermyangle.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.appyvet.rangebar.RangeBar;

import org.json.JSONArray;
import org.json.JSONObject;

import app.shopping.forevermyangle.R;
import app.shopping.forevermyangle.adapter.listviewadapter.ProductListViewAdapter;
import app.shopping.forevermyangle.network.callback.NetworkCallbackListener;
import app.shopping.forevermyangle.network.handler.NetworkHandler;
import app.shopping.forevermyangle.parser.products.ProductParser;
import app.shopping.forevermyangle.utils.GlobalData;
import app.shopping.forevermyangle.utils.Network;
import app.shopping.forevermyangle.view.FMAProgessDialog;

/**
 * @class SearchProductActivity
 * @desc Activity to show the searched products.
 */
public class SearchProductActivity extends FragmentActivity implements AdapterView.OnItemClickListener, NetworkCallbackListener, AbsListView.OnScrollListener, View.OnClickListener {

    /**
     * Class private data members.
     */
    private GridView mProductGridList = null;
    private SearchView mProductSearchView = null;
    private ProductListViewAdapter mAdapter = null;
    private NetworkHandler mNetworkHandler = new NetworkHandler();
    private LinearLayout mLayoutTabSort, mLayoutTabFilter;
    private FMAProgessDialog fmaProgessDialog = null;

    /**
     * Product filtering data and flags.
     */
    private boolean mFlagRefresh = false;
    private int mPageNumber = 1;
    private final int sPRODUCTS_PERPAGE = 10;
    private String mStrSrchString = "";
    private String mStrSrchOrder = "";
    private String mStrSrchOrderBy = "";
    private String mStrSrchStatus = "";
    private String mStrSrchInStock = "";//"&in_stock=true";
    private String mStrSrchMaxPrice = "";
    private String mStrSrchMinPrice = "";
    private String mStrSrchOnSale = "";
    private String mStrSrchCategotyId = GlobalData.srch_category_id;

    /**
     * {@link android.support.v7.app.AppCompatActivity} Override methods.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        fmaProgessDialog = new FMAProgessDialog(this);

        mLayoutTabSort = (LinearLayout) findViewById(R.id.tab_filter);
        mLayoutTabFilter = (LinearLayout) findViewById(R.id.tab_sort);

        mLayoutTabSort.setOnClickListener(this);
        mLayoutTabFilter.setOnClickListener(this);

        mProductGridList = (GridView) findViewById(R.id.product_listview);
        mProductGridList.setNumColumns(2);
        mProductGridList.setOnScrollListener(this);

        mAdapter = new ProductListViewAdapter(this, R.layout.item_gridview_product, GlobalData.TotalProducts);
        mProductGridList.setAdapter(mAdapter);
        mProductGridList.setOnItemClickListener(this);

        mProductSearchView = (SearchView) findViewById(R.id.prod_srch_query);
        mProductSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(SearchProductActivity.this.getCurrentFocus().getWindowToken(), 0);
                SearchProductActivity.this.mStrSrchString = "&search=" + query.trim();
                SearchProductActivity.this.mPageNumber = 1;
                GlobalData.TotalProducts.clear();
                SearchProductActivity.this.mAdapter.notifyDataSetChanged();
                mFlagRefresh = false;
                mProductGridList.setOnScrollListener(SearchProductActivity.this);
                Toast.makeText(SearchProductActivity.this, "Searching...", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GlobalData.TotalProducts.clear();
    }

    /**
     * {@link android.widget.AdapterView.OnItemClickListener} callback methods implemented.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        startActivity(new Intent(this, ProductDescriptionActivity.class));
    }

    /**
     * {@link NetworkCallbackListener} interface callback methods.
     */
    @Override
    public void networkSuccessResponse(int requestCode, JSONObject rawObject, JSONArray rawArray) {

        fmaProgessDialog.hide();
        switch (requestCode) {

            case 1:             // Product list response.

                updateProductList(rawArray);
                break;
        }
        Toast.makeText(this, requestCode + " success from response from http handler.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void networkFailResponse(int requestCode, String message) {

        fmaProgessDialog.hide();
        switch (requestCode) {
            case 1:

                mFlagRefresh = false;
                break;
        }
        Toast.makeText(this, "" + requestCode + ":Fail", Toast.LENGTH_SHORT).show();
    }

    /**
     * {@link android.widget.AbsListView.OnScrollListener} event callback.
     */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        Snackbar.make(view, "first:" + firstVisibleItem + ", visible:" + visibleItemCount + ", total:" + totalItemCount, Snackbar.LENGTH_LONG).show();

        if (((firstVisibleItem + visibleItemCount) == totalItemCount) && (mFlagRefresh == false)) {

            mFlagRefresh = true;
            getProductList();
        }
    }

    /**
     * @method: getProductList
     * @desc Method to get the Product list from web http.
     */
    private void getProductList() {

        if (mPageNumber == 1) {         // Show progress dialog in case of first page loading only.
            fmaProgessDialog.show();
        }
        String url = Network.URL_GET_ALL_PRODUCTS + "?per_page=" + sPRODUCTS_PERPAGE + "&page=" + mPageNumber;
        url = url + "" + mStrSrchString + mStrSrchOrder + mStrSrchOrderBy + mStrSrchStatus + mStrSrchInStock + mStrSrchMaxPrice + mStrSrchMinPrice + mStrSrchOnSale + mStrSrchCategotyId;
        mNetworkHandler.httpCreate(1, this, this, new JSONObject(), url, NetworkHandler.RESPONSE_ARRAY);
        mNetworkHandler.executeGet();
    }

    /**
     * @param raw {@link JSONArray} list of all products.
     * @method updateProductList
     * @desc Method to update the product list on UIScreen.
     */
    public void updateProductList(JSONArray raw) {

        ProductParser productParser = new ProductParser();
        int next = productParser.parseProducts(raw);
        mPageNumber++;

        if (next < sPRODUCTS_PERPAGE) {      // remove listener, after while no more data is pending.
            mProductGridList.setOnScrollListener(null);
        }
        mAdapter.notifyDataSetChanged();
        mFlagRefresh = false;
    }

    /**
     * {@link android.view.View.OnClickListener} listener callback method.
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tab_sort:

                getSortedProducts();
                break;
            case R.id.tab_filter:

                getFilteredProducts();
                break;
        }
    }

    /**
     * @method getSortedProducts
     * @desc Method to show the sorting option in alert dialog.
     */
    private void getSortedProducts() {

        final BottomSheetDialog b = new BottomSheetDialog(this);
        View view = (View) getLayoutInflater().inflate(R.layout.custom_search, null);
        RadioGroup r = (RadioGroup) view.findViewById(R.id.sort_radiogroup);
        r.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                b.hide();
                switch (checkedId) {
                    case R.id.option_1:

                        break;
                    case R.id.option_2:

                        break;
                    case R.id.option_3:

                        break;
                    case R.id.option_4:

                        break;
                }
                GlobalData.TotalProducts.clear();
                mAdapter.notifyDataSetChanged();
                mPageNumber = 1;
                getProductList();
                Toast.makeText(SearchProductActivity.this, "Fetching", Toast.LENGTH_SHORT).show();
            }
        });
        b.setContentView(view);
        b.show();
    }

    /**
     * @method getFilteredProducts
     * @desc Method to show filtering options in alert dialog.
     */
    private void getFilteredProducts() {

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Filter");
        final View view = (View) getLayoutInflater().inflate(R.layout.custom_filter, null);

        final TextView txtMinRange = (TextView) view.findViewById(R.id.txt_min_range);
        final TextView txtMaxRange = (TextView) view.findViewById(R.id.txt_max_range);
        txtMinRange.setText("Min Price");
        txtMaxRange.setText("Max Price");

        final RangeBar priceRangeBar = (RangeBar) view.findViewById(R.id.price_rangebar);

        priceRangeBar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {

                txtMinRange.setText("Min Price: " + leftPinValue);
                txtMaxRange.setText("Max Price: " + rightPinValue);
                Snackbar.make(view, leftPinValue + ", " + rightPinValue, Snackbar.LENGTH_SHORT).show();
            }
        });

        alertDialog.setView(view);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Filter",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        mStrSrchMaxPrice = "&min_price=" + priceRangeBar.getLeftPinValue();
                        mStrSrchMinPrice = "&max_price=" + priceRangeBar.getRightPinValue();
                        GlobalData.TotalProducts.clear();
                        mAdapter.notifyDataSetChanged();
                        mPageNumber = 1;
                        getProductList();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                mStrSrchMaxPrice = "";
                mStrSrchMinPrice = "";
                GlobalData.TotalProducts.clear();
                mAdapter.notifyDataSetChanged();
                mPageNumber = 1;
                getProductList();
            }
        });
        alertDialog.show();
    }

}
