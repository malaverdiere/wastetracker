
package es.sendit2us.client.wstest.stubs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for pickupDetail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pickupDetail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="categoryCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="categoryDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="containerCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="containerDescr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="familyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="familyDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="usageCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="usageDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pickupDetail", propOrder = {
    "categoryCode",
    "categoryDesc",
    "containerCode",
    "containerDescr",
    "familyCode",
    "familyDesc",
    "usageCode",
    "usageDesc"
})
public class PickupDetail {

    protected String categoryCode;
    protected String categoryDesc;
    protected String containerCode;
    protected String containerDescr;
    protected String familyCode;
    protected String familyDesc;
    protected String usageCode;
    protected String usageDesc;

    /**
     * Gets the value of the categoryCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCategoryCode() {
        return categoryCode;
    }

    /**
     * Sets the value of the categoryCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCategoryCode(String value) {
        this.categoryCode = value;
    }

    /**
     * Gets the value of the categoryDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCategoryDesc() {
        return categoryDesc;
    }

    /**
     * Sets the value of the categoryDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCategoryDesc(String value) {
        this.categoryDesc = value;
    }

    /**
     * Gets the value of the containerCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContainerCode() {
        return containerCode;
    }

    /**
     * Sets the value of the containerCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContainerCode(String value) {
        this.containerCode = value;
    }

    /**
     * Gets the value of the containerDescr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContainerDescr() {
        return containerDescr;
    }

    /**
     * Sets the value of the containerDescr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContainerDescr(String value) {
        this.containerDescr = value;
    }

    /**
     * Gets the value of the familyCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFamilyCode() {
        return familyCode;
    }

    /**
     * Sets the value of the familyCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFamilyCode(String value) {
        this.familyCode = value;
    }

    /**
     * Gets the value of the familyDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFamilyDesc() {
        return familyDesc;
    }

    /**
     * Sets the value of the familyDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFamilyDesc(String value) {
        this.familyDesc = value;
    }

    /**
     * Gets the value of the usageCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsageCode() {
        return usageCode;
    }

    /**
     * Sets the value of the usageCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsageCode(String value) {
        this.usageCode = value;
    }

    /**
     * Gets the value of the usageDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsageDesc() {
        return usageDesc;
    }

    /**
     * Sets the value of the usageDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsageDesc(String value) {
        this.usageDesc = value;
    }

}
