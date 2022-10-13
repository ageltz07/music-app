package me.adamgeltz.musicapp.controllers;

import lombok.Getter;
import me.adamgeltz.musicapp.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    private final StorageService storageService;

    @Autowired
    public IndexController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping
    public String getHomepage(Model model) {

        // MVC - Model, View, Controller
        model.addAttribute("songFileNames", storageService.getSongFileNames());

        // Returns index.html from templates package
        return "index";
    }

}
