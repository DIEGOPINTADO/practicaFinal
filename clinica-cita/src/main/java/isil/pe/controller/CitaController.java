package isil.pe.controller;

import isil.pe.model.Cita;
import isil.pe.service.CitaService;
import isil.pe.service.CitaServiceDynamoDB;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.stream.Stream;

@Controller
public class CitaController {

    private final CitaServiceDynamoDB citaServiceDynamoDB ;
    private final CitaService citaService;

    public CitaController(CitaService citaService,CitaServiceDynamoDB citaServiceDynamoDB) {
        this.citaService = citaService;
        this.citaServiceDynamoDB = citaServiceDynamoDB;
    }


    @GetMapping("/")
    public String index(Model model){

        model.addAttribute("cita", new Cita());

        return "index";
    }


    @PostMapping("/cita/save")
    public String citaSave(Cita cita, RedirectAttributes redirectAttributes){
        String mensaje = "";
        try{
            System.out.println(cita);
            citaServiceDynamoDB.loadData(cita);
            citaService.sendNotification(cita);
            mensaje = "Se envio la cita correctamente";
            redirectAttributes.addFlashAttribute("message_ok",mensaje);

        }catch (Exception e){
            mensaje = "Error al registrar la cita";
            redirectAttributes.addFlashAttribute("message_error",mensaje);
        }
        return "redirect:/";
    }
}


