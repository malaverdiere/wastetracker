package es.sendit2us.wastetracker.server.tests;

import java.sql.SQLException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.BeforeClass;

import com.core.agora.utils.DataImporter;

import es.sendit2us.wastetracker.server.HibernateProxy;

public class WasteTrackerTests extends TestCase {
	private static InitialContext context = null;

	public static final String DATABASE = "WTTests";
	
	@BeforeClass
	public static void setUpClass() throws NamingException {
	}
	
	@Before
	public void setUp() throws Exception {
		if(context == null) {
			System.out.println("***********************************************************");
			Properties props = new Properties();
			props.setProperty(Context.INITIAL_CONTEXT_FACTORY,
					"org.apache.openejb.client.LocalInitialContextFactory");

			/*
			 * props.put("WasteTracker", "new://Resource?type=DataSource");
			 * props.put("WasteTracker.JdbcDriver", "org.hsqldb.jdbcDriver");
			 * props.put("WasteTracker.JdbcUrl", "jdbc:hsqldb:mem:WasteTracker");
			 */

			props.put("WasteTracker", "new://Resource?type=DataSource");
			props.put("WasteTracker.JdbcDriver", "com.mysql.jdbc.Driver");
			props.put("WasteTracker.JdbcUrl", "jdbc:mysql://localhost:3306/" + DATABASE);
			props.put("WasteTracker.UserName", "root");
			props.put("WasteTracker.Password", "coreroot");

			// props.put(Context.SECURITY_PRINCIPAL, "guest");
			// props.put(Context.SECURITY_CREDENTIALS, "guest");

			context = new InitialContext(props);					
		}

		importData();
		
		init();
	}

	public InitialContext getContext() {
		return context;
	}
	
	public Object lookup(String jndi) {
	
		try {
			return context.lookup(jndi);
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public HibernateProxy getJPA() {
	
		return (HibernateProxy) lookup("HibernateProxyImplLocal");
	}
	
	protected void init() {
		
	}
	
	private void importData() throws SQLException {

		DataImporter di = new DataImporter();
		di.connect("jdbc:mysql://localhost:3306/" + DATABASE, "root", "coreroot");
		di.truncateAll();
		di.importData("CUSTOMERS", "src/test/resources/data/CUSTOMERS.csv", "address_address, address_ccaa, address_city, address_country, address_zipCode, cnum, customerCode, company, name, address_phone1, address_phone2");
		di.importData("CAT_INCIDENCES", "src/test/resources/data/CAT_INCIDENCES.csv", "code, description");
		//di.importData("FAM_PRODUCTS", "src/test/resources/data/FAM_PRODUCTS.csv", "code, description");
		//di.importData("CAT_PRODUCTS", "src/test/resources/data/CAT_PRODUCTS.csv", "code, description, family");
		//di.importData("CAT_CONTAINERS", "src/test/resources/data/CAT_CONTAINERS.csv", "code, description");
		di.importData("PICKUPREQUESTS", "src/test/resources/data/PICKUPREQUESTS.csv", "code, customerAuthCode, deviceCode, pickupDate, customer_address_address, customer_address_ccaa, customer_address_city, customer_address_country, customer_address_zipCode, customer_cnum, customer_customerCode, customer_company, customer_name, customer_address_phone1, customer_address_phone2, deliverydate");
		di.importData("PICKUPREQUEST_DET", "src/test/resources/data/PICKUPREQUEST_DET.csv", "category_code, category_description, category_family_code, category_family_description, container_code, container_description, request_id, state");
		di.importData("WASTE_DESCRIPTIONS", "src/test/resources/data/WASTE_DESCRIPTIONS.csv", "code, description");
	}
}
