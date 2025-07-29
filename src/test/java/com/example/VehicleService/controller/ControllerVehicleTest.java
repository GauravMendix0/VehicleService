package com.example.VehicleService.controller;

import com.example.VehicleService.dto.VehicleRequestDTO;
import com.example.VehicleService.dto.VehicleResponseDTO;
import com.example.VehicleService.service.VehicleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(ControllerVehicle.class)
class ControllerVehicleTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehicleService vehicleService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testGetAllVehicles() throws Exception {
        VehicleResponseDTO v1 = new VehicleResponseDTO();
        v1.setId(1);
        v1.setNumber("MH12AA1234");
        v1.setModel("Swift");
        v1.setOwnerName("Gaurav");

        VehicleResponseDTO v2 = new VehicleResponseDTO();
        v2.setId(2);
        v2.setNumber("MH13BB4321");
        v2.setModel("Honda");
        v2.setOwnerName("Gaurav");

        when(vehicleService.getAllVehicles()).thenReturn(List.of(v1, v2));

        mockMvc.perform(get("/vehicles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].model", is("Swift")))
                .andExpect(jsonPath("$[1].number", is("MH13BB4321")));
    }

    @Test
    void testGetVehicleById() throws Exception {
        VehicleResponseDTO response = new VehicleResponseDTO();
        response.setId(1);
        response.setNumber("MH12AA1234");
        response.setModel("Swift");
        response.setOwnerName("Gaurav");

        when(vehicleService.getVehicleById(1)).thenReturn(response);

        mockMvc.perform(get("/vehicles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.model", is("Swift")))
                .andExpect(jsonPath("$.ownerName", is("Gaurav")));
    }

    @Test
    void testCreateVehicle() throws Exception {
        VehicleRequestDTO request = new VehicleRequestDTO();
        request.setNumber("MH12AA1234");
        request.setModel("Swift");
        request.setOwnerId(1);

        VehicleResponseDTO response = new VehicleResponseDTO();
        response.setId(1);
        response.setNumber("MH12AA1234");
        response.setModel("Swift");
        response.setOwnerName("Gaurav");

        when(vehicleService.addVehicle(any(VehicleRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number", is("MH12AA1234")))
                .andExpect(jsonPath("$.ownerName", is("Gaurav")));
    }

    @Test
    void testDeleteVehicle() throws Exception {
        doNothing().when(vehicleService).deleteVehicle(1);

        mockMvc.perform(delete("/vehicles/1"))
                .andExpect(status().isOk());
    }
}
