package es.sendit2us.wastetracker.server.facade;

import java.util.ArrayList;
import java.util.List;

import es.sendit2us.wastetracker.server.model.Address;
import es.sendit2us.wastetracker.server.model.PickupRequestDetailEntity;
import es.sendit2us.wastetracker.server.model.PickupRequestEntity;

public class VOFactory {

	public static PickupHeader newPickupHeader(PickupRequestEntity request) {
		PickupHeader vo = new PickupHeader();
		
		Address addr = request.getCustomer().getAddress();
		vo.setAddress(addr.getPhone1() + ", " + addr.getAddress() + " " + addr.getZipCode() + " " + addr.getCity());			
		vo.setPickupDate(request.getPickupDate().getTime());
		vo.setId(request.getId());
		vo.setName(request.getCustomer().getCompany());
		vo.setCode(request.getCode());
		vo.setDeviceCode(request.getDeviceCode());
		return vo;
	}
	
	public static PickupHeader newPickupError(Integer id, String msg) {
		PickupHeader vo = new PickupHeader();
		vo.setId(id);
		vo.setMsgError(msg);
		return vo;
	}
	
	public static PickupDetail newPickupDetail(PickupRequestDetailEntity d) {
		PickupDetail detail = new PickupDetail();
		detail.setId(d.getId());
		detail.setState(PickupDetail.NORMAL);
		detail.setCategoryCode(d.getCategory().getCode());
		detail.setCategoryDesc(d.getCategory().getDescription());
		detail.setContainerCode(d.getContainer().getCode());
		detail.setContainerDescr(d.getContainer().getDescription());
		return detail;
	}
	
	public static PickupHeader[] pickupEntityToHeader(List<PickupRequestEntity> requests) {
		PickupHeader[] res = new PickupHeader[requests.size()];
		
		int i = 0;
		for(PickupRequestEntity p: requests) {
			PickupHeader vo = VOFactory.newPickupHeader(p);
			res[i++] = vo;			
		}

		return res;		
	}
	
	public static List<PickupDetail> retrieveDetail(PickupRequestEntity pickup) {
		List<PickupDetail> det = new ArrayList<PickupDetail>();
		for(PickupRequestDetailEntity d: pickup.getDetail()) {
			PickupDetail detail = VOFactory.newPickupDetail(d);
			det.add(detail);
		}
		return det;
	}

}
