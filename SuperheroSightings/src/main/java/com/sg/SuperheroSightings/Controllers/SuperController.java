/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.Controllers;

import com.sg.SuperheroSightings.dtos.Org;
import com.sg.SuperheroSightings.dtos.Power;
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
public class SuperController {

    @Autowired
    SuperService service;

    @GetMapping({"/", "home"})
    public String displayHome(Model model) {
        Response<List<Sighting>> response = service.getLastSightings();
        model.addAttribute("sightingsList", response.getData());
        return "home";
    }

    @GetMapping("supers")
    public String displaySuper(Model model) {
        Response<List<Supe>> response = service.getAllSupers();
        Response<List<Power>> powers = service.getAllPowers();
        Response<List<Org>> orgs = service.getAllOrgs();

        model.addAttribute("orgs", orgs.getData());
        model.addAttribute("powers", powers.getData());
        model.addAttribute("supers", response.getData());
        return "supers";
    }

    @PostMapping("addSuper")
    public String addSuper(Supe supe, HttpServletRequest request) {
        String powId = request.getParameter("powerId");
        String[] orgsId = request.getParameterValues("orgId");

        Power pow = new Power();
        pow.setId(Integer.parseInt(powId));
        supe.setPower(pow);

        List<Org> orgs = new ArrayList();
        if (!ArrayUtils.isEmpty(orgsId)) {
            for (String orgId : orgsId) {
                Response<Org> response = service.getOrgById(Integer.parseInt(orgId));
                if (response.getSuccess()) {
                    orgs.add(response.getData());
                }
            }
        }
        supe.setOrgs(orgs);
        Response<Supe> response = service.addSuper(supe);

        return "redirect:/supers";
    }

    @GetMapping("/editSuper/{id}")
    public String editSuper(@PathVariable Integer id, Model model) {
//        Response<List<Supe>> response = service.getAllSupers();
        Response<List<Power>> powers = service.getAllPowers();
        Response<List<Org>> orgs = service.getAllOrgs();
        Response<Supe> supe = service.getSupeById(id.intValue());

        model.addAttribute("orgsList", orgs.getData());
        model.addAttribute("powers", powers.getData());
//        model.addAttribute("supers", response.getData());
        model.addAttribute("super", supe.getData());
        return "editSuper";
    }

    @PostMapping("editSuper")
    public String editSuper(Supe supe, HttpServletRequest request) {
        String powId = request.getParameter("powerId");
        String[] orgsId = request.getParameterValues("orgsId");

        Power pow = new Power();
        pow.setId(Integer.parseInt(powId));
        supe.setPower(pow);

        List<Org> orgs = new ArrayList();
        if (!ArrayUtils.isEmpty(orgsId)) {
            for (String orgId : orgsId) {
                Response<Org> response = service.getOrgById(Integer.parseInt(orgId));
                if (response.getSuccess()) {
                    orgs.add(response.getData());
                }
            }
        }

        supe.setOrgs(orgs);
        Response<Supe> response = service.updateSuper(supe);

        return "redirect:/supers";
    }

    @GetMapping("superDetail/{id}")
    public String displaySuperDetail(@PathVariable Integer id, Model model) {
        Response<Supe> supe = service.getSupeById(id.intValue());
        model.addAttribute("super", supe.getData());
        return "superDetail";
    }

    @GetMapping("deleteSuper/{id}")
    public String deleteSuper(@PathVariable Integer id) {
        Response response = service.deleteSuper(id.intValue());
        return "redirect:/supers";
    }
    
    @GetMapping("addPower")
    public String addPower(Model model){
        Response<List<Power>> powers = service.getAllPowers();
        model.addAttribute("powers", powers.getData());

        return "addPower";
    }
    
    @PostMapping("addPower")
    public String addPower(Power power, HttpServletRequest request) {

        Response<Power> response = service.addPower(power);

        return "redirect:/addPower";
    }
    
    @GetMapping("deletePower/{id}")
    public String deletePower(@PathVariable Integer id) {
        Response response = service.deletePower(id.intValue());
        return "redirect:/addPower";
    }
}
