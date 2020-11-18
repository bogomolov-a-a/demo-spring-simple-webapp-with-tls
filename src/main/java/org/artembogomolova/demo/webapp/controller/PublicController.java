package org.artembogomolova.demo.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublicController {

  @GetMapping("/")
  public String getIndexPage() {
    return "fragments/public/index";
  }
}
