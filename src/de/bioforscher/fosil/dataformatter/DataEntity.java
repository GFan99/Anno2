//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2018.12.12 um 11:10:11 AM CET 
//


package de.bioforscher.fosil.dataformatter;

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
 *         &lt;element name="TextEntity" type="{http://www.bioforscher.de/fosil/DataFormatter}TextEntity"/>
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
    "textEntity"
})
public class DataEntity {

    @XmlElement(name = "TextEntity", required = true)
    protected TextEntity textEntity;

    /**
     * Ruft den Wert der textEntity-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TextEntity }
     *     
     */
    public TextEntity getTextEntity() {
        return textEntity;
    }

    /**
     * Legt den Wert der textEntity-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TextEntity }
     *     
     */
    public void setTextEntity(TextEntity value) {
        this.textEntity = value;
    }

}
