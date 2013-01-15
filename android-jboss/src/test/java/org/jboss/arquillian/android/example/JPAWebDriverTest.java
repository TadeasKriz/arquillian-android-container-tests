/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.arquillian.android.example;

import java.io.File;
import java.net.URL;

import junit.framework.Assert;

import org.jboss.arquillian.example.controller.LoginController;
import org.jboss.arquillian.example.controller.RegisterController;
import org.jboss.arquillian.example.dao.UserDAO;
import org.jboss.arquillian.example.dao.UserDAOException;
import org.jboss.arquillian.example.dao.impl.UserDAOImpl;
import org.jboss.arquillian.example.model.Credentials;
import org.jboss.arquillian.example.model.User;
import org.jboss.arquillian.example.security.Authenticator;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.container.test.api.TargetsContainer;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author <a href="https://community.jboss.org/people/smikloso">Stefan Miklosovic</a>
 */
@RunWith(Arquillian.class)
public class JPAWebDriverTest {
	
    private static final String WEBAPP_SRC = "src/main/webapp";
    
    private static final String META_INF = "src/main/resources/META-INF";

    private static final String USERNAME = "JohnDoe";

    private static final String PASSWORD = "PASSWORD";

    @FindBy(id = "registerForm:username")
    WebElement registerUserNameField;

    @FindBy(id = "registerForm:password")
    WebElement registerPasswordField;

    @FindBy(id = "registerForm:register")
    WebElement submitRegistration;

    @FindBy(id = "loginForm:username")
    WebElement loginUserNameField;

    @FindBy(id = "loginForm:password")
    WebElement loginPasswordField;

    @FindBy(xpath = "//h3[contains(text(), 'Log in')]")
    WebElement loginHeader;

    @FindBy(id = "loginForm:login")
    WebElement submitLogin;

    @FindBy(xpath = "//p[contains(text(), 'You are signed in as " + USERNAME + ".')]")
    WebElement welcomeMessage;
    
    @Deployment(name = "jbossas", managed = true, testable = false)
    @TargetsContainer("jbossas")
	public static WebArchive createDeployment() {
		WebArchive war =  ShrinkWrap.create(WebArchive.class, "login.war")
				.addClasses(User.class,
						UserDAO.class,
						UserDAOImpl.class,
						UserDAOException.class,
						Authenticator.class, 
						Credentials.class,
						LoginController.class, 
						RegisterController.class)
				.addAsWebInfResource(new File(META_INF, "persistence.xml"), "classes/META-INF/persistence.xml")
				.addAsWebResource(new File(WEBAPP_SRC, "login.xhtml"))
				.addAsWebResource(new File(WEBAPP_SRC, "home.xhtml"))
				.addAsWebResource(new File(WEBAPP_SRC, "register.xhtml"))
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsWebInfResource(
						new StringAsset("<faces-config version=\"2.0\"/>"),
						"faces-config.xml");
		System.out.println(war.toString(true));
		return war;
	}
	
    @Drone
    WebDriver driver;

    @ArquillianResource
    URL deploymentUrl;

    @Test
    public void register() throws Exception {
        
        driver.get("http://10.0.2.2:8080/login/register.jsf");
        
        // Register

        registerUserNameField.sendKeys(USERNAME);
        registerPasswordField.sendKeys(PASSWORD);

        // ensure that HTTP request is fired and wait for the response to be delivered
        Graphene.guardHttp(submitRegistration).click();

        Assert.assertTrue(loginHeader.isDisplayed());

        // And try to log in
        Assert.assertTrue("User should be registered and redirected to login page!"
                , loginUserNameField.isDisplayed()
                        && loginPasswordField.isDisplayed());

        loginUserNameField.clear();
        loginUserNameField.sendKeys(USERNAME);
        loginPasswordField.clear();
        loginPasswordField.sendKeys(PASSWORD);

        // ensure that HTTP request is fired and wait for the response to be delivered
        Graphene.guardHttp(submitLogin).click();

        Assert.assertTrue("User should be at welcome page!", welcomeMessage.isDisplayed());
    }
}
