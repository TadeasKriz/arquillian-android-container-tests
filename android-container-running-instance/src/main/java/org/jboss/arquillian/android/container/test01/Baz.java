package org.jboss.arquillian.android.container.test01;

public class Baz {

    String name;
    
    public Baz(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return "baz";
    }

}
