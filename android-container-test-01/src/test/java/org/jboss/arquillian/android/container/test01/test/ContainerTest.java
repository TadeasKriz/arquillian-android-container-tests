package org.jboss.arquillian.android.container.test01.test;


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
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.android.container.test01.Bar;

@RunWith(Arquillian.class)
@RunAsClient
public class ContainerTest {

    @Deployment(name = "android1", order = 1, testable = false)
    @TargetsContainer("android1")
    public static Archive<?> createDeployment1() {
        System.out.println("create deployment android2");
        return ShrinkWrap.create(JavaArchive.class, "android1.jar");
    }

    @Deployment(name = "jbossas", order = 2, testable = false)
    @TargetsContainer("jbossas")
    public static Archive<?> createDeployment2() {
        System.out.println("create deployment jbossas");
        return ShrinkWrap.create(JavaArchive.class, "jbossas.jar")
            .addClass(Bar.class);
    }

    @Test
    @InSequence(1)
    @OperateOnDeployment("android1")
    public void test01(@ArquillianResource AndroidDevice android) {
        Assert.assertTrue(android != null);
    }

    @Test
    @InSequence(2)
    @OperateOnDeployment("jbossas")
    public void test02() {
        Assert.assertTrue(true);
    }

}
