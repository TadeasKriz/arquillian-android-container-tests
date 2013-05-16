package org.jboss.arquillian.android.container.test01.test;


import java.io.File;

import org.jboss.arquillian.container.android.api.AndroidDevice;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.container.test.api.TargetsContainer;
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

@RunWith(Arquillian.class)
@RunAsClient
public class ContainerTest {

    @Deployment(name = "android", testable = false)
    @TargetsContainer("android")
    public static Archive<?> createDeployment1() {
        return ShrinkWrap.createFromZipFile(JavaArchive.class, new File("selendroid-test-app-0.4.apk"));
    }

    @Test
    @InSequence(1)
    @OperateOnDeployment("android")
    public void test01(@ArquillianResource AndroidDevice android) {
        Assert.assertTrue(android != null);
    }
}
