
package es.sendit2us.client.wstest.stubs;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the es.sendit2us.client.wstest.stubs package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _WasteTrackerException_QNAME = new QName("http://wastetracker.corecanarias.com/facade", "WasteTrackerException");
    private final static QName _AddRequest_QNAME = new QName("http://wastetracker.corecanarias.com/facade", "addRequest");
    private final static QName _AddRequestResponse_QNAME = new QName("http://wastetracker.corecanarias.com/facade", "addRequestResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: es.sendit2us.client.wstest.stubs
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PickupDetail }
     * 
     */
    public PickupDetail createPickupDetail() {
        return new PickupDetail();
    }

    /**
     * Create an instance of {@link AddRequest }
     * 
     */
    public AddRequest createAddRequest() {
        return new AddRequest();
    }

    /**
     * Create an instance of {@link WasteTrackerException }
     * 
     */
    public WasteTrackerException createWasteTrackerException() {
        return new WasteTrackerException();
    }

    /**
     * Create an instance of {@link AddRequestResponse }
     * 
     */
    public AddRequestResponse createAddRequestResponse() {
        return new AddRequestResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WasteTrackerException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wastetracker.corecanarias.com/facade", name = "WasteTrackerException")
    public JAXBElement<WasteTrackerException> createWasteTrackerException(WasteTrackerException value) {
        return new JAXBElement<WasteTrackerException>(_WasteTrackerException_QNAME, WasteTrackerException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wastetracker.corecanarias.com/facade", name = "addRequest")
    public JAXBElement<AddRequest> createAddRequest(AddRequest value) {
        return new JAXBElement<AddRequest>(_AddRequest_QNAME, AddRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddRequestResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wastetracker.corecanarias.com/facade", name = "addRequestResponse")
    public JAXBElement<AddRequestResponse> createAddRequestResponse(AddRequestResponse value) {
        return new JAXBElement<AddRequestResponse>(_AddRequestResponse_QNAME, AddRequestResponse.class, null, value);
    }

}
