/**
 * MisKey.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package xbolt.custom.hanwha.aprv.neo.branch.ss.approval.axisws;

public class MisKey  implements java.io.Serializable {
    private java.lang.String misDocId;

    private java.lang.String systemId;

    public MisKey() {
    }

    public MisKey(
           java.lang.String misDocId,
           java.lang.String systemId) {
           this.misDocId = misDocId;
           this.systemId = systemId;
    }


    /**
     * Gets the misDocId value for this MisKey.
     * 
     * @return misDocId
     */
    public java.lang.String getMisDocId() {
        return misDocId;
    }


    /**
     * Sets the misDocId value for this MisKey.
     * 
     * @param misDocId
     */
    public void setMisDocId(java.lang.String misDocId) {
        this.misDocId = misDocId;
    }


    /**
     * Gets the systemId value for this MisKey.
     * 
     * @return systemId
     */
    public java.lang.String getSystemId() {
        return systemId;
    }


    /**
     * Sets the systemId value for this MisKey.
     * 
     * @param systemId
     */
    public void setSystemId(java.lang.String systemId) {
        this.systemId = systemId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MisKey)) return false;
        MisKey other = (MisKey) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.misDocId==null && other.getMisDocId()==null) || 
             (this.misDocId!=null &&
              this.misDocId.equals(other.getMisDocId()))) &&
            ((this.systemId==null && other.getSystemId()==null) || 
             (this.systemId!=null &&
              this.systemId.equals(other.getSystemId())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getMisDocId() != null) {
            _hashCode += getMisDocId().hashCode();
        }
        if (getSystemId() != null) {
            _hashCode += getSystemId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MisKey.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://vo.approval.ss.branch.neo.hanwha", "MisKey"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("misDocId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://vo.approval.ss.branch.neo.hanwha", "misDocId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("systemId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://vo.approval.ss.branch.neo.hanwha", "systemId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
