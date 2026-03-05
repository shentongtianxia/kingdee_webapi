package com.kingdee.webapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Provide a few compatibility redirects/forwards so UIs or older clients that expect
 * different paths still work:
 *  - /doc -> redirects to /doc.html
 *  - /swagger-ui.html -> redirects to /doc.html (some setups use this)
 *  - /v2/api-docs -> forwards to /v3/api-docs (Springfox OAS3 uses /v3, some UIs request /v2)
 */
@Controller
public class DocRedirectController {

    @GetMapping({"/doc", "/swagger-ui.html"})
    public void redirectToDocHtml(HttpServletResponse response) throws IOException {
        response.sendRedirect("/doc.html");
    }

    @GetMapping("/v2/api-docs")
    public void forwardV2ToV3(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward internally to /v3/api-docs so clients requesting /v2 get the OAS3 JSON
        request.getRequestDispatcher("/v3/api-docs").forward(request, response);
    }
}


