/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.Controllers;

import com.sg.SuperheroSightings.dtos.Location;
import com.sg.SuperheroSightings.dtos.Org;
import com.sg.SuperheroSightings.dtos.Sighting;
import com.sg.SuperheroSightings.dtos.Supe;
import com.sg.SuperheroSightings.service.Response;
import com.sg.SuperheroSightings.service.SuperService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.util.ArrayUtils;

/**
 *
 * @author Tamara
 */
@Controller
public class SightingController {

    @Autowired
    SuperService service;

    @GetMapping("sightings")
    public String displaySightings(Model model) {
        Response<List<Sighting>> response = service.getSightings();
        Response<List<Location>> locs = service.getAllLocs();
        Response<List<Supe>> supes = service.getAllSupers();

        model.addAttribute("supers", supes.getData());
        model.addAttribute("sightingsList", response.getData());
        model.addAttribute("locations", locs.getData());
        return "sightings";
    }

    @PostMapping("addSight")
    public String addSight(Sighting sight, HttpServletRequest request) {
        String locId = request.getParameter("locationId");
        String[] superIds = request.getParameterValues("superId");

        Response<Location> locRes = service.getLocById(Integer.parseInt(locId));
        Location loc = locRes.getData();
        sight.setLocation(loc);

        List<Supe> supes = new ArrayList();
        if (!ArrayUtils.isEmpty(superIds)) {
            for (String superId : superIds) {
                Response<Supe> response = service.getSupeById(Integer.parseInt(superId));
                if (response.getSuccess()) {
                    supes.add(response.getData());
                }
            }
        }

        sight.setSupers(supes);
        Response<Sighting> response = service.addSighting(sight);

        return "redirect:/sightings";
    }

    @GetMapping("/editSight/{id}")
    public String editSight(@PathVariable Integer id, Model model) {
        Response<List<Supe>> response = service.getAllSupers();
        Response<Sighting> sight = service.getSightById(id.intValue());
        Response<List<Location>> locs = service.getAllLocs();

        model.addAttribute("locations", locs.getData());
        model.addAttribute("supers", response.getData());
        model.addAttribute("sight", sight.getData());
        return "editSight";
    }

    @PostMapping("editSight")
    public String editSight(Sighting sight, HttpServletRequest request) {
        String locId = request.getParameter("locationId");
        String[] superIds = request.getParameterValues("superId");

        Response<Location> locRes = service.getLocById(Integer.parseInt(locId));
        Location loc = locRes.getData();
        sight.setLocation(loc);

        List<Supe> supes = new ArrayList();

        if (!ArrayUtils.isEmpty(superIds)) {
            for (String superId : superIds) {
                Response<Supe> response = service.getSupeById(Integer.parseInt(superId));
                if (response.getSuccess()) {
                    supes.add(response.getData());
                }
            }
        }

        sight.setSupers(supes);
        Response response = service.updateSighting(sight);

        return "redirect:/sightings";
    }

    @GetMapping("sightDetail/{id}")
    public String displaySightDetail(@PathVariable Integer id, Model model) {
        Response<Sighting> sight = service.getSightById(id.intValue());
        model.addAttribute("sight", sight.getData());
        return "sightDetail";
    }

    @GetMapping("deleteSight/{id}")
    public String deleteSight(@PathVariable Integer id) {
        Response response = service.deleteSighting(id.intValue());
        return "redirect:/sightings";
    }
    
    @GetMapping("location")
    public String getLocPage(Model model){
        Response<List<Location>> locs = service.getAllLocs();

        model.addAttribute("locs", locs.getData());
        return "location";
    }
    
    @PostMapping("addLocation")
    public String addLocation(Location loc, HttpServletRequest request) {

        Response<Location> response = service.addLocation(loc);

        return "redirect:/location";
    }
    
    @GetMapping("editLocation/{id}")
    public String editLocation(@PathVariable Integer id, Model model) {
        Response<Location> loc = service.getLocById(id.intValue());

        model.addAttribute("loc", loc.getData());
        return "editLocation";
    }
    
    @PostMapping("editLocation/{id}")
    public String editLocation(Location loc) {
        Response response = service.updateLocation(loc);

        return "redirect:/location";
    }
    
    @GetMapping("deleteLocation/{id}")
    public String deleteLoc(@PathVariable Integer id) {
        Response response = service.deleteLocation(id.intValue());
        return "redirect:/location";
    }
    
}
