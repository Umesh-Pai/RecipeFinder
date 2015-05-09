package com.pactera.facade;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.archive.FridgeArchive;
import com.pactera.domain.FridgeItem;
import com.pactera.utils.CSVParser;
import com.pactera.utils.Suggestion;
import com.pactera.utils.SuggestionHelper;

/**
 * This class is responsible for fetching fridge items
 * and to add new items
 * 
 */
@Path("/fridge")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FridgeFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(FridgeFacade.class);
    
    /**
     * This method returns the list of items from the Fridge
     *
     * @param  
     * @return  list of items from the fridge
     */
    @GET
    public List<FridgeItem> get() {
        return FridgeArchive.instance().get();
    }
    
    /**
     * This method inserts items into fridge and returns suggestions for what to cook based on the items in the fridge and the recipes provided
     *
     * @param  items  items to be added into the fridge
     * @return  suggestions for what to cook
     * @throws IOException
     */
    @POST @Path("/add")
    @Consumes(MediaType.TEXT_PLAIN)
    public Suggestion addItems(String items) throws IOException {
       
        try {
            if (items.trim().isEmpty()) 
            	throw new IllegalArgumentException("No items specified! ");

            /**
        	* parsing CSV data, each line contains a fridge item
        	*/
            List<FridgeItem> fridgeItems = CSVParser.parseFridgeItems(items);
            FridgeArchive.instance().update(fridgeItems);
            
        } catch(IllegalArgumentException e) {
        	LOGGER.error(e.getMessage());
            throw new WebApplicationException(Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build());
        }

        /**
    	* To send response back either as order taken or available recipes
    	*/
        return SuggestionHelper.suggestRecipe();
    }
}

