//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2018.11.07 um 09:43:41 AM CET 
//


package xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für DataEntity complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="DataEntity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="source" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="textID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="text" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="annotations" type="{http://www.bioforscher.de/fosil/DataFormatter}AnnotationItem"/>
 *         &lt;element name="raterID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DataEntity", propOrder = {
    "source",
    "textID",
    "text",
    "annotations",
    "raterID"
})
public class DataEntity {

    @XmlElement(required = true)
    protected String source;
    @XmlElement(required = true)
    protected String textID;
    @XmlElement(required = true)
    protected String text;
    @XmlElement(required = true)
    protected AnnotationItem annotations;
    @XmlElement(required = true)
    protected String raterID;

    /**
     * Ruft den Wert der source-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSource() {
        return source;
    }

    /**
     * Legt den Wert der source-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSource(String value) {
        this.source = value;
    }

    /**
     * Ruft den Wert der textID-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTextID() {
        return textID;
    }

    /**
     * Legt den Wert der textID-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTextID(String value) {
        this.textID = value;
    }

    /**
     * Ruft den Wert der text-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getText() {
        return text;
    }

    /**
     * Legt den Wert der text-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setText(String value) {
        this.text = value;
    }

    /**
     * Ruft den Wert der annotations-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link AnnotationItem }
     *     
     */
    public AnnotationItem getAnnotations() {
        return annotations;
    }

    /**
     * Legt den Wert der annotations-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link AnnotationItem }
     *     
     */
    public void setAnnotations(AnnotationItem value) {
        this.annotations = value;
    }

    /**
     * Ruft den Wert der raterID-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRaterID() {
        return raterID;
    }

    /**
     * Legt den Wert der raterID-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRaterID(String value) {
        this.raterID = value;
    }

}
