package org.jboss.arquillian.android.container.test03.test;


import org.jboss.arquillian.android.container.test01.Bar;
import org.jboss.arquillian.android.container.test01.Baz;
import org.jboss.arquillian.android.container.test01.ObjectStore;
import org.jboss.arquillian.container.android.api.AndroidDevice;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.container.test.api.TargetsContainer;
import org.jboss.arquillian.core.spi.HashObjectStore;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ContainerTest {

    @Deployment(name = "android1", order = 1, managed = true, testable = false)
    @TargetsContainer("android1")
    public static Archive<?> createDeployment1() {
        System.out.println("create deployment android1");
        return ShrinkWrap.create(JavaArchive.class, "jbossas1.jar").addClass(HashObjectStore.class)
            .addClass(ObjectStore.class).addClass(Bar.class).addClass(Baz.class);
    }

    @Test
    @InSequence(1)
    @OperateOnDeployment("android1")
    public void test01(@ArquillianResource AndroidDevice android) {
        System.out.println("test android1");
        Assert.assertTrue(android != null);
        System.out.println("\t serial number: " + android.getSerialNumber());
        Assert.assertTrue(true);
    }
}
