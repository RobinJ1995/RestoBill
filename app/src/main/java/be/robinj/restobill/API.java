package be.robinj.restobill;

import android.content.Context;
import android.content.Entity;
import android.util.Log;

import com.google.gson.Gson;
import com.orm.SugarRecord;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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

	public API (Context context)
	{
		this.server = context.getSharedPreferences ("prefs", Context.MODE_PRIVATE).getString ("server", "http://10.0.2.2:8000/");
	}

	private HttpResponse doRequest (String file, HashMap<String, String> parameters)
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

			return client.execute (post);
		}
		catch (Exception ex)
		{
			ex.printStackTrace ();

			return null;
		}
	}

	private String getIdsString (Set<Long> ids)
	{
		StringBuilder strbIds = new StringBuilder ();
		boolean passedFirst = false;

		for (long id : ids)
		{
			if (passedFirst)
				strbIds.append (",");
			else
				passedFirst = true;

			strbIds.append (String.valueOf (id));
		}

		return strbIds.toString ();
	}

	public void saveTable (TableEntity table)
	{
		HashMap<String, String> parameters = new HashMap<String, String> ();
		parameters.put ("id", String.valueOf (table.getId ()));
		parameters.put ("name", table.name);
		parameters.put ("syncId", String.valueOf (table.syncId));

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
		parameters.put ("syncId", String.valueOf (product.syncId));

		this.doRequest ("saveProduct.php", parameters);
	}

	public void saveOrder (OrderEntity order)
	{
		HashMap<String, String> parameters = new HashMap<String, String> ();
		parameters.put ("id", String.valueOf (order.getId ()));
		parameters.put ("product_id", String.valueOf (order.productEntity));
		parameters.put ("bill_id", String.valueOf (order.billEntity));
		parameters.put ("amount", String.valueOf (order.amount));
		parameters.put ("syncId", String.valueOf (order.syncId));

		this.doRequest ("saveOrder.php", parameters);
	}

	public void saveBill (BillEntity bill)
	{
		HashMap<String, String> parameters = new HashMap<String, String> ();
		parameters.put ("id", String.valueOf (bill.getId ()));
		parameters.put ("table_id", String.valueOf (bill.tableEntity));
		parameters.put ("closed", bill.closed ? "1" : "0");
		parameters.put ("syncId", String.valueOf (bill.syncId));

		this.doRequest ("saveBill.php", parameters);
	}

	public void startSyncThread ()
	{
		final API self = this;

		Runnable runnable = new Runnable ()
		{
			@Override
			public void run ()
			{
				while (true) // No time to figure out how to do proper scheduling //
				{
					try
					{
						self.syncTables ();
						self.syncProducts ();
						self.syncOrders ();
						self.syncBills ();

						Thread.sleep (2500);
					}
					catch (Exception ex)
					{
						ex.printStackTrace (); // The show must go on! //
					}
				}
			}
		};

		SingletonSyncThread thread = SingletonSyncThread.getInstance (runnable);

		if (! thread.isAlive ())
			thread.start ();
	}

	public void syncTables ()
	{
		try
		{
			HashMap<String, String> parameters = new HashMap<String, String> ();
			Set<Long> ids = new HashSet<Long> ();

			for (TableEntity table : TableEntity.listAll (TableEntity.class))
				ids.add (table.syncId);
			parameters.put ("ids", this.getIdsString (ids));

			HttpResponse response = this.doRequest ("syncTables.php", parameters);
			InputStreamReader in = new InputStreamReader (response.getEntity ().getContent ());
			Gson gson = new Gson ();
			TableEntity[] tables = gson.fromJson (in, TableEntity[].class);
			in.close ();

			for (TableEntity table : tables)
				table.save (false);

			HttpResponse response2 = this.doRequest ("offerTables.php", parameters);
			String strMissingIds = EntityUtils.toString (response2.getEntity ());
			for (String strId : strMissingIds.split (","))
			{
				if (! strId.isEmpty ())
				{
					long id = Long.parseLong (strId);
					List<TableEntity> list = TableEntity.find (TableEntity.class, "sync_id = ?", String.valueOf (id));

					if (list.size () > 0)
						list.get (0).save (true);
				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace ();
		}
	}

	public void syncProducts ()
	{
		try
		{
			HashMap<String, String> parameters = new HashMap<String, String> ();
			Set<Long> ids = new HashSet<Long> ();

			for (ProductEntity product : ProductEntity.listAll (ProductEntity.class))
				ids.add (product.syncId);
			parameters.put ("ids", this.getIdsString (ids));

			HttpResponse response = this.doRequest ("syncProducts.php", parameters);
			InputStreamReader in = new InputStreamReader (response.getEntity ().getContent ());
			Gson gson = new Gson ();
			ProductEntity[] products = gson.fromJson (in, ProductEntity[].class);
			in.close ();

			for (ProductEntity product : products)
				product.save (false);

			HttpResponse response2 = this.doRequest ("offerProducts.php", parameters);
			String strMissingIds = EntityUtils.toString (response2.getEntity ());
			for (String strId : strMissingIds.split (","))
			{
				if (! strId.isEmpty ())
				{
					long id = Long.parseLong (strId);
					List<ProductEntity> list = ProductEntity.find (ProductEntity.class, "sync_id = ?", String.valueOf (id));

					if (list.size () > 0)
						list.get (0).save (true);
				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace ();
		}
	}

	public void syncOrders ()
	{
		try
		{
			HashMap<String, String> parameters = new HashMap<String, String> ();
			Set<Long> ids = new HashSet<Long> ();

			for (OrderEntity order : OrderEntity.listAll (OrderEntity.class))
				ids.add (order.syncId);
			parameters.put ("ids", this.getIdsString (ids));

			HttpResponse response = this.doRequest ("syncOrders.php", parameters);
			InputStreamReader in = new InputStreamReader (response.getEntity ().getContent ());
			Gson gson = new Gson ();
			OrderEntity[] orders = gson.fromJson (in, OrderEntity[].class);
			in.close ();

			for (OrderEntity order : orders)
				order.save (false);

			HttpResponse response2 = this.doRequest ("offerOrders.php", parameters);
			String strMissingIds = EntityUtils.toString (response2.getEntity ());
			for (String strId : strMissingIds.split (","))
			{
				if (! strId.isEmpty ())
				{
					long id = Long.parseLong (strId);
					List<OrderEntity> list = ProductEntity.find (OrderEntity.class, "sync_id = ?", String.valueOf (id));

					if (list.size () > 0)
						list.get (0).save (true);
				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace ();
		}
	}

	public void syncBills ()
	{
		try
		{
			HashMap<String, String> parameters = new HashMap<String, String> ();
			Set<Long> ids = new HashSet<Long> ();

			for (BillEntity bill : BillEntity.listAll (BillEntity.class))
				ids.add (bill.syncId);
			parameters.put ("ids", this.getIdsString (ids));

			HttpResponse response = this.doRequest ("syncBills.php", parameters);
			InputStreamReader in = new InputStreamReader (response.getEntity ().getContent ());
			Gson gson = new Gson ();
			BillEntity[] bills = gson.fromJson (in, BillEntity[].class);
			in.close ();

			for (BillEntity bill : bills)
				bill.save (false);

			HttpResponse response2 = this.doRequest ("offerBills.php", parameters);
			String strMissingIds = EntityUtils.toString (response2.getEntity ());
			for (String strId : strMissingIds.split (","))
			{
				if (! strId.isEmpty ())
				{
					long id = Long.parseLong (strId);
					List<BillEntity> list = BillEntity.find (BillEntity.class, "sync_id = ?", String.valueOf (id));

					if (list.size () > 0)
						list.get (0).save (true);
				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace ();
		}
	}
}
