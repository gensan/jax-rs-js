/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.mbae.jaxjs.model;

import ch.mbae.jaxjs.annotations.Type;
import static org.junit.Assert.*;

import ch.mbae.services.Contact;
import java.lang.reflect.Method;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author marcbaechinger
 */
public class ServiceMethodModelBuilderTest {
    private ServiceMethodModelBuilder builder;

    @Before
    public void init() {
        builder = new ServiceMethodModelBuilder();
    }
    
    @Test
    public void buildMethodModelForUpdateMethod() {
        ServiceMethodModel model = builder.buildMethodModel
                (getMethod("update"), null);
        
        assertNotNull(model);
        
        Assert.assertEquals(HttpVerb.PUT, model.getHttpVerb());
        Assert.assertEquals("data", model.getJsonParameter());
        Assert.assertEquals(Contact.class, model.getJsonParameterType());
        Assert.assertEquals(ReturnType.Object, model.getReturnType());
        Assert.assertEquals("update", model.getName());
        Assert.assertEquals("/{id}/comments/{commentId}", model.getPath());
        Assert.assertEquals(2, model.getPathParameters().size());
    }
    
    @Test
    public void buildMethodModelForUpdateCollectionMethod() {
        ServiceMethodModel model = builder.buildMethodModel
                (getMethod("updateCollection"), null);
        
        assertNotNull(model);
        
        Assert.assertEquals("updateCollection", model.getName());
        Assert.assertEquals(HttpVerb.POST, model.getHttpVerb());
        Assert.assertEquals("data", model.getJsonParameter());
        Assert.assertEquals(Contact.class, model.getJsonParameterType());
        Assert.assertEquals(ReturnType.Object, model.getReturnType());
        Assert.assertEquals("contact", model.getPayloadPropertyName());
    }
    
    @Test
    public void buildMethodModelForUpdateArrayMethod() {
        ServiceMethodModel model = builder.buildMethodModel
                (getMethod("updateArray"), null);
        
        assertNotNull(model);
        
        Assert.assertEquals("updateArray", model.getName());
        Assert.assertEquals(HttpVerb.DELETE, model.getHttpVerb());
        Assert.assertEquals("data", model.getJsonParameter());
        Assert.assertEquals(Contact.class, model.getJsonParameterType());
        Assert.assertEquals(ReturnType.Collection, model.getReturnType());
        Assert.assertEquals("contact", model.getPayloadPropertyName());
    }
    
    @PUT
    @Path("/{id}/comments/{commentId}")
    public Contact update(@PathParam("id") Long id, @PathParam("commentId") Long commentId, Contact contact) {
        return null;
    }
    
    @POST
    public Contact updateCollection(@Type(Contact.class) List<Contact> contacts) {
        return null;
    }
    
    @DELETE
    public Contact[] updateArray(Contact[] contacts) {
        return null;
    }
    
    
    private Method getMethod(String name) {
        Class clazz = this.getClass();
        Method matchingMethod = null;
        for (Method m: clazz.getDeclaredMethods()) {
            if (m.getName().equals(name)) {
                matchingMethod = m;
                break;
            }
        }
        return matchingMethod;
    }
}
