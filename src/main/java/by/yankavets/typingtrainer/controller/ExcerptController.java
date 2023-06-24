package by.yankavets.typingtrainer.controller;

import by.yankavets.typingtrainer.model.entity.training.Excerpt;
import by.yankavets.typingtrainer.service.training.ExcerptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/excerpts")
public class ExcerptController {

    @Autowired
    private ExcerptService excerptService;

    @GetMapping
    public List<Excerpt> excerptList() {
        return excerptService.findAll();
    }
}
