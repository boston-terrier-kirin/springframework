package com.example.demo;

import com.example.demo.controller.GradeController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class GradeApplicationTest {

    private MockMvc mockMvc;

    @Autowired
    private GradeController controller;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void contextLoads() {
        assertNotNull(mockMvc);
    }

    @Test
    public void showNewGradeFormTest() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/grades/new");
        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("form"))
                .andExpect(model().attributeExists("grade"));
    }

    @Test
    public void testSuccessfulSubmission() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders.post("/grades/save")
                .param("name", "Harry")
                .param("subject", "Potions")
                .param("score", "C-");

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/grades"));
    }

    @Test
    public void testErrorSubmission() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders.post("/grades/save")
                .param("name", "John Doe")
                .param("subject", "Potions")
                .param("score", "C-");

        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().hasErrors())
                .andExpect(model().errorCount(1))
                .andExpect(view().name("form"));
    }

}
