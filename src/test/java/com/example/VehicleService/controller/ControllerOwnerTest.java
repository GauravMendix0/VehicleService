package com.example.VehicleService.controller;

import com.example.VehicleService.model.Owner;
import com.example.VehicleService.service.OwnerService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
    private OwnerService ownerService;

    @Test
    void testGetAllOwners() throws Exception {
        Owner owner1 = new Owner(1, "Gaurav", "123");
        Owner owner2 = new Owner(2, "Anil", "456");

        when(ownerService.getAll()).thenReturn(List.of(owner1, owner2));

        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].name", is("Gaurav")));
    }

    @Test
    void testGetOwnerById() throws Exception {
        Owner owner = new Owner(1, "Gaurav", "123");

        when(ownerService.getById(1)).thenReturn(owner);

        mockMvc.perform(get("/owners/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Gaurav")))
                .andExpect(jsonPath("$.contact", is("123")));
    }

    @Test
    void testCreateOwner() throws Exception {
        Owner savedOwner = new Owner(1, "New", "9876543210");

        when(ownerService.saveOwner(any())).thenReturn(savedOwner);

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

    @Test
    void testUpdateOwner() throws Exception {
        Owner updatedOwner = new Owner(1, "Updated", "0000000000");

        when(ownerService.updateOwner(eq(1), any())).thenReturn(updatedOwner);

        String json = """
                {
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

    @Test
    void testPatchOwner() throws Exception {
        Owner patchedOwner = new Owner(1, "Patched", "1111111111");

        when(ownerService.patchOwner(eq(1), any())).thenReturn(patchedOwner);

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

    @Test
    void testDeleteOwner() throws Exception {
        doNothing().when(ownerService).deleteOwner(1);

        mockMvc.perform(delete("/owners/1"))
                .andExpect(status().isOk());
    }

//    @Test
//    void testDeleteOwnerNotFound() throws Exception {
//        doThrow(new RuntimeException("Owner with ID 99 not found"))
//                .when(ownerService).deleteOwner(99);
//
//        mockMvc.perform(delete("/owners/99"))
//                .andExpect(status().isNotFound());
//    }
}
