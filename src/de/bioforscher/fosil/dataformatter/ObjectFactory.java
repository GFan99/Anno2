//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2018.12.12 um 11:10:11 AM CET 
//


package de.bioforscher.fosil.dataformatter;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the de.bioforscher.fosil.dataformatter package. 
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

    private final static QName _Element_QNAME = new QName("http://www.bioforscher.de/fosil/DataFormatter", "element");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: de.bioforscher.fosil.dataformatter
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DataEntity }
     * 
     */
    public DataEntity createDataEntity() {
        return new DataEntity();
    }

    /**
     * Create an instance of {@link AnnotationItem }
     * 
     */
    public AnnotationItem createAnnotationItem() {
        return new AnnotationItem();
    }

    /**
     * Create an instance of {@link Label }
     * 
     */
    public Label createLabel() {
        return new Label();
    }

    /**
     * Create an instance of {@link TextEntity }
     * 
     */
    public TextEntity createTextEntity() {
        return new TextEntity();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DataEntity }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.bioforscher.de/fosil/DataFormatter", name = "element")
    public JAXBElement<DataEntity> createElement(DataEntity value) {
        return new JAXBElement<DataEntity>(_Element_QNAME, DataEntity.class, null, value);
    }

}
