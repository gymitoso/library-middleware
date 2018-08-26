package br.com.library.middleware.cucumber.stepdefs;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import br.com.library.middleware.MiddlewareApplication;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = MiddlewareApplication.class)
public abstract class StepDefs {

    // when using api call
    protected ResultActions actions;

}
