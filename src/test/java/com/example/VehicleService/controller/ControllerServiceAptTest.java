package com.example.VehicleService.controller;

import com.example.VehicleService.model.ServiceAppt;
import com.example.VehicleService.ServiceRequest;
import com.example.VehicleService.model.Vehicle;
import com.example.VehicleService.repo.RepoServiceApt;
import com.example.VehicleService.repo.RepoVehicle;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@WebMvcTest(ControllerServiceApt.class)
class ControllerServiceAptTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RepoServiceApt repoServiceApt;

    @MockBean
    private RepoVehicle repoVehicle;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testGetAllAppointments() throws Exception {
        Vehicle vehicle = new Vehicle("MH12AA1234", "Swift", null);
        ServiceAppt appt1 = new ServiceAppt(vehicle, "2025-07-10", "Oil Change");
        ServiceAppt appt2 = new ServiceAppt(vehicle, "2025-07-15", "Tyre Rotation");

        when(repoServiceApt.findAll()).thenReturn(List.of(appt1, appt2));

        mockMvc.perform(get("/services"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)));
    }

    @Test
    void testGetAppointmentById() throws Exception {
        Vehicle vehicle = new Vehicle("MH12AA1234", "Swift", null);
        ServiceAppt appt = new ServiceAppt(vehicle, "2025-07-10", "Oil Change");

        when(repoServiceApt.findById(1)).thenReturn(Optional.of(appt));

        mockMvc.perform(get("/services/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.service", is("Oil Change")));
    }

    @Test
    void testCreateAppointment() throws Exception {
        Vehicle vehicle = new Vehicle("MH12AA1234", "Swift", null);
        ServiceAppt appt = new ServiceAppt(vehicle, "2025-07-10", "Brake Check");
        ServiceRequest request = new ServiceRequest();
        request.setVehicle(vehicle);
        request.setServiceAppt(appt);

        when(repoVehicle.findById(anyInt())).thenReturn(Optional.of(vehicle));
        when(repoServiceApt.save(any())).thenReturn(appt);

        mockMvc.perform(post("/services")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.service", is("Brake Check")));
    }

    @Test
    void testDeleteAppointment() throws Exception {
        Vehicle vehicle = new Vehicle("MH12AA1234", "Swift", null);
        ServiceAppt appt = new ServiceAppt(vehicle, "2025-07-10", "Oil Change");

        when(repoServiceApt.findById(1)).thenReturn(Optional.of(appt));

        when(repoServiceApt.existsById(1)).thenReturn(true);

        mockMvc.perform(delete("/services/1"))
                .andExpect(status().isOk());
    }
}
