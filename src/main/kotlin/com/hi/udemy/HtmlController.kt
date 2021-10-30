package com.hi.udemy

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class HtmlController {

    @GetMapping("/")
    fun index(model: Model): String {
        return "index"
    }
}