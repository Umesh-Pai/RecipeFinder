package com.pactera.facade;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

import java.io.IOException;
import javax.ws.rs.WebApplicationException;
import org.junit.Before;
import org.junit.Test;

import com.pactera.archive.FridgeArchive;

public class FridgeFacadeTest {

    private FridgeFacade facade;

    @Before
    public void init()
    {
    	facade = new FridgeFacade();
        FridgeArchive.instance().removeAll();
    }

    @Test(expected = WebApplicationException.class)
    public void addItems_shouldThrowAnException_WhenCsvIsEmpty() throws IOException {
    	facade.addItems("");
        fail("Adding items should have failed!");
    }

    @Test(expected = WebApplicationException.class)
    public void addItems_shouldThrowAnException_WhenCsvIsBlank() throws IOException {
    	facade.addItems("   ");
        fail("Adding items should have failed!");
    }

    @Test(expected = WebApplicationException.class)
    public void addItems_shouldThrowAnException_WhenCsvIsInvalid() throws IOException {
        String invalidCsv = "bread,10,slices,25/12/2014\n" +
                            "cheese,10,slices,25/12/2014, unexpected-column\n"+
                            "bread,10,slices,25/13/2014";
                            
                            
        facade.addItems(invalidCsv);
        fail("Adding items should have failed!");
    }

    @Test
    public void addItems_shouldPopulateTheArchive() throws IOException {
        String csv = "bread,10,slices,25/12/2014\n" +
                     "cheese,10,slices,25/12/2014";

        FridgeArchive.instance().removeAll();

        facade.addItems(csv);
        assertThat(FridgeArchive.instance().get()).hasSize(2);
    }
}
