package com.example.VehicleService.controller;

import com.example.VehicleService.model.Owner;
import com.example.VehicleService.model.ServiceAppt;
import com.example.VehicleService.model.ServiceRequest;
import com.example.VehicleService.model.Vehicle;
import com.example.VehicleService.service.ServiceServiceAppt;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
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
    private ServiceServiceAppt serviceApptService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void testGetAllAppointments() throws Exception {
        Owner owner = new Owner(1, "Gaurav", "MH");
        Vehicle vehicle = new Vehicle("MH12AA1234", "Swift", owner);
        ServiceAppt appt1 = new ServiceAppt(vehicle, LocalDateTime.of(2025, 7, 10, 10, 0), "Oil Change");
        ServiceAppt appt2 = new ServiceAppt(vehicle, LocalDateTime.of(2025, 7, 15, 10, 0), "Tyre Rotation");

        when(serviceApptService.getAllAppointments()).thenReturn(List.of(appt1, appt2));

        mockMvc.perform(get("/services"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)));
    }

    @Test
    void testGetAppointmentById() throws Exception {
        Owner owner = new Owner(1,"Gaurav", "MH");
        Vehicle vehicle = new Vehicle("MH12AA1234", "Swift", owner);
        ServiceAppt appt = new ServiceAppt(vehicle, LocalDateTime.of(2025, 7, 10, 10, 0), "Oil Change");

        when(serviceApptService.getAppointmentById(1)).thenReturn(appt);

        mockMvc.perform(get("/services/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.service", is("Oil Change")));
    }

    @Test
    void testCreateAppointment() throws Exception {
        Owner owner = new Owner(1,"Gaurav", "MH");
        Vehicle vehicle = new Vehicle("MH12AA1234", "Swift", owner);
        vehicle.setVid(101);
        ServiceAppt appt = new ServiceAppt(vehicle, LocalDateTime.of(2025, 7, 10, 10, 0), "Brake Check");

        ServiceRequest request = new ServiceRequest();
        request.setVehicle(vehicle);
        request.setServiceAppt(appt);

        when(serviceApptService.createAppointment(any(ServiceRequest.class))).thenReturn(appt);

        mockMvc.perform(post("/services")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.service", is("Brake Check")));
    }

    @Test
    void testReplaceAppointment() throws Exception {
        Owner owner = new Owner(1,"Gaurav", "MH");
        Vehicle vehicle = new Vehicle("MH12AA1234", "Swift", owner);
        vehicle.setVid(102);
        ServiceAppt appt = new ServiceAppt(vehicle, LocalDateTime.of(2025, 7, 20, 10, 0), "Engine Checkup");

        ServiceRequest request = new ServiceRequest();
        request.setVehicle(vehicle);
        request.setServiceAppt(appt);

        when(serviceApptService.replaceAppointment(eq(1), any(ServiceRequest.class))).thenReturn(appt);

        mockMvc.perform(put("/services/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.service", is("Engine Checkup")));
    }

    @Test
    void testUpdateAppointment() throws Exception {
        Owner owner = new Owner(1,"Gaurav", "MH");
        Vehicle vehicle = new Vehicle("MH12AA1234", "Swift", owner);
        vehicle.setVid(103);
        ServiceAppt appt = new ServiceAppt(vehicle, LocalDateTime.of(2025, 7, 25, 10, 0), "Battery Check");

        ServiceRequest request = new ServiceRequest();
        request.setVehicle(vehicle);
        request.setServiceAppt(appt);

        when(serviceApptService.updateAppointment(eq(1), any(ServiceRequest.class))).thenReturn(appt);

        mockMvc.perform(patch("/services/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.service", is("Battery Check")));
    }

    @Test
    void testDeleteAppointment() throws Exception {
        doNothing().when(serviceApptService).deleteAppointment(1);

        mockMvc.perform(delete("/services/1"))
                .andExpect(status().isOk());
    }
}
