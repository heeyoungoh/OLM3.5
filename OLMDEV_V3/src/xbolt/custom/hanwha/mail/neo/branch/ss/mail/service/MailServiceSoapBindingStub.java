/**
 * MailServiceSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package xbolt.custom.hanwha.mail.neo.branch.ss.mail.service;

public class MailServiceSoapBindingStub extends org.apache.axis.client.Stub implements xbolt.custom.hanwha.mail.neo.branch.ss.mail.service.MailService {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[3];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getMailStatusCounts");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://service.mail.ss.branch.neo.hanwha", "mailKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String[].class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://vo.mail.ss.branch.neo.hanwha", "WsMailStatus"));
        oper.setReturnClass(xbolt.custom.hanwha.mail.neo.branch.ss.mail.vo.WsMailStatus[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://service.mail.ss.branch.neo.hanwha", "getMailStatusCountsReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://service.mail.ss.branch.neo.hanwha", "fault"),
                      "hanwha.neo.branch.ss.common.vo.WsException",
                      new javax.xml.namespace.QName("http://vo.common.ss.branch.neo.hanwha", "WsException"), 
                      true
                     ));
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("cancelMISMailByRecipient");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://service.mail.ss.branch.neo.hanwha", "mailKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://service.mail.ss.branch.neo.hanwha", "receiverForCancel"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String[].class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://service.mail.ss.branch.neo.hanwha", "senderInfo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://vo.mail.ss.branch.neo.hanwha", "WsResource"), xbolt.custom.hanwha.mail.neo.branch.ss.mail.vo.WsResource.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://service.mail.ss.branch.neo.hanwha", "cancelMISMailByRecipientReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://service.mail.ss.branch.neo.hanwha", "fault"),
                      "hanwha.neo.branch.ss.common.vo.WsException",
                      new javax.xml.namespace.QName("http://vo.common.ss.branch.neo.hanwha", "WsException"), 
                      true
                     ));
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("sendMISMail");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://service.mail.ss.branch.neo.hanwha", "mailBody"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://service.mail.ss.branch.neo.hanwha", "mailInfo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://vo.mail.ss.branch.neo.hanwha", "WsMailInfo"), xbolt.custom.hanwha.mail.neo.branch.ss.mail.vo.WsMailInfo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://service.mail.ss.branch.neo.hanwha", "receivers"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://vo.mail.ss.branch.neo.hanwha", "WsRecipient"), xbolt.custom.hanwha.mail.neo.branch.ss.mail.vo.WsRecipient[].class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://service.mail.ss.branch.neo.hanwha", "attachFile"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://vo.common.ss.branch.neo.hanwha", "WsAttachFile"), xbolt.custom.hanwha.mail.neo.branch.ss.common.vo.WsAttachFile[].class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://service.mail.ss.branch.neo.hanwha", "sendMISMailReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://service.mail.ss.branch.neo.hanwha", "fault"),
                      "hanwha.neo.branch.ss.common.vo.WsException",
                      new javax.xml.namespace.QName("http://vo.common.ss.branch.neo.hanwha", "WsException"), 
                      true
                     ));
        _operations[2] = oper;

    }

    public MailServiceSoapBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public MailServiceSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public MailServiceSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://vo.common.ss.branch.neo.hanwha", "WsAttachFile");
            cachedSerQNames.add(qName);
            cls = xbolt.custom.hanwha.mail.neo.branch.ss.common.vo.WsAttachFile.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://vo.common.ss.branch.neo.hanwha", "WsException");
            cachedSerQNames.add(qName);
            cls = xbolt.custom.hanwha.mail.neo.branch.ss.common.vo.WsException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://vo.mail.ss.branch.neo.hanwha", "WsMailInfo");
            cachedSerQNames.add(qName);
            cls = xbolt.custom.hanwha.mail.neo.branch.ss.mail.vo.WsMailInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://vo.mail.ss.branch.neo.hanwha", "WsMailStatus");
            cachedSerQNames.add(qName);
            cls = xbolt.custom.hanwha.mail.neo.branch.ss.mail.vo.WsMailStatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://vo.mail.ss.branch.neo.hanwha", "WsRecipient");
            cachedSerQNames.add(qName);
            cls = xbolt.custom.hanwha.mail.neo.branch.ss.mail.vo.WsRecipient.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://vo.mail.ss.branch.neo.hanwha", "WsResource");
            cachedSerQNames.add(qName);
            cls = xbolt.custom.hanwha.mail.neo.branch.ss.mail.vo.WsResource.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public xbolt.custom.hanwha.mail.neo.branch.ss.mail.vo.WsMailStatus[] getMailStatusCounts(java.lang.String[] mailKey) throws java.rmi.RemoteException, xbolt.custom.hanwha.mail.neo.branch.ss.common.vo.WsException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://service.mail.ss.branch.neo.hanwha", "getMailStatusCounts"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {mailKey});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (xbolt.custom.hanwha.mail.neo.branch.ss.mail.vo.WsMailStatus[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (xbolt.custom.hanwha.mail.neo.branch.ss.mail.vo.WsMailStatus[]) org.apache.axis.utils.JavaUtils.convert(_resp, xbolt.custom.hanwha.mail.neo.branch.ss.mail.vo.WsMailStatus[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof xbolt.custom.hanwha.mail.neo.branch.ss.common.vo.WsException) {
              throw (xbolt.custom.hanwha.mail.neo.branch.ss.common.vo.WsException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public java.lang.String cancelMISMailByRecipient(java.lang.String mailKey, java.lang.String[] receiverForCancel, xbolt.custom.hanwha.mail.neo.branch.ss.mail.vo.WsResource senderInfo) throws java.rmi.RemoteException, xbolt.custom.hanwha.mail.neo.branch.ss.common.vo.WsException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://service.mail.ss.branch.neo.hanwha", "cancelMISMailByRecipient"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {mailKey, receiverForCancel, senderInfo});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof xbolt.custom.hanwha.mail.neo.branch.ss.common.vo.WsException) {
              throw (xbolt.custom.hanwha.mail.neo.branch.ss.common.vo.WsException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public java.lang.String sendMISMail(java.lang.String mailBody, xbolt.custom.hanwha.mail.neo.branch.ss.mail.vo.WsMailInfo mailInfo, xbolt.custom.hanwha.mail.neo.branch.ss.mail.vo.WsRecipient[] receivers, xbolt.custom.hanwha.mail.neo.branch.ss.common.vo.WsAttachFile[] attachFile) throws java.rmi.RemoteException, xbolt.custom.hanwha.mail.neo.branch.ss.common.vo.WsException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://service.mail.ss.branch.neo.hanwha", "sendMISMail"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {mailBody, mailInfo, receivers, attachFile});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof xbolt.custom.hanwha.mail.neo.branch.ss.common.vo.WsException) {
              throw (xbolt.custom.hanwha.mail.neo.branch.ss.common.vo.WsException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

}
