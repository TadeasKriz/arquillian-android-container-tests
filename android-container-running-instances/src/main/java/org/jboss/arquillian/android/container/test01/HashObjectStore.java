package org.jboss.arquillian.android.container.test01;

import java.util.concurrent.ConcurrentHashMap;

public class HashObjectStore implements ObjectStore {

    private ConcurrentHashMap<Class<?>, Object> store;
    
    public HashObjectStore() {
        store = new ConcurrentHashMap<Class<?>, Object>();
    }
    
    @Override
    public <T> ObjectStore add(Class<T> type, T instance) {
        store.put(type, instance);
        return this;
    }

    @Override
    public <T> T get(Class<T> type) {
        return type.cast(store.get(type));
    }

    @Override
    public ObjectStore clear() {
        store.clear();
        return this;
    }
    
    @Override 
    public int size() {
        return store.size();
    }
    
}
