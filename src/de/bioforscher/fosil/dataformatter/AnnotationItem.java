//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2018.11.07 um 10:21:16 AM CET 
//


package de.bioforscher.fosil.dataformatter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für AnnotationItem complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="AnnotationItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="isEvent" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="isMusic" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AnnotationItem", propOrder = {
    "isEvent",
    "isMusic"
})
public class AnnotationItem {

    protected boolean isEvent;
    protected boolean isMusic;

    /**
     * Ruft den Wert der isEvent-Eigenschaft ab.
     * 
     */
    public boolean isIsEvent() {
        return isEvent;
    }

    /**
     * Legt den Wert der isEvent-Eigenschaft fest.
     * 
     */
    public void setIsEvent(boolean value) {
        this.isEvent = value;
    }

    /**
     * Ruft den Wert der isMusic-Eigenschaft ab.
     * 
     */
    public boolean isIsMusic() {
        return isMusic;
    }

    /**
     * Legt den Wert der isMusic-Eigenschaft fest.
     * 
     */
    public void setIsMusic(boolean value) {
        this.isMusic = value;
    }

}
