package org.jboss.arquillian.android.container.test02.test;


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

    @Deployment(name = "android2", order = 2, managed = true, testable = false)
    @TargetsContainer("android2")
    public static Archive<?> createDeployment2() {
        System.out.println("create deployment android2");
        return ShrinkWrap.create(JavaArchive.class, "jbossas1.jar").addClass(HashObjectStore.class)
            .addClass(ObjectStore.class).addClass(Bar.class).addClass(Baz.class);
    }

    @Deployment(name = "android3", order = 3, managed = true, testable = false)
    @TargetsContainer("android3")
    public static Archive<?> createDeployment3() {
        System.out.println("create deployment android3");
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

    @Test
    @InSequence(2)
    @OperateOnDeployment("android2")
    public void test02(@ArquillianResource AndroidDevice android) {
        System.out.println("test android2");
        Assert.assertTrue(android != null);
        System.out.println("\t serial number: " + android.getSerialNumber());
        Assert.assertTrue(true);
    }

    @Test
    @InSequence(3)
    @OperateOnDeployment("android3")
    public void test03(@ArquillianResource AndroidDevice android) {
        System.out.println("test android3");
        Assert.assertTrue(android != null);
        System.out.println("\t serial number: " + android.getSerialNumber());
        Assert.assertTrue(true);
    }
}
