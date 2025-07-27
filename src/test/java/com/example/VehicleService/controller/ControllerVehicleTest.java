package com.example.VehicleService.controller;

import com.example.VehicleService.model.Owner;
import com.example.VehicleService.model.Vehicle;
import com.example.VehicleService.model.VehicleRequest;
import com.example.VehicleService.service.VehicleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ControllerVehicle.class)
class ControllerVehicleTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehicleService vehicleService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testGetAllVehicles() throws Exception {
        Owner owner = new Owner(1, "Gaurav", "123");
        Vehicle v1 = new Vehicle("MH12AA1234", "Swift", owner);
        Vehicle v2 = new Vehicle("MH13BB4321", "Honda", owner);

        when(vehicleService.getAllVehicles()).thenReturn(List.of(v1, v2));

        mockMvc.perform(get("/vehicles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)));
    }

    @Test
    void testGetVehicleById() throws Exception {
        Owner owner = new Owner(1, "Gaurav", "123");
        Vehicle vehicle = new Vehicle("MH12AA1234", "Swift", owner);

        when(vehicleService.getVehicleById(1)).thenReturn(vehicle);

        mockMvc.perform(get("/vehicles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.model", is("Swift")));
    }

    @Test
    void testCreateVehicle() throws Exception {
        Owner owner = new Owner(1, "Gaurav", "123");
        Vehicle vehicle = new Vehicle("MH12AA1234", "Swift", owner);

        VehicleRequest request = new VehicleRequest();
        request.setOwner(owner);
        request.setVehicle(vehicle);

        when(vehicleService.createVehicle(any())).thenReturn(vehicle);

        mockMvc.perform(post("/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.model", is("Swift")));
    }

    @Test
    void testDeleteVehicle() throws Exception {
        // No need to mock anything if delete doesn't return body or throw error

        doNothing().when(vehicleService).deleteVehicle(1);

        mockMvc.perform(delete("/vehicles/1"))
                .andExpect(status().isOk());
    }
}
