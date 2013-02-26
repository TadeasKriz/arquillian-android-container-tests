package org.jboss.arquillian.android.container.test01;

public interface Context {
    boolean isActive();
    
    ObjectStore getObjectStore();
    
    void clearAll();
}
