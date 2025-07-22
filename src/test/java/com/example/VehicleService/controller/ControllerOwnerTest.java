package com.example.VehicleService.controller;

import com.example.VehicleService.model.Owner;
import com.example.VehicleService.repo.RepoOwner;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ControllerOwner.class)
public class ControllerOwnerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RepoOwner repoOwner;

    // GET all owners
    @Test
    void testGetAllOwners() throws Exception {
        Owner owner1 = new Owner(1, "Gaurav", "123");
        Owner owner2 = new Owner(2, "Anil", "456");

        when(repoOwner.findAll()).thenReturn(List.of(owner1, owner2));

        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].name", is("Gaurav")));
    }

    // GET owner by ID - found
    @Test
    void testGetOwnerById() throws Exception {
        Owner owner = new Owner(1, "Gaurav", "123");

        when(repoOwner.findById(1)).thenReturn(Optional.of(owner));

        mockMvc.perform(get("/owners/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Gaurav")))
                .andExpect(jsonPath("$.contact", is("123")));
    }

    // GET owner by ID - not found
    @Test
    void testGetOwnerByIdNotFound() throws Exception {
        when(repoOwner.findById(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/owners/99"))
                .andExpect(status().isNotFound());
    }

    // POST new owner
    @Test
    void testCreateOwner() throws Exception {
        Owner savedOwner = new Owner(1, "New", "9876543210");

        when(repoOwner.save(any())).thenReturn(savedOwner);

        String json = """
                {
                  "name": "New",
                  "contact": "9876543210"
                }
                """;

        mockMvc.perform(post("/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("New")))
                .andExpect(jsonPath("$.contact", is("9876543210")));
    }

    // PUT update owner - exists
    @Test
    void testUpdateOwner() throws Exception {
        Owner inputOwner = new Owner(1, "Updated", "0000000000");

        when(repoOwner.existsById(1)).thenReturn(true);
        when(repoOwner.save(any())).thenReturn(inputOwner);

        String json = """
                {
                  "oid": 1,
                  "name": "Updated",
                  "contact": "0000000000"
                }
                """;

        mockMvc.perform(put("/owners/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Updated")))
                .andExpect(jsonPath("$.contact", is("0000000000")));
    }

    // PUT update owner - not found
    @Test
    void testUpdateOwnerNotFound() throws Exception {
        when(repoOwner.existsById(99)).thenReturn(false);

        String json = """
                {
                  "oid": 99,
                  "name": "Updated",
                  "contact": "0000000000"
                }
                """;

        mockMvc.perform(put("/owners/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    // PATCH owner - exists
    @Test
    void testPatchOwner() throws Exception {
        Owner patchedOwner = new Owner(1, "Patched", "1111111111");

        when(repoOwner.findById(1)).thenReturn(Optional.of(patchedOwner));
        when(repoOwner.save(any())).thenReturn(patchedOwner);

        String json = """
                {
                  "name": "Patched",
                  "contact": "1111111111"
                }
                """;

        mockMvc.perform(patch("/owners/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Patched")))
                .andExpect(jsonPath("$.contact", is("1111111111")));
    }


    // DELETE owner - exists
    @Test
    void testDeleteOwner() throws Exception {
        when(repoOwner.existsById(1)).thenReturn(true);
        doNothing().when(repoOwner).deleteById(1);

        mockMvc.perform(delete("/owners/1"))
                .andExpect(status().isOk());
    }

    // DELETE owner - not found
    @Test
    void testDeleteOwnerNotFound() throws Exception {
        when(repoOwner.existsById(99)).thenReturn(false);

        mockMvc.perform(delete("/owners/99"))
                .andExpect(status().isNotFound());
    }
}
