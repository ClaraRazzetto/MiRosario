package com.mirosario.MiRosario.controladores;

import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorControlador {

    @Controller
    public class ControladorError implements ErrorController {

        @RequestMapping(value = "/error", method = {RequestMethod.GET, RequestMethod.POST})
        public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {

            ModelAndView errorPage = new ModelAndView("error");
            String errorMsg = "";
            int httpErrorCode = (int) httpRequest.getAttribute("javax.servlet.error.status_code");

            switch (httpErrorCode) {
                case 400: {
                    errorMsg = "El recurso solicitado no existe";
                    break;
                }
                case 403: {
                    errorMsg = "No tiene permisos para acceder al recurso";
                    break;
                }
                case 401: {
                    errorMsg = "No se encuentra autorizado";
                    break;
                }
                case 404: {
                    errorMsg = "El recurso solicitado no fue encontrado";
                    break;
                }
                case 500: {
                    errorMsg = "Ocurrio un error interno";
                    break;
                }
            }
            errorPage.addObject("codigo", httpErrorCode);
            errorPage.addObject("mensaje", errorMsg);
            return errorPage;
        }
    }
}