package be.robinj.restobill;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import be.robinj.restobill.model.BillEntity;
import be.robinj.restobill.model.OrderEntity;
import be.robinj.restobill.model.ProductEntity;
import be.robinj.restobill.model.TableEntity;

/**
 * Created by robin on 07/11/15.
 */
public class API
{
	private String server;

	public API (String server)
	{
		this.server = server;
	}

	private void doRequest (String file, HashMap<String, String> parameters)
	{
		try
		{
			/*
			 * I'll just use the deprecated one... at least it works. Otherwise I'm
			 * not going to get anywhere today.
			 *

			HttpURLConnection conn = (HttpURLConnection) (new URL (this.server + file).openConnection ());
			conn.setDoOutput (true);
			conn.setRequestMethod ("POST");
			conn.setInstanceFollowRedirects (true);
			conn.setRequestProperty ("charset", "utf-8");
			conn.setRequestProperty ("Content-Type", "application/x-www-form-urlencoded");
			conn.setUseCaches (false);

			StringBuilder strbPost = new StringBuilder ();
			boolean passedFirst = false;
			for (String key : parameters.keySet ())
			{
				if (passedFirst)
					strbPost.append ("&");
				else
					passedFirst = true;

				strbPost.append (key)
					.append ("=")
					.append (parameters.get (key));
			}

			byte[] postData = new byte[0];
			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT)
				postData = strbPost.toString ().getBytes (StandardCharsets.UTF_8);
			conn.setRequestProperty ("Content-Length", String.valueOf (postData.length));

			DataOutputStream dos = new DataOutputStream (conn.getOutputStream ());
			dos.write (postData);
			*/

			HttpClient client = new DefaultHttpClient ();
			HttpPost post = new HttpPost (this.server + file);

			List<NameValuePair> nvp = new ArrayList<NameValuePair> (2);
			for (String key : parameters.keySet ())
				nvp.add (new BasicNameValuePair (key, parameters.get (key)));

			post.setEntity (new UrlEncodedFormEntity (nvp));

			client.execute (post);
		}
		catch (Exception ex)
		{
			ex.printStackTrace ();
		}
	}

	public void saveTable (TableEntity table)
	{
		HashMap<String, String> parameters = new HashMap<String, String> ();
		parameters.put ("id", String.valueOf (table.getId ()));
		parameters.put ("name", table.name);

		this.doRequest ("saveTable.php", parameters);
	}

	public void saveProduct (ProductEntity product)
	{
		HashMap<String, String> parameters = new HashMap<String, String> ();
		parameters.put ("id", String.valueOf (product.getId ()));
		parameters.put ("name", product.name);
		parameters.put ("price", new DecimalFormat ("0.00").format (product.price));
		parameters.put ("description", product.description);
		parameters.put ("available", product.available ? "1" : "0");

		this.doRequest ("saveProduct.php", parameters);
	}

	public void saveOrder (OrderEntity order)
	{
		HashMap<String, String> parameters = new HashMap<String, String> ();
		parameters.put ("id", String.valueOf (order.getId ()));
		parameters.put ("product_id", String.valueOf (order.productEntity));
		parameters.put ("bill_id", String.valueOf (order.billEntity));
		parameters.put ("amount", String.valueOf (order.amount));

		this.doRequest ("saveOrder.php", parameters);
	}

	public void saveBill (BillEntity bill)
	{
		HashMap<String, String> parameters = new HashMap<String, String> ();
		parameters.put ("id", String.valueOf (bill.getId ()));
		parameters.put ("table_id", String.valueOf (bill.tableEntity));
		parameters.put ("closed", bill.closed ? "1" : "0");

		this.doRequest ("saveBill.php", parameters);
	}
}
