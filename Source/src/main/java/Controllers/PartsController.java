package Controllers;

import Services.ExecuteSelect;
import jdk.nashorn.internal.runtime.ECMAException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PartsController {
    private ExecuteSelect _es;
    private ArrayList<HashMap<String, String>> _partsArray;

    public PartsController() {
        _es = new ExecuteSelect();
        @SuppressWarnings("unchecked")
        ArrayList<HashMap<String, String >> partsQuery = _es.execute("SELECT * FROM APP.PART");
        _partsArray = partsQuery;
    }

    // {PART_NAME=NVIDIA GeForce 900, PART_PRICE=199.99, PART_FORM_FACTOR=Desktop, PART_ID=1} <= list of these
    public ArrayList<HashMap<String, String >> getParts() {
        return _partsArray;
    }

    public ArrayList<String> getPartNames() {
        ArrayList<String> parts = new ArrayList<>();
        for (HashMap h : _partsArray) {
            String name = h.get("PART_NAME").toString();
            parts.add(name);
        }
        return parts;
    }

    public Double getPartPrice(int id) {
        Double price = 0.00;
        for (HashMap h : _partsArray) {
            if(Integer.parseInt(h.get("PART_ID").toString()) == id) {
                price = Double.parseDouble(h.get("PART_PRICE").toString());
            }
        }
        return price;
    }

    public Double getPartPrice(String name) {
        Double price = 0.00;
        for (HashMap h : _partsArray) {
            if(h.get("PART_NAME").toString() == name) {
                price = Double.parseDouble(h.get("PART_PRICE").toString());
            }
        }
        return price;
    }

    public ArrayList getPartAttributes(int partId) {
        @SuppressWarnings("unchecked")
        ArrayList partAttributes =
                _es.execute("SELECT * FROM " +
                                "PART_ATTRIBUTE WHERE PART_ID = " + partId);
        return partAttributes;
    }

    public int getPartId(String partName) {
        String query = "SELECT PART_ID FROM PART WHERE PART_NAME = '"+partName+"'";
        ArrayList<HashMap<String, String>> queryResult = _es.execute(query);
        try {
            HashMap<String, String> partInfoHashmap = queryResult.get(0);
            System.out.println("SUCCESS: "+query);
            return Integer.parseInt(partInfoHashmap.values().toArray()[0].toString());
        //if there is no part returned from the query queryResult.get will throw an error
        //send back -99 to indicate no part found.
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("COULD NOT FIND PART: "+ partName);
            return -99;
        }
    }
}
