// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JSR-172 Reference Implementation wscompile 1.0, using: JAX-RPC Standard Implementation (1.1, build R59)

package es.sendit2us.wastetracker.client.blackberry.rest;

import net.rim.device.api.util.Persistable;

public class CategoryIncidenceEntity extends MasterDataEntity implements Persistable {

	public static final long serialVersionUID = -6165178878325806406L;

	public CategoryIncidenceEntity() {
		super();
	}

	public CategoryIncidenceEntity(java.lang.String code, java.lang.String description, int id) {
		super(code, description, id);
	}
   
   public MasterDataEntity[] getArray(int numElems) {
   	return new CategoryIncidenceEntity[numElems];
   }
}
