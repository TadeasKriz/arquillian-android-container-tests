package org.jboss.arquillian.android.container.test01;

public interface ObjectStore {
    <T> ObjectStore add(Class<T> type, T instance);
    
    <T> T get(Class<T> type);
    
    ObjectStore clear();
    
    int size();
}
