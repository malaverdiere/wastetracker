package es.sendit2us.wastetracker.server.tests;

import java.util.ArrayList;

import junit.framework.TestCase;

import com.google.gson.Gson;

import es.sendit2us.wastetracker.server.facade.PickupDetail;
import es.sendit2us.wastetracker.server.facade.PickupHeader;

public class TestJSon extends TestCase {

	public void testJson() {
		PickupHeader p = new PickupHeader();
		p.setCode("code");
		PickupDetail d1 = new PickupDetail();
		d1.setCategoryCode("c1");
		PickupDetail d2 = new PickupDetail();
		d2.setCategoryCode("c2");
		p.setDetail(new ArrayList<PickupDetail>());
		p.getDetail().add(d1);
		p.getDetail().add(d2);

		Gson gson = new Gson();
		String s = gson.toJson(p);
		System.out.println(s);
	}
}
