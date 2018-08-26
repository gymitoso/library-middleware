package br.com.library.middleware.cucumber.stepdefs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.library.middleware.web.rest.AuthController;
import br.com.library.middleware.web.rest.vm.LoginVM;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class LoginStepDefs extends StepDefs {

    @Autowired
    private AuthController authController;

    @Autowired
    private ObjectMapper mapper;

    private LoginVM loginVM;

    private MockMvc restUserMockMvc;

    @Before
    public void setup() {
        this.restUserMockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        this.loginVM = new LoginVM();
    }

    @Given("^a registered user login '(.*)'$")
    public void registered_user(String login) throws Throwable {
        this.loginVM.setUsername(login);
    }

    @And("^password '(.*)'$")
    public void password_user(String password) throws Throwable {
        this.loginVM.setPassword(password);
    }

    @When("^I try to sign in to Mobibuzz system$")
    public void try_sign_in() throws Throwable {
        String json = mapper.writeValueAsString(this.loginVM);
        actions = restUserMockMvc.perform(post("/api/authenticate")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    }

    @Then("^I have to receive the '(.*)' and '(.*)'$")
    public void receive(String token, String user) throws Throwable {
        actions
            .andExpect(jsonPath("$." + token).exists())
            .andExpect(jsonPath("$." + user).exists());
    }

    @And("^http status '(\\d+)'$")
    public void http_status(int status) throws Throwable {
        actions
            .andExpect(status().is(status));
    }
}
