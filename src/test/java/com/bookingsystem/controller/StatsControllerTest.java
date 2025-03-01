package com.bookingsystem.controller;

import com.bookingsystem.model.generated.UnitListResponse;
import com.bookingsystem.service.StatsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StatsController.class)
class StatsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatsService statsService;

    @Test
    void testGetAvailableUnitsCountWithParams() throws Exception {
        UnitListResponse dummyResponse = new UnitListResponse();
        dummyResponse.setContent(Collections.emptyList());
        dummyResponse.setTotalElements(0);
        dummyResponse.setTotalPages(0);
        dummyResponse.setSize(10);
        dummyResponse.setStatus(200);

        when(statsService.getAvailableUnits(anyInt(), anyInt())).thenReturn(dummyResponse);

        mockMvc.perform(get("/stats/available-units")
                        .param("page", "0")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    void testGetAvailableUnitsCountDefaultParameters() throws Exception {
        UnitListResponse dummyResponse = new UnitListResponse();
        dummyResponse.setContent(Collections.emptyList());
        dummyResponse.setTotalElements(5);
        dummyResponse.setTotalPages(1);
        dummyResponse.setSize(10);
        dummyResponse.setStatus(200);

        when(statsService.getAvailableUnits(0, 10)).thenReturn(dummyResponse);

        mockMvc.perform(get("/stats/available-units")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(5))
                .andExpect(jsonPath("$.size").value(10));
    }

    @Test
    void testGetAvailableUnitsCountNotFound() throws Exception {
        UnitListResponse dummyResponse = new UnitListResponse();
        dummyResponse.setContent(Collections.emptyList());
        dummyResponse.setTotalElements(0);
        dummyResponse.setTotalPages(0);
        dummyResponse.setSize(0);
        dummyResponse.setStatus(404);

        when(statsService.getAvailableUnits(anyInt(), anyInt())).thenReturn(dummyResponse);

        mockMvc.perform(get("/stats/available-units")
                        .param("page", "0")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }
}
