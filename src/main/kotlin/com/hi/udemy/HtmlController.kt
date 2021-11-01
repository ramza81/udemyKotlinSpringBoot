package com.hi.udemy

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.security.MessageDigest
import javax.servlet.http.HttpSession

@Controller
class HtmlController {

    @Autowired
    lateinit var repository: UserRepository

    @GetMapping("/")
    fun index(model: Model): String {
        model.addAttribute("title", "Home")
        return "index"
    }

    @GetMapping("/{formType}")
    fun htmlForm(model: Model, @PathVariable formType: String): String {
        model.addAttribute("title", formType)
        return formType
    }

    fun crypto(ss: String) : String {
        val sha = MessageDigest.getInstance("SHA-256")
        val hexa = sha.digest(ss.toByteArray())
        val cryptoString = hexa.fold("", { str, it -> str + "%02x".format(it) } )
        return cryptoString
    }

    @PostMapping("/sign")
    fun postSign(model: Model,
                 @RequestParam(value = "id") userId: String,
                 @RequestParam(value = "password") password: String): String {

        println(userId.toString())
        println(password.toString())

        try {
            val cryptoPassword = crypto(password)
            val user = repository.save(
                User(
                    userId = userId,
                    password = cryptoPassword
                )
            )
            println(user.toString())
        } catch (e: Exception) {
            e.printStackTrace()
        }

        model.addAttribute("title", "sign success")
        return "index"

    }

    @PostMapping("/login")
    fun postLogin(model: Model,
                  session: HttpSession,
                  @RequestParam(value = "id") userId: String,
                  @RequestParam(value = "password") password: String): String {
        var pageName = ""

        try {
            val cryptoPassword = crypto(password)
            val dbUser = repository.findByUserId(userId)

            if (dbUser != null) {
                val dbPassword = dbUser.password

                if (cryptoPassword == dbPassword) {
                    session.setAttribute("userId", dbUser.userId)

                    model.addAttribute("title", "login success")
                    model.addAttribute("userId", userId)

                    pageName = "welcome"
                }
            } else  {
                model.addAttribute("title", "Failed login")
                pageName = "login"
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return pageName

    }

}