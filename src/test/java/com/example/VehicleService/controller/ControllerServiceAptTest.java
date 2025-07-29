package com.example.VehicleService.controller;

import com.example.VehicleService.dto.ServiceApptRequestDTO;
import com.example.VehicleService.dto.ServiceApptResponseDTO;
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
import static org.mockito.ArgumentMatchers.*;
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

    private ServiceApptResponseDTO getSampleResponseDTO(String service, LocalDateTime date) {
        ServiceApptResponseDTO dto = new ServiceApptResponseDTO();
        dto.setId(1);
        dto.setService(service);
        dto.setServiceDate(date);
        dto.setVehicleNumber("MH12AA1234");
        dto.setVehicleModel("Swift");
        dto.setOwnerName("Gaurav");
        return dto;
    }

    @Test
    void testGetAllAppointments() throws Exception {
        ServiceApptResponseDTO dto1 = getSampleResponseDTO("Oil Change", LocalDateTime.of(2025, 7, 10, 10, 0));
        ServiceApptResponseDTO dto2 = getSampleResponseDTO("Tyre Rotation", LocalDateTime.of(2025, 7, 15, 10, 0));

        when(serviceApptService.getAllAppointments()).thenReturn(List.of(dto1, dto2));

        mockMvc.perform(get("/services"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)));
    }

    @Test
    void testGetAppointmentById() throws Exception {
        ServiceApptResponseDTO dto = getSampleResponseDTO("Oil Change", LocalDateTime.of(2025, 7, 10, 10, 0));

        when(serviceApptService.getAppointmentById(1)).thenReturn(dto);

        mockMvc.perform(get("/services/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.service", is("Oil Change")))
                .andExpect(jsonPath("$.vehicleNumber", is("MH12AA1234")));
    }

    @Test
    void testCreateAppointment() throws Exception {
        ServiceApptRequestDTO request = new ServiceApptRequestDTO();
        request.setService("Brake Check");
        request.setServiceDate(LocalDateTime.of(2025, 7, 10, 10, 0));
        request.setVehicleId(101);

        ServiceApptResponseDTO response = getSampleResponseDTO("Brake Check", request.getServiceDate());

        when(serviceApptService.createAppointment(any(ServiceApptRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/services")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.service", is("Brake Check")));
    }

    @Test
    void testReplaceAppointment() throws Exception {
        ServiceApptRequestDTO request = new ServiceApptRequestDTO();
        request.setService("Engine Checkup");
        request.setServiceDate(LocalDateTime.of(2025, 7, 20, 10, 0));
        request.setVehicleId(102);

        ServiceApptResponseDTO response = getSampleResponseDTO("Engine Checkup", request.getServiceDate());

        when(serviceApptService.replaceAppointment(eq(1), any(ServiceApptRequestDTO.class))).thenReturn(response);

        mockMvc.perform(put("/services/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.service", is("Engine Checkup")));
    }

    @Test
    void testUpdateAppointment() throws Exception {
        ServiceApptRequestDTO request = new ServiceApptRequestDTO();
        request.setService("Battery Check");
        request.setServiceDate(LocalDateTime.of(2025, 7, 25, 10, 0));
        request.setVehicleId(103);

        ServiceApptResponseDTO response = getSampleResponseDTO("Battery Check", request.getServiceDate());

        when(serviceApptService.updateAppointment(eq(1), any(ServiceApptRequestDTO.class))).thenReturn(response);

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
