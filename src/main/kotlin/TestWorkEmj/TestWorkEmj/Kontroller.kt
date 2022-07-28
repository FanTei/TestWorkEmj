package TestWorkEmj.TestWorkEmj

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView


@RestController
class MyRestController {
    @RequestMapping("/")
    fun welcome(): ModelAndView {
        val modelAndView = ModelAndView()
        modelAndView.viewName = "index.html"
        return modelAndView
    }
}