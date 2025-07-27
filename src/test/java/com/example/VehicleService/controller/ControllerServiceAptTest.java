package com.example.VehicleService.controller;

import com.example.VehicleService.model.ServiceAppt;
import com.example.VehicleService.model.ServiceRequest;
import com.example.VehicleService.model.Vehicle;
import com.example.VehicleService.service.ServiceServiceAppt;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

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

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testGetAllAppointments() throws Exception {
        Vehicle vehicle = new Vehicle("MH12AA1234", "Swift", null);
        ServiceAppt appt1 = new ServiceAppt(vehicle, "2025-07-10", "Oil Change");
        ServiceAppt appt2 = new ServiceAppt(vehicle, "2025-07-15", "Tyre Rotation");

        when(serviceApptService.getAllAppointments()).thenReturn(List.of(appt1, appt2));

        mockMvc.perform(get("/services"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)));
    }

    @Test
    void testGetAppointmentById() throws Exception {
        Vehicle vehicle = new Vehicle("MH12AA1234", "Swift", null);
        ServiceAppt appt = new ServiceAppt(vehicle, "2025-07-10", "Oil Change");

        when(serviceApptService.getAppointmentById(1)).thenReturn(appt);

        mockMvc.perform(get("/services/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.service", is("Oil Change")));
    }

    @Test
    void testCreateAppointment() throws Exception {
        Vehicle vehicle = new Vehicle("MH12AA1234", "Swift", null);
        vehicle.setVid(101);  // Set ID
        ServiceAppt appt = new ServiceAppt(vehicle, "2025-07-10", "Brake Check");
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
        Vehicle vehicle = new Vehicle("MH12AA1234", "Swift", null);
        vehicle.setVid(102);
        ServiceAppt appt = new ServiceAppt(vehicle, "2025-07-20", "Engine Checkup");
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
        Vehicle vehicle = new Vehicle("MH12AA1234", "Swift", null);
        vehicle.setVid(103);
        ServiceAppt appt = new ServiceAppt(vehicle, "2025-07-25", "Battery Check");
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
