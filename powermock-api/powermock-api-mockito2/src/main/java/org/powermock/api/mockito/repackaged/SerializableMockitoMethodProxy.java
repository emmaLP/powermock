/*
 *  Copyright (c) 2007 Mockito contributors
 *  This program is made available under the terms of the MIT License.
 */
package org.powermock.api.mockito.repackaged;

import org.mockito.internal.creation.util.MockitoMethodProxy;
import org.powermock.api.mockito.internal.mockcreation.RuntimeExceptionProxy;
import org.powermock.api.mockito.repackaged.cglib.proxy.MethodProxy;
import org.powermock.reflect.Whitebox;

import java.io.Serializable;

class SerializableMockitoMethodProxy implements MockitoMethodProxy, Serializable {

    private static final long serialVersionUID = -5337859962876770632L;
    private final Class<?> c1;
    private final Class<?> c2;
    private final String desc;
    private final String name;
    private final String superName;
    private transient MethodProxy methodProxy;

    public SerializableMockitoMethodProxy(MethodProxy methodProxy) {
        assert methodProxy != null;
        Object info = Whitebox.getInternalState(methodProxy, "createInfo");
        c1 = Whitebox.getInternalState(info, "c1");
        c2 = Whitebox.getInternalState(info, "c2");
        desc = methodProxy.getSignature().getDescriptor();
        name = methodProxy.getSignature().getName();
        superName = methodProxy.getSuperName();
        this.methodProxy = methodProxy;
    }

    MethodProxy getMethodProxy() {
        if (methodProxy == null) {
            methodProxy = MethodProxy.create(c1, c2, desc, name, superName);
        }
        return methodProxy;
    }

    public Object invokeSuper(Object target, Object[] arguments) {
        try {
            return getMethodProxy().invokeSuper(target, arguments);
        }
        catch (Throwable t) {
            throw new RuntimeExceptionProxy(t);
        }
    }
}