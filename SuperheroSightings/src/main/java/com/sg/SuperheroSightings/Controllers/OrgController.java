/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.Controllers;

import com.sg.SuperheroSightings.dtos.Org;
import com.sg.SuperheroSightings.dtos.Power;
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
public class OrgController {

    @Autowired
    SuperService service;

    @GetMapping("/orgs")
    public String displayOrgs(Model model) {
        Response<List<Supe>> response = service.getAllSupers();
        Response<List<Org>> orgs = service.getAllOrgs();

        model.addAttribute("orgs", orgs.getData());
        model.addAttribute("supers", response.getData());
        return "orgs";
    }

    @PostMapping("addOrg")
    public String addOrg(Org org, HttpServletRequest request) {
        String[] superIds = request.getParameterValues("superId");

        List<Supe> supes = new ArrayList();
        if (!ArrayUtils.isEmpty(superIds)) {
            for (String superId : superIds) {
                Response<Supe> response = service.getSupeById(Integer.parseInt(superId));
                if (response.getSuccess()) {
                    supes.add(response.getData());
                }
            }
        }
        
        org.setAllSupers(supes);
        Response<Org> response = service.addOrg(org);

        return "redirect:/orgs";
    }

    @GetMapping("/editOrg/{id}")
    public String editOrg(@PathVariable Integer id, Model model) {
        Response<List<Supe>> response = service.getAllSupers();
        Response<Org> org = service.getOrgById(id.intValue());

        model.addAttribute("supers", response.getData());
        model.addAttribute("org", org.getData());
        return "editOrg";
    }

    @PostMapping("editOrg")
    public String editOrg(Org org, HttpServletRequest request) {
        String[] superIds = request.getParameterValues("superId");

        List<Supe> supes = new ArrayList();
        if (!ArrayUtils.isEmpty(superIds)) {
            for (String superId : superIds) {
                Response<Supe> response = service.getSupeById(Integer.parseInt(superId));
                if (response.getSuccess()) {
                    supes.add(response.getData());
                }
            }
        }

        org.setAllSupers(supes);
        Response response = service.updateOrg(org);

        return "redirect:/orgs";
    }

    @GetMapping("orgDetail/{id}")
    public String displayDetail(@PathVariable Integer id, Model model) {
        Response<Org> org = service.getOrgById(id.intValue());
        model.addAttribute("org", org.getData());
        return "orgDetail";
    }

    @GetMapping("deleteOrg/{id}")
    public String deleteOrg(@PathVariable Integer id) {
        Response response = service.deleteOrg(id.intValue());
        return "redirect:/orgs";
    }

}
