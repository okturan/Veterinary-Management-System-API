package dev.patika.veterinary;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class OwnerApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void ownerResponsesIncludeAnimalsWithOpenSessionInViewDisabled() throws Exception {
        String ownerResponse = mockMvc.perform(post("/api/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Portfolio Demo Owner",
                                  "phone": "555-0100",
                                  "email": "demo.owner@example.test",
                                  "address": "1 Test Avenue",
                                  "city": "Testville"
                                }
                                """))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode owner = objectMapper.readTree(ownerResponse);
        long ownerId = owner.path("id").asLong();

        try {
            mockMvc.perform(post("/api/owners/{id}/animals", ownerId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    {
                                      "name": "Pixel",
                                      "species": "Cat",
                                      "breed": "Domestic Shorthair",
                                      "gender": "Female",
                                      "colour": "Tortoiseshell",
                                      "dateOfBirth": "2022-04-15"
                                    }
                                    """))
                    .andExpect(status().isCreated());

            mockMvc.perform(get("/api/owners/{id}", ownerId))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.animals[0].name").value("Pixel"));

            mockMvc.perform(get("/api/owners"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[*].name", hasItem("Portfolio Demo Owner")))
                    .andExpect(jsonPath("$[?(@.name == 'Portfolio Demo Owner')].animals[0].name")
                            .value("Pixel"));

            mockMvc.perform(get("/api/owners").param("name", "portfolio demo"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].animals[0].name").value("Pixel"));
        } finally {
            mockMvc.perform(delete("/api/owners/{id}", ownerId))
                    .andExpect(status().isNoContent());
        }
    }
}
