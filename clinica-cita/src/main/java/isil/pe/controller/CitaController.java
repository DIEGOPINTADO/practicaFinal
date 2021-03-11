package isil.pe.controller;

import isil.pe.model.Cita;
import isil.pe.service.CitaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CitaController {


    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }


    @GetMapping("/")
    public String index(Model model){

        model.addAttribute("cita", new Cita());

        return "index";
    }


    @PostMapping("/cita/save")
    public String citaSave(Cita cita){

        System.out.println(cita);

      //  citaService.sendNotification(cita);
        return "redirect:/";
    }
}


