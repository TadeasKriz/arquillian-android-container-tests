package org.jboss.arquillian.android.container.test01;

public class Bar {
    
    String name;
    
    public Bar(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return "bar";
    }
}
